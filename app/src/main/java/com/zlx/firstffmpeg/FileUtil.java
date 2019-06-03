package com.zlx.firstffmpeg;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @date: 2019\6\3 0003
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class FileUtil {

    /**
     * 将asset文件写入缓存
     */
    public static boolean copyAssetAndWrite(Context context,File outFile,String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return true;
        } catch (IOException e) {
            Log.e("TAG",e.toString());
        }

        return false;
    }
}
