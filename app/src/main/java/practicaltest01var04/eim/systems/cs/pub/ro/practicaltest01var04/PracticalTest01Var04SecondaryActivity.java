package practicaltest01var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        setContentView(R.layout.activity_practical_test01_var04_secondary);

        Intent intent = getIntent();
        EditText text = (EditText) findViewById(R.id.top);

        text.setText(intent.getStringExtra("counterValue"));

    }

    public void onClick(View view) {
        Button button = (Button) view;

        switch(button.getId()) {
            case R.id.verify:
                setResult(RESULT_OK, null);
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED, null);
                break;
        }

        finish();
    }
}
