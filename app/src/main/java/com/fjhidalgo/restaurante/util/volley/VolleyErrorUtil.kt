package com.fjhidalgo.restaurante.util.volley

import android.util.Log
import com.android.volley.NoConnectionError
import com.android.volley.TimeoutError
import com.android.volley.VolleyError
import com.fjhidalgo.restaurante.data.AppConstants
import org.json.JSONException
import org.json.JSONObject

class VolleyErrorUtil {

    companion object {
        fun handleError(error: VolleyError): Pair<Int, String> {

            val networkResponse = error.networkResponse

            //Log.e("networkResponse",String(networkResponse.data))
            var statusCode = 0
            var errorMessage = "Unknown error"

            if (networkResponse == null) {
                if (error.javaClass == TimeoutError::class.java) {
                    errorMessage = "Request timeout"
                } else if (error.javaClass == NoConnectionError::class.java) {
                    errorMessage = "Failed to connect server"
                }
            } else if (networkResponse.data == null) {
                errorMessage = when (networkResponse.statusCode) {
                    404 -> "Resource not found"
                    401 -> "Please login again"
                    400 -> "Check your inputs"
                    500 -> "Something is getting wrong"
                    else -> "Unknown error"
                }
            } else {
                val result = String(networkResponse.data)
                try {
                    val response = JSONObject(result)
//                    var status: String
                    var message = ""
                    var errorDescription = ""

                    if (response.has("errors")) {
                        val errors = response.getJSONArray("errors")
                        //status = JSONObject(errors.get(0).toString()).getString("status")
                        message = try {
                            JSONObject(errors.get(0).toString()).getString("errorMessage")
                        } catch (ex: Exception) {
                            JSONObject(errors.get(0).toString()).getString("message")
                        }
                        statusCode = try {
                            JSONObject(errors.get(0).toString()).getInt("errorCode")
                        } catch (ex: Exception) {
                            JSONObject(errors.get(0).toString()).getInt("status")
                        }

                    } else {
                        Log.e("respone",response.toString())
                        errorDescription = response.getString("error_description")
//                        status = response.getString("status")
//                        message = response.getString("message")

                    }

                    Log.e("Error Status", statusCode.toString())
                    Log.e("Error Message", message)

                    when (networkResponse.statusCode) {
                        404 -> errorMessage = "Resource not found"
                        401 -> errorMessage = "$message Please login again"
                        400 -> errorMessage = "$message Check your inputs"
                        500 -> errorMessage = "$message Something is getting wrong"
                    }

                    if (errorDescription == AppConstants.USER_NOT_EXIST_MESSAGE){
                        errorMessage = AppConstants.USER_NOT_EXIST_MESSAGE
                    } else if (errorDescription == AppConstants.PASSWORD_DONT_MATCH_MESSAGE){
                        errorMessage = AppConstants.PASSWORD_DONT_MATCH_MESSAGE
                    }

                } catch (ex: JSONException) {
                    ex.printStackTrace()
                }
            }
            Log.e("Error", errorMessage)
            error.printStackTrace()

            return Pair(statusCode, errorMessage)
        }
    }
}