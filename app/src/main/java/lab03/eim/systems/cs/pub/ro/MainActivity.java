package lab03.eim.systems.cs.pub.ro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton callButton, hangupButton, backspaceButton;
    private EditText phoneNumberEditText;
    private Button numberButton;
    final public static int PERMISSION_REQUEST_CALL_PHONE = 1;

    int[] numberButtonIds = {R.id.nr_0_button,
            R.id.nr_1_button,
            R.id.nr_2_button,
            R.id.nr_3_button,
            R.id.nr_4_button,
            R.id.nr_5_button,
            R.id.nr_6_button,
            R.id.nr_7_button,
            R.id.nr_8_button,
            R.id.nr_9_button};

    private CallImageButtonClickListener callImageButtonClickListener = new CallImageButtonClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        backspaceButton = (ImageButton) findViewById(R.id.backspace);
        backspaceButton.setOnClickListener(backspaceButtonClickListener);

        callButton = (ImageButton) findViewById(R.id.call_image_button);
        callButton.setOnClickListener(callImageButtonClickListener);

        hangupButton = (ImageButton) findViewById(R.id.hangup_image_button);
        hangupButton.setOnClickListener(hangupImageButtonClickListener);


        phoneNumberEditText = (EditText)findViewById(R.id.phone_number);

        for (int id: numberButtonIds) {
            numberButton = (Button)findViewById(id);
            numberButton.setOnClickListener(genericButtonClickListener);
        }

        numberButton = (Button)findViewById(R.id.star_button);
        numberButton.setOnClickListener(genericButtonClickListener);
        numberButton = (Button)findViewById(R.id.count_button);
        numberButton.setOnClickListener(genericButtonClickListener);
    }


    private class CallImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupImageButtonClickListener hangupImageButtonClickListener = new HangupImageButtonClickListener();
    private class HangupImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    }

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());
        }
    }
}
