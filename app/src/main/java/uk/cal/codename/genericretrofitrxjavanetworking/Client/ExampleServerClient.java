package uk.cal.codename.genericretrofitrxjavanetworking.Client;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public class ExampleServerClient {

    private ExampleServerClient(){}

    public static final String BASE_URL = "http://123.456.789.10:5000";

    public static <T> T getExampleRetrofitClient(Class<T> requestClass){
        return RetrofitClient.getClient(BASE_URL).create(requestClass);
    }
}
