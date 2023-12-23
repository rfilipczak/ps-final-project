package edu.p.lodz.pl.common.entities;

import com.google.gson.Gson;

public abstract class Serializable {
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
