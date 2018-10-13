package kantin.com.yemekhane.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import kantin.com.yemekhane.R;
import kantin.com.yemekhane.model.CodeModel;
import kantin.com.yemekhane.model.searchModel.SearchListModel;

public class Services {
    private static final String TAG = Services.class.getName();
    @SuppressLint("StaticFieldLeak")
    private static final Services mInstance = new Services();
    private static int mWaitingRequestCount = 0;
    private RequestQueue mRequestQueue;
    private ProgressDialog progressDialog;
    private Context mContext;
    private static final String REST_SERVICE = "http://kantin.daxstyle.com";

    private final Response.ErrorListener volleyErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                VolleyLog.e("Error: ", error.getMessage());
                int message = R.string.data_not_found;
                if (error instanceof NoConnectionError)
                    message = R.string.message_connect_to_internet;
                else if (error instanceof TimeoutError) {
                    message = R.string.time_out;
                }

                Util.showToast(mContext.getApplicationContext(), message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };

    private final RetryPolicy timeoutPolicy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private final RequestQueue.RequestFinishedListener<Object> mRequestFinishedListener = new RequestQueue.RequestFinishedListener<Object>() {
        @Override
        public void onRequestFinished(Request<Object> request) {
            mWaitingRequestCount--;
            if (mWaitingRequestCount == 0) {
                try {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                } catch (Exception pass) {
                    //pass
                }
            }
        }
    };

    public static synchronized Services getInstance() {
        return mInstance;
    }

    private void showProgress(Context context) {
        mContext = context;
        if (progressDialog == null) {
            createProgress();
        } else {
            if (!progressDialog.getContext().getClass().equals(mContext.getClass())) {
                progressDialog.dismiss();
                createProgress();
            } else
                progressDialog.show();
        }
        Util.startProgressAnimation(progressDialog);
    }

    private void createProgress() {
        progressDialog = Util.createProgressDialog(mContext);
        progressDialog.setOnCancelListener(dialog -> cancelAllRequests());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
            mRequestQueue.addRequestFinishedListener(mRequestFinishedListener);
        }
        return mRequestQueue;
    }

    private <T> void add(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(timeoutPolicy);
        getRequestQueue().add(req);
        mWaitingRequestCount++;
    }

    public interface OnFinishListener {
        void onFinish(Object obj);
    }

    private void cancelAllRequests() {
        mRequestQueue.cancelAll(TAG);
    }

    public void getStudentList(String words, OnFinishListener ofl, Boolean showErrors) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonObject.put("action", "getListWithSchoolNumber");
            jsonBody.put("searchKeyword", words);
            jsonObject.put("params", jsonBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            GsonRequest<SearchListModel> gsonRequest = new GsonRequest<>(Request.Method.POST, REST_SERVICE, SearchListModel.class, jsonObject, null, ofl::onFinish, showErrors ? volleyErrorListener : null);
            add(gsonRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void getSavedStudentList(Context context, OnFinishListener ofl, Boolean showErrors) {
        showProgress(context);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonObject.put("action", "getListForSelectedList");
            jsonObject.put("params", jsonBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            GsonRequest<SearchListModel> gsonRequest = new GsonRequest<>(Request.Method.POST, REST_SERVICE, SearchListModel.class, jsonObject, null, ofl::onFinish, showErrors ? volleyErrorListener : null);
            add(gsonRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addAndDelete(Context context, int id, String menu, Boolean isSelected, int paymentMethod, String note, OnFinishListener ofl, Boolean showErrors) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonObject.put("action", "insertMenu");
            jsonBody.put("id", id);
            jsonBody.put("menu", menu);
            jsonBody.put("isSelected", isSelected);
            jsonBody.put("note", note);
            jsonBody.put("paymentPeriod", paymentMethod);
            jsonObject.put("params", jsonBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            GsonRequest<CodeModel> gsonRequest = new GsonRequest<>(Request.Method.POST, REST_SERVICE, CodeModel.class, jsonObject, null, ofl::onFinish, showErrors ? volleyErrorListener : null);
            add(gsonRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void deleteAll(Context context, String idList, OnFinishListener ofl, Boolean showErrors) {
        showProgress(context);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonObject.put("action", "deleteStudentListWithId");
            jsonBody.put("idList", idList);
            jsonObject.put("params", jsonBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            GsonRequest<CodeModel> gsonRequest = new GsonRequest<>(Request.Method.POST, REST_SERVICE, CodeModel.class, jsonObject, null, ofl::onFinish, showErrors ? volleyErrorListener : null);
            add(gsonRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void changeMenuForMember(Context context, String id, String menu, OnFinishListener ofl, Boolean showErrors) {
        showProgress(context);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonObject.put("action", "insertMenuForMember");
            jsonBody.put("id", id);
            jsonBody.put("menu", menu);
            jsonObject.put("params", jsonBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            GsonRequest<CodeModel> gsonRequest = new GsonRequest<>(Request.Method.POST, REST_SERVICE, CodeModel.class, jsonObject, null, ofl::onFinish, showErrors ? volleyErrorListener : null);
            add(gsonRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addNote(Context context, int id, String note, OnFinishListener ofl, Boolean showErrors) {
        showProgress(context);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonObject.put("action", "updateStudentNote");
            jsonBody.put("id", id);
            jsonBody.put("note", note);
            jsonObject.put("params", jsonBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            GsonRequest<CodeModel> gsonRequest = new GsonRequest<>(Request.Method.POST, REST_SERVICE, CodeModel.class, jsonObject, null, ofl::onFinish, showErrors ? volleyErrorListener : null);
            add(gsonRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
