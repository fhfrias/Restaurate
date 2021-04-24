package com.fjhidalgo.restaurante.util.volley;

import androidx.core.util.Pair;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

public interface VolleyCallback {

    void onResponse(Pair<JSONObject, Map<String, String>> response);

    void onError(VolleyError error);
}
