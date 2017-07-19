package uk.cal.codename.genericretrofitrxjavanetworking.ResponseObjects;

import com.google.gson.annotations.SerializedName;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public class ExampleResponseObj extends ResponseObj {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private String data;
    @SerializedName("more")
    private boolean more;

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public boolean isMore() {
        return more;
    }

    @Override
    public String toString() {
        return "ExampleResponseObj{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", more=" + more +
                '}';
    }
}
