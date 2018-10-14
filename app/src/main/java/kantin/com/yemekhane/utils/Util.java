package kantin.com.yemekhane.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Objects;

import kantin.com.yemekhane.R;

public class Util {

    public static void showToast(Context context, int message) {
        showToast(context, context.getString(message));
    }

    private static void showToast(Context context, String message) {
        Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setContentView(R.layout.progress_dialog);
        return progressDialog;
    }

    static void startProgressAnimation(ProgressDialog pd) {
        Objects.requireNonNull(pd.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AnimationDrawable ad = (AnimationDrawable) pd.findViewById(R.id.ivLoading).getBackground();
        ad.start();
    }


}
