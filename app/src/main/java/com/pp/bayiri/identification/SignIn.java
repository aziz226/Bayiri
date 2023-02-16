package com.pp.bayiri.identification;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.MainActivity;
import com.pp.bayiri.Politique;
import com.pp.bayiri.R;

public class SignIn extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private TextInputEditText signEmail;
    private EditText email, pass;
    private LinearLayout compteGoog;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    private Button btnCn;
    private String em, pa, userId;
    private DatabaseReference ref;
    private TextView politiq;
    private VideoView videoView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        compteGoog= findViewById(R.id.id_ll);
       // ignoreBtn= findViewById(R.id.id_ignor_btn);
        btnCn= findViewById(R.id.btn_con);
        politiq= findViewById(R.id.termCond);

        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc= GoogleSignIn.getClient(this, gso);

        mAuth= FirebaseAuth.getInstance();

        email= findViewById(R.id.con_email);
        pass= findViewById(R.id.con_pass);

        videoView=findViewById(R.id.videoview);
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bac);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
        });

        btnCn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(""+email.getText().toString().trim())){
                email.setError("Veuillez entrer votre email!");
            }else if (TextUtils.isEmpty(""+pass.getText().toString().trim())){
                pass.setError("Veuillez entrez votre mot de passe!");
            }else {
                ProgressDialog dialog= new ProgressDialog(this);
                dialog.setTitle("Connexion en cours");
                dialog.setMessage("Veuillez patienter...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),
                        pass.getText().toString().trim()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        userId= mAuth.getCurrentUser().getUid();
                        ref= FirebaseDatabase.getInstance().getReference().child(Constant.check)
                                .child(userId);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String etat= snapshot.child(Constant.checkEtat).getValue().toString();
                                    if (etat.equals("true")){
                                        Toast.makeText(SignIn.this, "Connexion réussie!",
                                                Toast.LENGTH_SHORT).show();
                                        HomeActivity();
                                        dialog.dismiss();
                                    }else{
                                        Intent intent= new Intent(SignIn.this, Politique.class);
                                        intent.putExtra("si", "google");
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                }else {
                                    Intent intent= new Intent(SignIn.this, Politique.class);
                                    intent.putExtra("si", "google");
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }else {
                        Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        compteGoog.setOnClickListener(view -> {
            // signIn();
            Intent signIntent= gsc.getSignInIntent();
            startActivityForResult(signIntent, RC_SIGN_IN);
        });

        politiq.setOnClickListener(v -> {
            startActivity(new Intent(this, Politique.class));
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== RC_SIGN_IN){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account= task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch (ApiException e){}
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                       ShowDialog();
                    } else {
                        String msg= task.getException().getMessage();
                        Toast.makeText(this, "Erreur: "+msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(SignIn.this,
                        "Erreur: "+e, Toast.LENGTH_SHORT).show());
    }

    private void HomeActivity() {
        Intent intent= new Intent(SignIn.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void SignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    private void ShowDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog_layout);
        dialog.setCancelable(false);

        Button btnTermNo = (Button) dialog.findViewById(R.id.btn_terms_no);
        Button btnTerm= (Button) dialog.findViewById(R.id.btn_terms);
        CheckBox checkBox= dialog.findViewById(R.id.checkBox);
        // if butnton is clicked, close the custom dialog

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (checkBox.isChecked()){
                btnTerm.setVisibility(View.VISIBLE);
                btnTermNo.setVisibility(View.GONE);
            }else {
                btnTerm.setVisibility(View.GONE);
                btnTermNo.setVisibility(View.VISIBLE);
            }
        });

        btnTerm.setOnClickListener(view -> {
            HomeActivity();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}