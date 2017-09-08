package com.znsoft.glsurfaceGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class znResourceManager {
    private Context mContext;

    public znResourceManager(Context context) {
        mContext = context;
    }

    public String loadStringResource(int resourceId) throws Exception {
        InputStream is = mContext.getResources().openRawResource(resourceId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return baos.toString();
    }

    private Bitmap bitmap;

    public Bitmap loadBitmapResource(int resourceId) {
        InputStream is = mContext.getResources().openRawResource(R.raw.lev1);

        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // Ignore.
            }
        }
        return bitmap;
    }

    public void recycleBitmap() {
        bitmap.recycle();
    }

}
