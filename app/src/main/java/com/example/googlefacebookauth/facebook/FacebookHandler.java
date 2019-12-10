package com.example.googlefacebookauth.facebook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.googlefacebookauth.FaceBookActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookHandler {

    private static CallbackManager callbackManager = null;

    public static CallbackManager getCallbackManager() {
        if (callbackManager == null)
            callbackManager = CallbackManager.Factory.create();
        return callbackManager;
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

    private void onFacebookLoginAttempt(/*SOME CALLBACK TO MAIN ACTIVITY*/Activity context) {
        LoginManager.getInstance().logInWithReadPermissions(context, Arrays.asList("public_profile"));
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

    private void logout() {
        LoginManager.getInstance().logOut();
    }
}
