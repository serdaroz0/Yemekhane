package kantin.com.yemekhane.utils;

import android.content.Context;
import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import kantin.com.yemekhane.model.PersonModel;

public class Util {
    public static ArrayList<PersonModel> loadCards(Context context, ArrayList<PersonModel> cardModels) {
        cardModels = new ArrayList<>();

        try {
            Object cards = Util.loadObject(context, "PersonModel.obj");

            if (cards != null)
                cardModels = (ArrayList<PersonModel>) cards;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cardModels;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static boolean isFileExists(Context c, String fileName) {
        return c.getFileStreamPath(fileName).isFile();
    }
    public static void saveObject(Context context, Object obj, String fileName) {
        if (checkExternalStorage()) {
            try {
                FileOutputStream fos;
                fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean checkExternalStorage() {
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
    public static ArrayList<PersonModel> loadCardsSaved(Context context, ArrayList<PersonModel> cardModels) {
        cardModels = new ArrayList<>();

        try {
            Object cards = Util.loadObject(context, "PersonModelSaved.obj");

            if (cards != null)
                cardModels = (ArrayList<PersonModel>) cards;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cardModels;
    }
}
