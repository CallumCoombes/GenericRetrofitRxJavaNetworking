package uk.cal.codename.genericretrofitrxjavanetworking.RequestObjects;

import com.google.gson.annotations.SerializedName;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public class ExampleRequestObj extends RequestObj{

    @SerializedName("id")
    private String id;
    @SerializedName("example_string")
    private String example;
    @SerializedName("timestamp")
    private long timestamp;

    public ExampleRequestObj(String id, String example, long timestamp) {
        this.id = id;
        this.example = example;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getExample() {
        return example;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ExampleRequestObj{" +
                "id='" + id + '\'' +
                ", example='" + example + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
