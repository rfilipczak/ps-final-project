package edu.p.lodz.pl.server.utils;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public record LicenceData(String LicenceUserName, Integer ValidationTime) {
    public static Type listOf = new TypeToken<ArrayList<LicenceData>>() {}.getType();
}
