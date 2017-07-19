package uk.cal.codename.genericretrofitrxjavanetworking.API;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import uk.cal.codename.genericretrofitrxjavanetworking.RequestObjects.ExampleRequestObj;
import uk.cal.codename.genericretrofitrxjavanetworking.ResponseObjects.ExampleResponseObj;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public interface ExampleApi {
    @POST("app/example_url")
    Observable<ExampleResponseObj> post(@Body ExampleRequestObj exampleRequestObj);
}
