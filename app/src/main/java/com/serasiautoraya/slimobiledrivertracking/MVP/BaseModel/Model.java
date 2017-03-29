package com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class Model {

    public HashMap<String,String> getHashMapType(){
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> map = gson.fromJson(getJSONType(), stringStringMap);
        return new HashMap<String, String>(map);
    }

    public String getJSONType(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static <T> T getModelInstance(Object object, Class<T> cls){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), cls);
    }

    public static <T> T getModelInstanceFromString(String jsonString, Class<T> cls){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, cls);
    }

}
