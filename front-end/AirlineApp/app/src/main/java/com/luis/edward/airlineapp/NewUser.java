package com.luis.edward.airlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;


public class NewUser extends AppCompatActivity {

    public void createUser(View view){
        final EditText newName= findViewById(R.id.EditTextInput_name);
        final EditText newLastName= findViewById(R.id.EditTextInput_Lastname);
        final EditText newEmail= findViewById(R.id.EditTextInput_email);
        final EditText newPassword= findViewById(R.id.EditTextInput_password);

        //--------------------Bloque para bajar users de API
        /*URL url=null;
        try {
            URL url2 = new URL("http://localhost:3000/users");
            url=url2;
        }
        catch (Exception e)
        {
            Log.d("ERROR:","No funciono POST ")
        }*/
        if( newName.getText().toString().trim().equals("")) {
            newName.setError( "Name is required!" );
            newName.setHint("Write your name");
        } else {
            if( newLastName.getText().toString().trim().equals("")) {
                newLastName.setError( "Last name is required!" );
                newLastName.setHint("Write your last name");
            } else {
                if( newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError( "Email is required!" );
                    newEmail.setHint("Write your email");
                } else {
                    if( newPassword.getText().toString().trim().equals("")) {
                        newPassword.setError( "Password is required!" );
                        newPassword.setHint("Write your password");
                    } else {
                        //Llamar a clase USER para usar API con Volley
                        UsersController prueba=UsersController.getInstance();
                        prueba.postUser(this,newName.getText().toString(),newLastName.getText().toString(),newPassword.getText().toString(),newEmail.getText().toString());


                        Toast.makeText(this, "Your account has been created", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(this,LoginActivity.class);

                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(Toast.LENGTH_LONG+1); // As I am using LENGTH_LONG in Toast
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                    }
                }

            }


        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);


        //WebView myWebView = (WebView) findViewById(R.id.webview);
        //myWebView.loadUrl("https://beach-finder.herokuapp.com/users/new");


    }



}

