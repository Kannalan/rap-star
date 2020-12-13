package com.example.playandrecodedemo;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 权限移动到项目mainacticity
 * 45-47line 路径
 * decode参数路径
 * playsound资源路径
 * 需修改
 */
public class MainActivity extends AppCompatActivity {
    private Button btnBegin;
    private Button btnStop;
    private Button btnMix;

    private File recordFile = null; // 录音文件夹
    private File decodeFile = null; // 解码文件夹
    private File mixFile = null; // 合音文件夹

    private String recordPath = "/sdcard/merge/record"; // 录音的路径
    private String decodePath = "/sdcard/merge/decode";//解码的路径
    private String mixPath = "/sdcard/merge/mix";//合音的路径

    private String recordFileName = "";
    private String decodeFileName = "";
    private String mixFileName = "";

    private MediaRecorder mRecorder;    //定义录制音频的MediaRecorder属性
    private MediaPlayer mPlayer;    //定义播放伴奏的MediaPlayer属性

    private ProgressDialog dialog;
    private List<RecordBean> list = new ArrayList<RecordBean>();

    //    private String path;
    private Handler myHandler;
    //  private String rawAudioFile;


    //定义AssetManager属性
    private AssetManager assetManager;
    //定义标识当前音频的id属性
    private int cur = 1;
    private String name = "lwt";
    private String onepath;
    private String twopath;

    /**
     * 权限
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int GET_RECODE_AUDIO = 1;
    private static String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_AUDIO,
                    GET_RECODE_AUDIO);
        }
        int permissionwrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionwrite != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态申请权限
        verifyStoragePermissions(MainActivity.this);
        //检测sd卡权限
        /*File sd = getExternalFilesDir(null);
        boolean can_write = sd.canWrite();
        Log.i("【】【】【】【】【】【",can_write+"");*/

        Initdata();

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放伴奏
                playSound();
                //点击录制音频
                startRecord();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止录制
                stopRecord();
                //解码录音文件
                decodefile(recordPath + "/" + recordFileName, decodePath + "/" + decodeFileName);
                //解码伴奏文件
                //decodefile("/sdcard/merge/record/seven.m4a","/sdcard/merge/decode/back.pcm");
            }
        });
        btnMix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordBean r1 = new RecordBean(1,decodeFileName,"",1,decodePath + "/" + decodeFileName);
                RecordBean r2 = new RecordBean(2,"back.pcm","",1,"/sdcard/merge/decode/back.pcm");
                list.add(r1);
                list.add(r2);
                dialog.setMessage("合音中...");
                dialog.show();
                new MixTask(2).execute();
                Log.i("mix", "mix");
            }
        });


        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 2://下载数据完成，将下载好的数据显示在页面上
                        //1. 当解码完成后开始录音和播放
                        String str = (String) msg.obj;
                        Log.e("hahh", str);
                        break;
                }
            }
        };
    }

    /**
     * 初始化
     */
    public void Initdata() {
        btnBegin = findViewById(R.id.btn_begin);
        btnStop = findViewById(R.id.btn_stop);
        btnMix = findViewById(R.id.btn_mix);
        recordFile = new File(recordPath);
        if (!recordFile.exists()) {
            recordFile.mkdirs();
        }
        decodeFile = new File(decodePath);
        if (!decodeFile.exists()) {
            decodeFile.mkdirs();
        }
        mixFile = new File(mixPath);
        if (!mixFile.exists()) {
            mixFile.mkdirs();
        }
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        recordFileName = new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(date) + ".mp4";
        decodeFileName = new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(date) + ".pcm";
        mixFileName = new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(date) + ".aac";
        Log.i("【】【】【", recordFileName + "+" + decodeFileName+"+"+mixFileName);
    }

    /**
     * 停止录音
     */
    private void stopRecord() {
        mRecorder.stop();
        mRecorder.release();
        mPlayer.stop();
        mPlayer.release();
    }


    /**
     * 开始录制音频
     */
    private void startRecord() {
        Log.i("【】【】】【】【】", "begin");
        mRecorder = new MediaRecorder();
        mRecorder.reset();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置声音来源
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 设置所录制的音视频文件的格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 设置所录制的声音的编码格式
        mRecorder.setAudioEncodingBitRate(96000);// 比特率
        mRecorder.setAudioChannels(2);// 通道
        mRecorder.setAudioSamplingRate(44100);// 采样率
        mRecorder.setOutputFile(recordPath + "/" + recordFileName);// 设置录制的音频文件的保存位置
        Log.i("【】【】【【】", recordPath + "/" + recordFileName);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
    }

    /**
     * 开始播放音频
     */
    private void playSound() {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                initMediaPlayer();
            }
        }.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initMediaPlayer() {
        //MediaPlayer对象初始化
        mPlayer = new MediaPlayer();
        //AssetManager对象获取
        assetManager = getAssets();
        try {
            AssetFileDescriptor descriptor =
                    assetManager.openFd("even.mp3");
            //给MediaPlayer对象设置音频数据源为当前MP3
            mPlayer.setDataSource(descriptor);
            //加载准备好
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给录音解码
     */
    private void decodefile(final String path, final String newpath) {
        new Thread() {
            @Override
            public void run() {
                //要解码路径
                AudioDecoder audioDec = AudioDecoder
                        .createDefualtDecoder(path);
                try {
                    audioDec.decodeToFile(newpath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = myHandler.obtainMessage();
                //设置Message对象的属性（what、obj）
                msg.what = 1;
                msg.obj = "哈哈哈哈";
                //发送Message对象
                myHandler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * 合音
     */
    class MixTask extends AsyncTask<Void, Double, Boolean> {

        private int size;

        MixTask(int num) {
            size = num;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String rawAudioFile = null;

            // 将需要合音的音频解码后的文件放到数组里
            File[] rawAudioFiles = new File[size];
            StringBuilder sbMix = new StringBuilder();
            int index = 0;

            for (int i = 0; i < list.size(); i++) {
                if (1 == list.get(i).getState()) {
                    rawAudioFiles[index++] = new File(list.get(i)
                            .getDecodePath());
                    sbMix.append(i + "");
                }
            }

            // 最终合音的路径
            final String mixFilePath = mixFile.getAbsolutePath() + "/mix"
                    + MD5Util.getMD5Str(sbMix.toString());
            Log.i("【】【】【】【】", mixFilePath);

            // 下面的都是合音的代码
            try {
                MultiAudioMixer audioMixer = MultiAudioMixer.createAudioMixer();
                Log.i("【】【】【】【】", "1111");
                audioMixer.setOnAudioMixListener(new MultiAudioMixer.OnAudioMixListener() {

                    FileOutputStream fosRawMixAudio = new FileOutputStream(
                            mixFilePath);

                    @Override
                    public void onMixing(byte[] mixBytes) throws IOException {
                        fosRawMixAudio.write(mixBytes);
                    }

                    @Override
                    public void onMixError(int errorCode) {
                        try {
                            if (fosRawMixAudio != null)
                                fosRawMixAudio.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMixComplete() {
                        try {
                            if (fosRawMixAudio != null)
                                fosRawMixAudio.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
/*-------------------------------------------------------------------
            MMultiAudioMixer mm = null;
            try {
                mm = new MMultiAudioMixer();
                Log.i("【】【】【】【】","mmok");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.i("【】【】【】【】","22222");
            audioMixer.setOnAudioMixListener(mm);*/
//----------------------------------------------------
                audioMixer.mixAudios(rawAudioFiles);
                int xx = rawAudioFiles.length;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            rawAudioFile = mixFilePath;
            AudioEncoder accEncoder = AudioEncoder
                    .createAccEncoder(rawAudioFile);

            String finalMixPath = mixFile.getAbsolutePath()+"/"+mixFileName;
            accEncoder.encodeToFile(finalMixPath);
            return true;

        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), "合音成功", Toast.LENGTH_SHORT)
                    .show();
            dialog.cancel();
        }
    }

}