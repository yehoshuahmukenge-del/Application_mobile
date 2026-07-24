package cd.esforca.cliniquedor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public class ImageUtils {
    public static String saveImageToInternalStorage(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            
            String fileName = "patient_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(context.getFilesDir(), fileName);
            
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteImage(String path) {
        if (path != null) {
            File file = new File(path);
            if (file.exists()) file.delete();
        }
    }
}
