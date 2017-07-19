package uk.cal.codename.genericretrofitrxjavanetworking.Request;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import uk.cal.codename.genericretrofitrxjavanetworking.ResponseObjects.ResponseObj;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public abstract class Request implements  RequestInterface{

    protected String TAG = this.getClass().getName();
    //protected UiHandler userInterfaceHandler;
    protected final int RETRY_COUNT = 4;
    protected PublishSubject<Boolean> subject = PublishSubject.create();
    protected Context mContext;

    /*public interface UiHandler{
        void handleUiChanges();
    }*/

    public Request(/*Context context*/) {
        //this.mContext = context;
    }

    protected void submit(Observable<?> responseObservable, ObserveOn threadType){
        switch (threadType) {
            case MAIN_THREAD:
                Log.d(TAG, "Submitting request on MAIN_THREAD");
                submitOnMainThread(responseObservable);
                break;
            case BACKGROUND_THREAD:
                Log.d(TAG, "Submitting request on BACKGROUND_THREAD");
                submitOnBackgroundThread(responseObservable);
                break;
            default:
                Log.d(TAG, "Error: ObserveOn thread type not recognised, request not submitted");
                break;
        }
    }

    /**
     * Submits task and observes on background thread. If task errors in anyway the task will retry,
     * adding a 1 second delay for each consecutive error (eg. delay of 1 sec -> 2 sec -> 3 sec etc).
     * Returned data is then processed in handleResponse().
     * @param responseObservable
     */
    protected void submitOnBackgroundThread(Observable<?> responseObservable){
        //subscribe to service
        responseObservable.subscribeOn(Schedulers.io())
                .retryWhen(attempts -> {
                    return attempts.zipWith(Observable.range(1, RETRY_COUNT), (n, i) -> i).flatMap(i -> {
                        Log.i("Request_backgrndThread", "Error: delay server call retry by " + i + " second(s)");
                        if(i == RETRY_COUNT) {
                            publishResultBoolean(false);
                        }
                        return Observable.timer(i, TimeUnit.SECONDS);
                    });
                })
                .subscribe(responseData -> {
                    Log.i("Assemble Server Call", "Received: " + responseData.toString());
                    //put data in config
                    boolean isSuccessful = handleResponse((ResponseObj)responseData);
                    publishResultBoolean(isSuccessful);
                });
    }

    /**
     * Submits task and observes on main thread UI thread. If task errors in anyway the task will retry,
     * adding a 1 second delay for each consecutive error (eg. delay of 1 sec -> 2 sec -> 3 sec etc).
     * Returned data is then processed in handleResponse().
     * @param responseObservable
     */
    protected void submitOnMainThread(Observable<?> responseObservable){
        //subscribe to service
        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(attempts -> {
                    return attempts.zipWith(Observable.range(1, RETRY_COUNT), (n, i) -> i).flatMap(i -> {
                        Log.i("Request_mainThread", "Error: delay server call retry by " + i + " second(s)");
                        if(i == RETRY_COUNT) {
                            publishResultBoolean(false);
                        }
                        return Observable.timer(i, TimeUnit.SECONDS);
                    });

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseData -> {
                    Log.i("Assemble Server Call", "Received: " + responseData.toString());
                    //put data in config
                    boolean isSuccessful = handleResponse((ResponseObj)responseData);
                    publishResultBoolean(isSuccessful);
                });
    }

    /**
     * Pass a boolean down to event listeners.
     */
    public void publishResultBoolean(Boolean bool) {
        subject.onNext(bool);
    }

    /**
     * Subscribe to this Observable. On event, do something e.g. replace a fragment
     */
    public Observable<Boolean> getBooleanObservable() {
        return subject;
    }

}
