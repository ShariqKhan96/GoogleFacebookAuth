package com.example.googlefacebookauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class FaceBookActivity extends AppCompatActivity {

    Button loginButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    Button logout, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book);
        FacebookSdk.sdkInitialize(getApplicationContext());
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        logout = findViewById(R.id.logout);
        data = findViewById(R.id.get_data);


        if (isLoggedIn)
            logout.setVisibility(View.VISIBLE);
        else logout.setVisibility(View.GONE);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
            }
        });
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(FaceBookActivity.this, Arrays.asList("public_profile"));

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {


                                getGraphData(loginResult.getAccessToken());
                                Log.e(FaceBookActivity.class.getSimpleName(), loginResult.getAccessToken().getToken());
                            }

                            @Override
                            public void onCancel() {
                                Log.e(FaceBookActivity.class.getSimpleName(), "onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e(FaceBookActivity.class.getSimpleName(), exception.getMessage());
                            }
                        });
            }
        });
        //loginButton.setReadPermissions(Arrays.asList(EMAIL));
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                Log.e(FaceBookActivity.class.getSimpleName(), loginResult.getAccessToken().getToken());
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.e(FaceBookActivity.class.getSimpleName(), "onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Log.e(FaceBookActivity.class.getSimpleName(), exception.getMessage());
//            }
//        });

    }

    private void getGraphData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            Log.e(FaceBookActivity.class.getSimpleName(), response.toString());

                            Log.e(FaceBookActivity.class.getSimpleName(), object.toString());
                        } catch (Exception e) {
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
