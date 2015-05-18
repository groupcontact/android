package seaice.app.groupcontact.utils;

import android.content.Context;
import android.os.Environment;
import android.util.TypedValue;

public class AppUtils {

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static float getPix(Context context, float dip) {
        float pix = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return pix;
    }
}
