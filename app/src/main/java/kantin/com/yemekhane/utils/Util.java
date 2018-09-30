package kantin.com.yemekhane.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setContentView(R.layout.progress_dialog);
        return progressDialog;
    }

    public static void startProgressAnimation(ProgressDialog pd) {
        Objects.requireNonNull(pd.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AnimationDrawable ad = (AnimationDrawable) pd.findViewById(R.id.ivLoading).getBackground();
        ad.start();
    }

    public static void saveObject(Context context, Object obj, String fileName) {
        if (checkExternalStorage()) {
            try {
                FileOutputStream fos;
                fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isFileExists(Context c, String fileName) {
        return c.getFileStreamPath(fileName).isFile();
    }

    private static boolean checkExternalStorage() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        return (mExternalStorageAvailable && mExternalStorageWriteable);
    }

    public static Object loadObject(Context context, String fileName) {
        Object obj = null;
        try {
            if (isFileExists(context, fileName)) {
                FileInputStream fis = context.openFileInput(fileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = is.readObject();
                is.close();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
