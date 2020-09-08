package com.nerdsboard.softexperttask;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerdsboard.softexperttask.Utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


class HttpCalls {

    private static final String LOG_TAG = "HttpCalls";

    public interface OnResponseReceived{
        void onSuccess(String successResponse);
        void onError(String errorMessage);
    }

    // Helper method to GET
    static void get(final Context context, String apiUrl, final OnResponseReceived onResponseReceived){

        boolean isConnected = checkInternetConnectivity(context);
        if(!isConnected){
            Toast.makeText(context, "Internet connection error", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int jsonStatus = jsonObject.getInt("status");

                            if(jsonStatus == 1){
                                onResponseReceived.onSuccess(response);
                            }else{
                                JSONObject jsonError = jsonObject.getJSONObject("erroe");
                                String jsonMessage = jsonError.getString("msg");
                                onResponseReceived.onError(jsonMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.e(getClass().getName(),"response = " + response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(LOG_TAG,"error =" + error);

                        if (networkResponse != null && networkResponse.data != null) {
                            String responseJson = new String(networkResponse.data);
                            String jsonError = "Error";

                            Log.e(LOG_TAG,"jsonError =" + responseJson);

                            //TODO parsing error

                            onResponseReceived.onError(jsonError);
                        }else{
                            onResponseReceived.onError("Error");
                        }
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json; charset=UTF-8");

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private static boolean checkInternetConnectivity(Context context) {
        return NetworkUtil.isInternetConnected(context);
    }

}

