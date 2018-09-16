package kantin.com.yemekhane.utils;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ussal Software on 21.05.2018.
 */
public class GsonArrayRequest<T> extends JsonRequest<ArrayList<T>> {
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<ArrayList<T>> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param clazz   Relevant class object, for Gson's reflection
     * @param url     URL of the request to make
     * @param headers Map of request headers
     */
    public GsonArrayRequest(int method, String url, Class<T> clazz, JSONObject postData, Map<String, String> headers,
                            Response.Listener<ArrayList<T>> listener, Response.ErrorListener errorListener) {
        super(method, url, (postData == null) ? "" : postData.toString(), listener, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = headers != null ? headers : new HashMap<>();
        params.put("Content-Type", "application/json; charset=utf-8");
        return params;
    }


    @Override
    protected Response<ArrayList<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));

            Type listType = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, clazz);
            ArrayList<T> tList = gson.fromJson(json, listType);
            return Response.success(
                    tList,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<T> response) {
        listener.onResponse(response);
    }
}