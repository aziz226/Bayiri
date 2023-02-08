package com.pp.bayiri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pp.bayiri.Admin.AdConnect;
import com.pp.bayiri.BottomNavFrag.LocationFrag;
import com.pp.bayiri.BottomNavFrag.VenteFrag;
import com.pp.bayiri.databinding.ActivityMainBinding;
import com.pp.bayiri.identification.SignIn;

public class MainActivity extends AppCompatActivity {

 /**   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    } **/

 private ActivityMainBinding mainBinding;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics analytics;

    private TextView btnAdmin, dec, email;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mToolbar= findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        drawerLayout= findViewById(R.id.drawer_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,drawerLayout ,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_ic);

        dec= findViewById(R.id.decon);
        navigationView= findViewById(R.id.navView);
        email= findViewById(R.id.email);
        btnAdmin= findViewById(R.id.dec);

        mAuth= FirebaseAuth.getInstance();
        analytics= FirebaseAnalytics.getInstance(this);

        ReplaceFrag(new LocationFrag());

        mainBinding.idBttnav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.id_loc:
                    ReplaceFrag(new LocationFrag());
                    break;
                case R.id.id_vent:
                    ReplaceFrag(new VenteFrag());
                    break;
               /* case R.id.id_aut:
                    ReplaceFrag(new AutreFrag());
                    break; */
            }
            return true;
        });

        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        gsc= GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser= mAuth.getCurrentUser();

        //Methode pour obtenir du email
        if (account!= null){
            String name= account.getDisplayName();
            String ema= account.getEmail();
            email.setText(ema);
        }else if (currentUser!= null){

            String n= currentUser.getEmail();
            email.setText(n);
        }

        dec.setOnClickListener(view -> {

            if (account!= null){
                gsc.signOut().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "deconnecter", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        Intent intent= new Intent(this, SignIn.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Erreur: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                mAuth.signOut();
                Intent intent= new Intent(this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        });

        btnAdmin.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AdConnect.class));

        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ReplaceFrag(Fragment fragment){
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        //.setReorderingAllowed(false);
        transaction.replace(R.id.id_framlay, fragment);
        transaction.commit();
    }

    public void ServiceClientele(View view){
        switch (view.getId()){
            case R.id.phone:
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

            case R.id.whaspp:
                Intent inte= new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/message/7XTQ3RXFJBRCH1"));
                startActivity(inte);
                break;
            case R.id.teleg:
                Intent in= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://t.me/Ximmo1"));
                startActivity(in);
                break;
            case R.id.e_mail:
               /* Intent email= new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("email"));
                email.putExtra(Intent.EXTRA_EMAIL, "tpplusservice@gmail.com");
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");

                Intent choose= Intent.createChooser(email, "lanuch Email");
                startActivity(choose); */

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "tpplusservice@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

                break;
            case R.id.facebook:
                Intent fac= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/profile.php?id=100087493126910&mibextid=ZbWKwL"));
                startActivity(fac);
                break;
            case R.id.twitter:
                Intent twi= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/SibiriKere?t=DoaFhX3Oiy_rAEMzISGhjA&s=09"));
                startActivity(twi);
                break;
            case R.id.link:
                Intent li= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/in/ximmo-agence-immobili%C3%A8re-96439a1b2"));
                startActivity(li);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if (currentUser== null){
            Intent intent= new Intent(this, SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

}