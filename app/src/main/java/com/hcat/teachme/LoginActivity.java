package com.hcat.teachme;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Admin on 2/26/2017.
 */

public class LoginActivity extends Activity implements FacebookCallback<LoginResult> {
    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_GENDER = "KEY_GENDER";
    public static final String KEY_BIRTHDAY = "KEY_BIRTHDAY";
    public static final String KEY_FACEBOOK_ID = "KEY_FACEBOOK_ID";
    private static final String TAG = "Login Activity";

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.login_activity);

        initViews();
    }

    private void initViews() {
        if (hasLoggedIn()) {
            LoginManager loginManagerInstance = LoginManager.getInstance();
            loginManagerInstance.registerCallback(callbackManager, this);
            loginManagerInstance.logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
        } else {
            loginButton = (LoginButton) findViewById(R.id.btn_login);
            loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
            loginButton.registerCallback(callbackManager, this);
        }
    }

    public boolean hasLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.i(TAG, "onSuccess: ");
        GraphRequest graphRequest =
                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
//                            Log.i(TAG, "onCompleted: "+object.toString(1));

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(LoginActivity.KEY_USERNAME, object.getString("name"));
                            intent.putExtra(LoginActivity.KEY_EMAIL, object.getString("email"));
                            intent.putExtra(LoginActivity.KEY_GENDER, object.getString("gender"));
                            intent.putExtra(LoginActivity.KEY_BIRTHDAY, object.getString("birthday"));
                            intent.putExtra(LoginActivity.KEY_FACEBOOK_ID, object.getString("id"));

                            intent.putExtra("json", object.toString(1));
                            LoginActivity.this.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,birthday,gender,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "onCancel: ");
    }

    @Override
    public void onError(FacebookException error) {
        Log.i(TAG, "onError: ");
    }
}
