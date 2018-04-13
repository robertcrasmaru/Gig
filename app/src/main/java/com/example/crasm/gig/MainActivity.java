package com.example.crasm.gig;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Button buttonSignIn,buttonRegister;
    RelativeLayout baseLayout;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                      .setDefaultFontPath("fonts/Arkhip_font.ttf")
                                       .setFontAttrId(R.attr.fontPath)
                                        .build());
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonSignIn = (Button)findViewById(R.id.buttonSignIn);
        baseLayout = (RelativeLayout)findViewById(R.id.root_layout);

        buttonRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showRegisterForm();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showLoginForm();
            }
        });

    }

    private void showRegisterForm(){

        AlertDialog.Builder form = new AlertDialog.Builder(this);
        form.setTitle("REGISTER");
        form.setMessage("Register with email");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.register_form,null);

        final MaterialEditText editEmail = (MaterialEditText) register_layout.findViewById(R.id.editEmail);
        final MaterialEditText editName = (MaterialEditText) register_layout.findViewById(R.id.editName);
        final MaterialEditText editPassword = (MaterialEditText) register_layout.findViewById(R.id.editPassword);
        final MaterialEditText editPhone = (MaterialEditText) register_layout.findViewById(R.id.editPhone);

        form.setView(register_layout);
        //AlertDialog build = form.create();
        form.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();




                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    Snackbar.make(baseLayout,"Please enter email", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(baseLayout,"Please enter password", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(editName.getText().toString())){
                    Snackbar.make(baseLayout,"Please enter name", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(editPhone.getText().toString())){
                    Snackbar.make(baseLayout,"Please enter phone number", Snackbar.LENGTH_SHORT).show();
                    return;
                }



            }
        });



        form.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        form.show();

    }


    private void showLoginForm(){

        AlertDialog.Builder form = new AlertDialog.Builder(this);
        form.setTitle("LOGIN");
        form.setMessage("Login with email");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.login_form,null);

        final MaterialEditText editEmail = (MaterialEditText) login_layout.findViewById(R.id.editEmail);
        final MaterialEditText editName = (MaterialEditText) login_layout.findViewById(R.id.editName);
        final MaterialEditText editPassword = (MaterialEditText) login_layout.findViewById(R.id.editPassword);
        MaterialEditText editPhone = (MaterialEditText) login_layout.findViewById(R.id.editPhone);

        form.setView(login_layout);

        form.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                buttonSignIn.setEnabled(false);

                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    Snackbar.make(baseLayout,"Please enter email", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(baseLayout,"Please enter password", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                final SpotsDialog waitDialog = new SpotsDialog(MainActivity.this);

                waitDialog.show();

                auth.signInWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitDialog.dismiss();
                                startActivity(new Intent(MainActivity.this,Welcome.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitDialog.dismiss();
                        Snackbar.make(baseLayout,"Failed"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        buttonSignIn.setEnabled(true);
                    }
                });



            }

        });

        form.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        form.create().show();


    }
}
