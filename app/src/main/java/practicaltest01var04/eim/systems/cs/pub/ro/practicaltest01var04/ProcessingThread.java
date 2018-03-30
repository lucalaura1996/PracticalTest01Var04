package practicaltest01var04.eim.systems.cs.pub.ro.practicaltest01var04;

import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    String text;

    public ProcessingThread(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("Message");
        intent.putExtra("Message", "message");

        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}