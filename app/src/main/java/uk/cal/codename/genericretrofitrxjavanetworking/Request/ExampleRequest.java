package uk.cal.codename.genericretrofitrxjavanetworking.Request;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observable;
import uk.cal.codename.genericretrofitrxjavanetworking.API.ExampleApi;
import uk.cal.codename.genericretrofitrxjavanetworking.Client.ExampleServerClient;
import uk.cal.codename.genericretrofitrxjavanetworking.RequestObjects.ExampleRequestObj;
import uk.cal.codename.genericretrofitrxjavanetworking.RequestObjects.RequestObj;
import uk.cal.codename.genericretrofitrxjavanetworking.ResponseObjects.ExampleResponseObj;
import uk.cal.codename.genericretrofitrxjavanetworking.ResponseObjects.ResponseObj;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public class ExampleRequest extends Request {

    private static ExampleRequest instance;
    private ExampleApi exampleApi;
    private ExampleRequestObj exampleRequestObj;

    public ExampleRequest(Context context) {
        super();
        this.exampleApi = ExampleServerClient.getExampleRetrofitClient(ExampleApi.class);
    }

    public static ExampleRequest instanceOf(Context context) {
        if (instance == null) {
            instance = new ExampleRequest(context);
        }
        return instance;
    }

    @Override
    public void submitRequest(RequestObj requestObj, ObserveOn threadType) {
        Log.i(TAG, "SubmitRequest called");
        //assign request object so it can be used in the response if needed
        exampleRequestObj = (ExampleRequestObj) requestObj;
        Observable<ExampleResponseObj> exampleResponseObjObservable =
                exampleApi.post((ExampleRequestObj) requestObj);
        submit(exampleResponseObjObservable, threadType);
    }

    @Override
    public boolean handleResponse(ResponseObj responseObj) {
        Log.i(TAG, "Handling response");
        ExampleResponseObj exampleResponseObj = (ExampleResponseObj) responseObj;
        Log.d(TAG, "ExampleResponseObj: " + exampleResponseObj.toString());

        //If response returns with status "ok"
        if (exampleResponseObj.getStatus().equals("ok")) {

            //Do successful response logic here

            return true;
        } else {

            //else do unsuccessful response logic here

            return false;
        }
    }
}
