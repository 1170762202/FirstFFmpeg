package com.zlx.firstffmpeg;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String VIDEO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/mrd/video";

    static {
        createFile(VIDEO_SAVE_DIR);
        Log.e("TAG", "create");
    }

    private static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static final String TAG = MainActivity.class.getName();

    private String mTargetPath;
    private ArrayList<String> mMediaPath;

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.video_view);

//        MediaController mMediaController = new MediaController(this);
//        mVideoView.setMediaController(mMediaController);

    }

    /**
     * 提取视频
     */
    private void extractVideo() {
        final String outVideo = VIDEO_SAVE_DIR + File.separator + "video.mp4";
        String targetPath = VIDEO_SAVE_DIR + File.separator + "demo.mp4";

        String[] commands = FFmpegCommands.extractVideo(targetPath, outVideo);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                mMediaPath = new ArrayList<>();
                Log.e(TAG, "extractVideo ffmpeg start...");
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG, "extractVideo ffmpeg end...");
                mMediaPath.add(outVideo);
//                extractAudio();
            }
        });
    }

    /**
     * 提取音频
     */
    private void extractAudio() {
        final String outVideo = mTargetPath + "/audio.aac";
        String[] commands = FFmpegCommands.extractAudio(getIntent().getStringExtra("path"), outVideo);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                Log.e(TAG, "extractAudio ffmpeg start...");

            }


            @Override
            public void onEnd(int result) {
                Log.e(TAG, "extractAudio ffmpeg end...");
                mMediaPath.add(outVideo);

            }
        });
    }

    public void save(View view) {
        toast("保存中");
        String targetPath = VIDEO_SAVE_DIR + File.separator + "demo.mp4";
        boolean save = FileUtil.copyAssetAndWrite(MainActivity.this, new File(targetPath), "demo.mp4");
        Log.e(TAG, "save: " + save + "   " + targetPath);
        mVideoView.setVideoPath(targetPath);
        toast("保存成功");
    }

    public void play(View view) {
        String targetPath = VIDEO_SAVE_DIR + File.separator + "demo.mp4";
        mVideoView.setVideoPath(targetPath);
        mVideoView.start();
    }

    public void pause(View view) {
        mVideoView.pause();
    }

    public void stop(View view) {
        mVideoView.stopPlayback();
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void extractVideo(View view) {
        extractVideo();
    }
}
