package edu.p.lodz.pl.common.entities;

import com.google.gson.Gson;

public record LicenceToken(String payload) {
    public LicenceRequestResponse toObject() {
        Gson gson = new Gson();
        return gson.fromJson(payload, LicenceRequestResponse.class);
    }
}
