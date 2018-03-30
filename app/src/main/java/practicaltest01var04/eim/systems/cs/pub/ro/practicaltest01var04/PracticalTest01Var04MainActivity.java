package practicaltest01var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    private int counter;
    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    private boolean serviceState;
    private IntentFilter ifilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("counter")) {
                counter = savedInstanceState.getInt("counter");
                Toast.makeText(this, "Saved value " + counter, Toast.LENGTH_SHORT).show();
            }
            else {
                counter = 0;
            }
        }
        else {
            counter = 0;
        }

        serviceState = false;

        ifilter.addAction("Just a message");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
//        salvez in counter valoarea
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("counter", counter);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        daca este salvata sa se refaca
        if (savedInstanceState.containsKey("counter")) {
            counter = savedInstanceState.getInt("counter");
        }
    }

    public void onClick(View view) {
        Button button = (Button) view;
        EditText bottom = (EditText) findViewById(R.id.bottom);
        String result;

        switch(button.getId()) {
            case R.id.topleft:
            case R.id.topright:
            case R.id.center:
            case R.id.bottomleft:
            case R.id.bottomright:
                result = bottom.getText().toString();
                if (result.length() != 0)
                    result += ", ";
                result += button.getText().toString();
                bottom.setText(result);
                counter++;

                Toast.makeText(this, "" + counter, Toast.LENGTH_SHORT).show();

                if (counter > 3 && !serviceState) {
                    Toast.makeText(this, "Stated Service", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),
                            PracticalTest01Var04Service.class);
                    intent.putExtra("text", bottom.getText().toString());
                    getApplicationContext().startService(intent);
                    registerReceiver(messageBroadcastReceiver, ifilter);
                    serviceState = true;
                }
                break;

            case R.id.navigate_to_secondary_activity_button:
                //am facut un intent cu a doua activitate (pagina)
                Intent intent = new Intent(getApplicationContext(),
                        PracticalTest01Var04SecondaryActivity.class);

//                i-am trimis rezultatul din textbox-ul de jos
                intent.putExtra("counterValue", bottom.getText().toString());

                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        EditText text = (EditText) findViewById(R.id.bottom);
        if(requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            if(resultCode == -1)
                Toast.makeText(this, "Secondary activity returned V", Toast.LENGTH_SHORT).show();
            else if(resultCode == 0){
                Toast.makeText(this, "Secondary activity returned C", Toast.LENGTH_SHORT).show();
            }
            counter = 0;
            text.setText("");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, ifilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(intent);
        serviceState = false;
    }


}
