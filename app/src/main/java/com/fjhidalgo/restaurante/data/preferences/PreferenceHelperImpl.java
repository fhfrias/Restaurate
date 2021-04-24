package com.fjhidalgo.restaurante.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.fjhidalgo.restaurante.di.PreferenceInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String USER_PREF = "USER_PREF";

    /*
    Gson gson;*/
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private final Context context;

    @PreferenceInfo
    private final String prefFileName;

    private final SharedPreferences prefs;

    //
    public PreferenceHelperImpl(Context context, String prefFileName) {
        this.context = context;
        this.prefFileName = prefFileName;

        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    private <T> T get(String name, Class<T> clazz) {

        Object o = gson.fromJson(this.prefs.getString(name, null), clazz);

        if (o == null) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                return null;
            }
        }
        return (T) o;
    }

    private <T> void set(String name, T object) {
        //this.prefs.edit().putString(name, gson.toJson(object)).commit();
        this.prefs.edit().putString(name, gson.toJson(object)).apply();
    }

    private void reset(String name) {
        this.prefs.edit().remove(name).apply();
    }

    @Override
    public <T> T getUserPref(Class<T> clazz) {
        return get(USER_PREF, clazz);
    }

    @Override
    public <T> void setUserPref(T object) {
        set(USER_PREF, object);
    }

    @Override
    public void resetUserPref() {
        reset(USER_PREF);
    }
}
