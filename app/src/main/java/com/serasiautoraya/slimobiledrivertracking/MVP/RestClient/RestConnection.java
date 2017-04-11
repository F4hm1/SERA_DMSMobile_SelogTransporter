package com.serasiautoraya.slimobiledrivertracking.MVP.RestClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.fragment.RequestHistoryFragment;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;
import com.serasiautoraya.slimobiledrivertracking.util.OverrideHurlStack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class RestConnection {

    private static RestConnection mRestConnection;
    private RequestQueue mRequestQueue;
    private int mStatusCode;
    private Context mContext;

    public RestConnection(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context);
        this.mContext = context;
    }

    public void postData(String transactionToken, String url, HashMap<String, String> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;
        Log.d("POST_TAGS", params.toString());
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", "6b22fb5969084b19a6c449cab37291fe");
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public void getServerTime(TimeRestCallBackInterface restCallback) {
        final TimeRestCallBackInterface restcall = restCallback;
        String tempLatitude = "";
        String tempLongitude = "";
        String tempAddress = "";

        if ((LocationServiceUtil.getLocationManager(mContext).getLastLocation() != null) &&
                LocationServiceUtil.getLocationManager(mContext).isGPSEnabled()) {
            tempLatitude = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocation().getLatitude();
            tempLongitude = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocation().getLongitude();
            tempAddress = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocationName();
        } else {
            restCallback.callBackOnFail("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
            return;
        }

        final String latitude = tempLatitude;
        final String longitude = tempLongitude;
        final String address = tempAddress;

        HashMap<String, String> params = new HashMap<>();
        params.put("lat", tempLatitude);
        params.put("lng", tempLongitude);
        params.put("username", "randinandra");

        GsonRequest<TimeRESTResponseModel> request = new GsonRequest<TimeRESTResponseModel>(
                Request.Method.GET,
                HelperUrl.GET_SERVER_LOCALTIME,
                TimeRESTResponseModel.class,
                null,
                params,
                new Response.Listener<TimeRESTResponseModel>() {
                    @Override
                    public void onResponse(TimeRESTResponseModel response) {
                        if (response != null) {
                            restcall.callBackOnSuccess(response, latitude, longitude, address);
                        } else {
                            restcall.callBackOnFail("Terjadi kesalahan, harap periksa koneksi anda, kemudian coba kembali");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail("Terjadi kesalahan, harap periksa koneksi anda, kemudian coba kembali");
                    }
                }
        );
        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public void getData(String transactionToken, String url, HashMap<String, String> params, RestCallBackInterfaceModel restCallBackInterfaceModel) {
        final RestCallBackInterfaceModel restcall = restCallBackInterfaceModel;
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Ocp-Apim-Subscription-Key", "6b22fb5969084b19a6c449cab37291fe");
        if (!transactionToken.equalsIgnoreCase("")) {
            headers.put("Authorization", transactionToken);
        }

        GsonRequest<BaseResponseModel> request = new GsonRequest<BaseResponseModel>(
                Request.Method.GET,
                url,
                BaseResponseModel.class,
                headers,
                params,
                new Response.Listener<BaseResponseModel>() {
                    @Override
                    public void onResponse(BaseResponseModel response) {
                        if (response != null) {
                            restcall.callBackOnSuccess(response);
                        } else {
                            restcall.callBackOnFail(response.getResponseText());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                    }
                }
        );
        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public void deleteData(String transactionToken, String url, HashMap<String, String> params, RestCallBackInterfaceModel restCallBackInterfaceModel) {
        url += "?";
        for (String key : params.keySet()) {
            url += key + "=" + params.get(key) + "&";
        }
        url = url.substring(0, url.length() - 1);

        final RestCallBackInterfaceModel restcall = restCallBackInterfaceModel;
        HashMap<String, String> params2 = new HashMap<>();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Ocp-Apim-Subscription-Key", "6b22fb5969084b19a6c449cab37291fe");
        if (!transactionToken.equalsIgnoreCase("")) {
            headers.put("Authorization", transactionToken);
        }

        GsonRequest<BaseResponseModel> request = new GsonRequest<BaseResponseModel>(
                Request.Method.DELETE,
                url,
                BaseResponseModel.class,
                headers,
                params2,
                new Response.Listener<BaseResponseModel>() {
                    @Override
                    public void onResponse(BaseResponseModel response) {
                        if (response != null) {
                            restcall.callBackOnSuccess(response);
                        } else {
                            restcall.callBackOnFail(response.getResponseText());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                    }
                }
        );
        request.setShouldCache(false);
        mRequestQueue.add(request);


//        final RestCallbackInterfaceJSON restcall = restCallBackInterfaceModel;
//        final String transactionTokenVar = transactionToken;
//        final HashMap<String, String> paramsVar = params;
//
//        HttpStack httpStack;
//        httpStack = new OverrideHurlStack();
//
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext, httpStack);
//
//        StringRequest request = new StringRequest(
//                Request.Method.DELETE,
//                url,
//                new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonResponse = new JSONObject(response);
//                    if(mStatusCode == 200){
//                        restcall.callBackOnSuccess(jsonResponse);
//                    }else {
//                        try {
//                            restcall.callBackOnFail(jsonResponse.getString("responseText"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                restcall.callBackOnFail(error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                mStatusCode = response.statusCode;
//                String jsonString = new String(response.data);
//                return  Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
//            }
//
//            @Override
//            public String getBodyContentType()
//            {
//                return "application/json";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//
//                headers.put("Content-Type", "application/json");
//                headers.put("Ocp-Apim-Subscription-Key", "6b22fb5969084b19a6c449cab37291fe");
//                if(!transactionTokenVar.equalsIgnoreCase("")){
//                    headers.put("Authorization", transactionTokenVar);
//                }
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams()
//            {
//                return paramsVar;
//            }
//
//            @Override
//            protected VolleyError parseNetworkError(VolleyError volleyError) {
//                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
//                    JSONObject jsonResponse = null;
//                    String responseText = "Terjadi Kesalahan";
//                    try {
//                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers)));
//                        responseText = jsonResponse.getString("responseText");
//                    } catch (UnsupportedEncodingException e) {
//                        return new VolleyError(e.getMessage());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    VolleyError error = new VolleyError(responseText);
//                    volleyError = error;
//                }
//                return volleyError;
//            }
//        };
//
//        request.setShouldCache(false);
//        requestQueue.add(request);
    }

    public void putData(String transactionToken, String url, HashMap<String, String> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", "6b22fb5969084b19a6c449cab37291fe");
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

}

