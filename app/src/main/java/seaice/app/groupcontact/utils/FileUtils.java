package seaice.app.groupcontact.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static Long lastWrite = System.currentTimeMillis();

    // 至少间隔10s才写一次
    private static Long writeGap = 10000L;

    public static <T> void write(Context context, String path, List<T> data, Class<T> typedClass,
                                 boolean force) {
        if (!force) {
            Long t = System.currentTimeMillis();
            if ((t - lastWrite) < writeGap) {
                return;
            }
            lastWrite = t;
        }
        try {
            FileOutputStream outputStream = context.openFileOutput(path, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(ParseUtils.toListString(data, typedClass));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // Make sure it never happends
        }
    }

    public static <T> List<T> read(Context context, String path, Class<T> typedClass) {
        try {
            StringBuilder str = new StringBuilder();
            FileInputStream inputStream = context.openFileInput(path);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = input.readLine()) != null) {
                str.append(line);
            }
            input.close();
            return ParseUtils.fromListString(str.toString(), typedClass);
        } catch (Exception e) {
            // Make sure it never happends
        }
        return new ArrayList<>();
    }
}
