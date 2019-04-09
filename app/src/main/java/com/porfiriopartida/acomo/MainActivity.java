package com.porfiriopartida.acomo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.porfiriopartida.acomo.config.Config;
import com.porfiriopartida.acomo.utils.ContactsUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static int PERMISSION_REQUEST_CODE = 100;
    private TextView tv;
    private Switch switchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button isContactButton = findViewById(R.id.isContactButton);

        tv = findViewById(R.id.isContactTextView);
        switchView = findViewById(R.id.uknownCallerSwitch);

        isContactButton.setOnClickListener(getContactLookupClickListener());
        switchView.setOnClickListener(getSwitchClickListener());

        showPermissions();

        preloadPreferences();
    }

    private void preloadPreferences(){
        boolean enabled = Config.isCallMuteEnabled(this);
        switchView.setChecked(enabled);
    }

    private View.OnClickListener getSwitchClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.setCallMute(MainActivity.this, switchView.isChecked()) ;
            }
        };
    }
    private View.OnClickListener getContactLookupClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView textView = findViewById(R.id.numberTextView);
                String number = textView.getText().toString();
                String contactName = ContactsUtils.retrieveContactName(MainActivity.this, number);
                if(contactName == null){
                    tv.setText("Not a contact, if calls they will be ignored.");
                } else {
                    tv.setText(String.format("This is a contact: %s", contactName));
                }
            }
        };
    }

    protected void showPermissions() {
        List<String> permissionsList = new ArrayList<String>();
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_CONTACTS);
        }

        if(permissionsList.size() > 0){
            ActivityCompat.requestPermissions(this,
                    permissionsList.toArray(new String[]{}),
                    PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }
        }
    }
}
