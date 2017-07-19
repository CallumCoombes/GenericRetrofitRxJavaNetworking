package uk.cal.codename.genericretrofitrxjavanetworking.Request;


import uk.cal.codename.genericretrofitrxjavanetworking.RequestObjects.RequestObj;
import uk.cal.codename.genericretrofitrxjavanetworking.ResponseObjects.ResponseObj;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public interface RequestInterface {

    /**
     * Submits the request to the server. Observable<> object must be declared and passed to submit()
     * method.
     *
     * Example:
     * Observable<RegisterResponseObj> registerResponse = registerApi.post((RegisterRequestObj)requestObj);
     * submitOnBackgroundThread(registerResponse);
     *
     * @param requestObj
     */
    public void submitRequest(RequestObj requestObj, ObserveOn threadType);

    /**
     * Handles the response from the server. This is still carried out in a worker thread if
     * submitOnBackgroundThread is called, or on the main thread (UI) if submitOnMainThread is called.
     * @param responseObj
     * @return boolean true if successful, false otherwise
     */
    public boolean handleResponse(ResponseObj responseObj);
}
