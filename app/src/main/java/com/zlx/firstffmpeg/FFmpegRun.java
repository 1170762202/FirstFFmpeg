package com.zlx.firstffmpeg;

import android.os.AsyncTask;

/**
 * @date: 2019\5\31 0031
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class FFmpegRun {
    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeginvoke");
    }

    public static void execute(String[] commands, final FFmpegRunListener fFmpegRunListener) {
        new AsyncTask<String[], Integer, Integer>() {
            @Override
            protected void onPreExecute() {
                if (fFmpegRunListener != null) {
                    fFmpegRunListener.onStart();
                }
            }

            @Override
            protected Integer doInBackground(String[]... params) {
                return run(params[0]);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if (fFmpegRunListener != null) {
                    fFmpegRunListener.onEnd(integer);
                }
            }
        }.execute(commands);
    }

    public native static int run(String[] commands);


    public interface FFmpegRunListener{
        void onStart();
        void onEnd(int result);
    }
}
