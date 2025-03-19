package com.lacak.suggestion_service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Suggestion {
    // "name": "London, ON, Canada",
    // "latitude": "42.98339",
    // "longitude": "-81.23304",
    // "score": 0.9

    @JsonProperty("name")
    private String name;

    @JsonProperty("asciiname")
    private String asciiname;

    @JsonProperty("score")
    private Double score;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("country_code")
    private String countryCode;
    // private Double latitude;
    // private Double longitude;
    // private Double score;

    // public Suggestion(String name, Double latitude, Double longitude) {
    // this.name = name;
    // this.latitude = latitude;
    // this.longitude = longitude;
    // }

    public Suggestion() {
    }

    public Suggestion(String name, String asciiname, Double score, String timezone, Double latitude, Double longitude, String countryCode) {
        this.name = name;
        this.asciiname = asciiname;
        this.score = score;
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsciiname() {
        return asciiname;
    }

    public void setAsciiname(String asciiname) {
        this.asciiname = asciiname;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
