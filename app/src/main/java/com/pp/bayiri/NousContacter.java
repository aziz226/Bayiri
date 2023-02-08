package com.pp.bayiri;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class NousContacter extends AppCompatActivity {

    private ImageView whats;
    String choix, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nous_contacter);

        whats= findViewById(R.id.id_whats);
        
        Bundle b= getIntent().getExtras();
        choix= b.getString("cx");
        code= b.getString("c");

       /* Toast.makeText(this, "Je vous écris à propos "+choix+" du code "+code
                , Toast.LENGTH_SHORT).show(); */
    }

    public void Contact(View view) {
        switch (view.getId()){
            case R.id.id_appel:
                Intent intent= new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+22675677838"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }else {
                    startActivity(intent);
                }
                break;
            case R.id.id_whats:

                Intent in= new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/message/7XTQ3RXFJBRCH1"));
                startActivity(in);

                break;
            case R.id.id_teleg:

                Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/ximmo0022675677838"));
                startActivity(i);
                break;
        }
    }

    private boolean WhatsappInstalled() {
        PackageManager manager= getPackageManager();
        boolean whatsappInstalled;

        try {
            manager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled= true;
        }catch (PackageManager.NameNotFoundException e){
            whatsappInstalled= false;
        }

        return whatsappInstalled;
    }
}