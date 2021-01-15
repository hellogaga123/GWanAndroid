package com.gaga.gwanandroid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import androidx.annotation.NonNull;

/**
 * @Author Gaga
 * @Date 2020/8/19 14:52
 * @Description
 */
class HandlerTest {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };
    private void foo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Message message=Message.obtain();
                handler.sendMessage(message);
            }
        }).start();
    }
}
