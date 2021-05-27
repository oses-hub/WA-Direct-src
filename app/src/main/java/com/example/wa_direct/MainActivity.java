package com.example.wa_direct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button sendMessageButton = findViewById(R.id.StartChatButton);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }

            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                void handleSendText(Intent intent) {
                    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if (sharedText != null) {
                        // Update UI to reflect text being shared
                    }
                }

            public void WADirect(){
                EditText PhoneNumber = (EditText)findViewById(R.id.PhoneNumberTextBox);
                CountryCodePicker cpp = (CountryCodePicker) findViewById(R.id.ccp);
                cpp.registerCarrierNumberEditText(PhoneNumber);
                String phoneNumber = cpp.getFullNumber();
                boolean installed = isAppInstalled("com.whatsapp");
                if(installed) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(MainActivity.this,"Whatsapp is not installed on your device",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClick(View v) {
                WADirect();
            }

            public boolean isAppInstalled(String packageName) {
                PackageManager pm = getPackageManager();
                boolean app_installed;
                try {
                    pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                    app_installed = true;
                }
                catch (PackageManager.NameNotFoundException e) {
                    app_installed = false;
                }
                return app_installed;
            }
        });
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            WADirect_share(sharedText);
        }
    }

    private void WADirect_share(String sharedText) {
        boolean installed = isAppInstalled_for_share_action("com.whatsapp");
        if(installed) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + sharedText));
            startActivity(browserIntent);
        } else {
            Toast.makeText(MainActivity.this,"Whatsapp is not installed on your device",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAppInstalled_for_share_action(String s) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}



