package com.fjhidalgo.restaurante.data.preferences;

public interface PreferenceHelper {
    <T> T getUserPref(Class<T> clazz);

    <T> void setUserPref(T object);

    void resetUserPref();
}
