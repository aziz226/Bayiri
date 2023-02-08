package com.pp.bayiri.identification;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.Politique;
import com.pp.bayiri.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private TextInputEditText email, pass, cPass; //userN
    private String un, em, pas, cPas;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mAuth= FirebaseAuth.getInstance();

        //userN= findViewById(R.id.cre_usn);
        email= findViewById(R.id.cre_email);
        pass= findViewById(R.id.cre_pass);
        cPass= findViewById(R.id.conf_pas);
        btn= findViewById(R.id.btn_cre);

        loadingBar= new ProgressDialog(this);



        /*if (TextUtils.isEmpty(""+un)){
            userN.setError("Veuillez entrer votre nom et prenom!");
        }else */
        btn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(""+email.getText().toString().trim())){
                email.setError("Veuillez entrer votre email!");
            }else  if (TextUtils.isEmpty(""+pass.getText().toString().trim())){
                pass.setError("Veuillez entrer votre mot de passe!");
            }else  if (TextUtils.isEmpty(""+cPass.getText().toString().trim())){
                cPass.setError("Le mot de passe ne correspond pas");
            }else {
                loadingBar.setTitle("Création du compte en cours");
                loadingBar.setMessage("Veuillez patienter...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                ShowDialog(email.getText().toString().trim(), pass.getText().toString().trim());
            }
        });
    }

    private void ShowDialog(String trim, String trim1) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog_layout);
        dialog.setCancelable(true);

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

            loadingBar.setTitle("Création du compte en cours");
            loadingBar.setMessage("Veuillez patienter...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(trim, trim1)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(this, "Création du compte réussie!", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(SignUp.this, SignIn.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            loadingBar.dismiss();
                            dialog.dismiss();

                        }else {
                            Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });

        });

        dialog.show();
    }
}