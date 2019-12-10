package com.example.googlefacebookauth.google;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleClient {

    public GoogleSignInClient getGoogleSignInClient(Context context) {
        return GoogleSignIn.getClient(context, getSignInOptions());

    }

    private GoogleSignInOptions getSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    private Intent getGoogleSignInIntent(GoogleSignInClient mGoogleSignInClient) {
        return mGoogleSignInClient.getSignInIntent();
    }

    private GoogleSignInAccount getGoogleSignedAccount(Context context) {
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    private GoogleSignInAccount handleSignInIntentResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            return account;
        } catch (ApiException e) {
            Log.e(GoogleClient.class.getSimpleName(), e.getLocalizedMessage());
            return null;
        }
    }
}
