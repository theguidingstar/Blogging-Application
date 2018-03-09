package com.example.yashshah.blogger;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_activity extends AppCompatActivity {

    Button Email_Login;
    LoginButton loginButtonFaceBook;
    SignInButton Google_login;
    ViewFlipper mViewFlipper;
    EditText login_email,editTextPassword_login,Sign_up_Name,Sign_up_Number,Sign_up_Email,Sign_up_Password;
    TextInputLayout Login_editText_email_textInputlayout,Linear_editTextPassword,signup_name,signup_number,signup_email,signup_password;
    Button buttonLogin,buttonSignup;
    String TAG="";
    private final int RC_SIGN_IN=121;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();



        setContentView(R.layout.login_activity);

        Email_Login=(Button)findViewById(R.id.Email_login);
        Email_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Login_activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.login_and_signup_form);
                Sign_up_Name = (EditText) dialog.findViewById(R.id.Sign_up_Name);
                Sign_up_Number = (EditText) dialog.findViewById(R.id.Sign_up_Number);
                Sign_up_Email = (EditText) dialog.findViewById(R.id.Sign_up_EMail);
                Sign_up_Password = (EditText) dialog.findViewById(R.id.Sign_up_Password);
                signup_name = (TextInputLayout) dialog.findViewById(R.id.texInputLayout_sign_up_name);
                signup_number = (TextInputLayout) dialog.findViewById(R.id.texInputLayout_sign_up_number);
                signup_email = (TextInputLayout) dialog.findViewById(R.id.texInputLayout_sign_up_email);
                signup_password = (TextInputLayout) dialog.findViewById(R.id.texInputLayout_sign_up_Password);


                mViewFlipper = (ViewFlipper) dialog.findViewById(R.id.viewFlipper);
                mViewFlipper.setInAnimation(Login_activity.this, R.anim.slide_in);

                login_email=(EditText)dialog.findViewById(R.id.Login_editText_email);
                Login_editText_email_textInputlayout=(TextInputLayout)dialog.findViewById(R.id.Login_editText_email_textInputlayout);
                Linear_editTextPassword=(TextInputLayout)dialog.findViewById(R.id.Linear_editTextPassword);
                editTextPassword_login=(EditText)dialog.findViewById(R.id.editTextPassword_login);
                TextView MovetoSignup,MovetoLogin;
                MovetoSignup=(TextView)dialog.findViewById(R.id.Signup_dialogue);
                MovetoLogin=(TextView)dialog.findViewById(R.id.Login_dialogue);
                buttonLogin=(Button)dialog.findViewById(R.id.buttonLogin);
                buttonSignup=(Button)dialog.findViewById(R.id.buttonSignup);
                MovetoLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewFlipper.showNext();
                    }
                });

                MovetoSignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewFlipper.showNext();
                    }
                });
                dialog.show();
                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Email=login_email.getText().toString().trim();
                        String Password=editTextPassword_login.getText().toString().trim();
                        LoginEmailPasswrd(Email,Password);

                    }
                });

                buttonSignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email=Sign_up_Email.getText().toString().trim();
                        String password=Sign_up_Password.getText().toString().trim();
                        SaveEmailPasswrd(email,password);
                    }
                });

            }
        });

        try{
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("Check Sign In", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent=new Intent(Login_activity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("Check Sign Out", "onAuthStateChanged:signed_out");
                }
            }
        };}
        catch ( Exception e)
        {
            e.printStackTrace();
        }

        Google_login=(SignInButton)findViewById(R.id.Google_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(Login_activity.this)
                .enableAutoManage(Login_activity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        try{
        Google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAuthListener != null) {// Check if user is signed in (non-null) and update UI accordingly.
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void SaveEmailPasswrd(String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if(task.isSuccessful())
                        {
                            Intent intent=new Intent(Login_activity.this,MainActivity.class);
                            startActivity(intent);
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(Login_activity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void LoginEmailPasswrd(String Email,String Password){
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if(task.isSuccessful())
                        {
                            Intent intent=new Intent(Login_activity.this,MainActivity.class);
                            startActivity(intent);
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login_activity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
