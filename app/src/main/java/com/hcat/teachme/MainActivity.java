package com.hcat.teachme;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private ProfilePictureView userAvatar;
    private TextView textName, textEmail;
    private DrawerLayout navigationBar;
    private TabHost tabHost;
    private ListView listPost;
    private EditText editSearch;
    private TextView textPageTitle;
    private ImageButton buttonMenu, buttonBack, buttonSearch, buttonClear, buttonOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        buttonMenu = (ImageButton) findViewById(R.id.btn_menu);
        buttonMenu.setOnClickListener(this);
        buttonBack = (ImageButton) findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(this);
        buttonSearch = (ImageButton) findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(this);
        buttonClear = (ImageButton) findViewById(R.id.btn_clear);
        buttonClear.setOnClickListener(this);
        buttonOption = (ImageButton) findViewById(R.id.btn_option);
        buttonOption.setOnClickListener(this);

        editSearch = (EditText) findViewById(R.id.edt_search);

        textPageTitle = (TextView) findViewById(R.id.txt_title);

        navigationBar = (DrawerLayout) findViewById(R.id.navigation_bar);

        userAvatar = (ProfilePictureView) findViewById(R.id.img_user);
        userAvatar.setPresetSize(ProfilePictureView.NORMAL);
        textName = (TextView) findViewById(R.id.txt_username);
        textEmail = (TextView) findViewById(R.id.txt_email);

        Intent receivedIntent = getIntent();
        userAvatar.setProfileId(receivedIntent.getStringExtra(LoginActivity.KEY_FACEBOOK_ID));
        textName.setText(receivedIntent.getStringExtra(LoginActivity.KEY_USERNAME));
        textEmail.setText(receivedIntent.getStringExtra(LoginActivity.KEY_EMAIL));

        findViewById(R.id.btn_logout).setOnClickListener(this);

        tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("TAB_1");
        tab1.setIndicator(getResources().getString(R.string.highlight_tab_title));
        tab1.setContent(R.id.tab_1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("TAB_2");
        tab2.setIndicator(getResources().getString(R.string.location_tab_title));
        tab2.setContent(R.id.tab_2);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        listPost = (ListView) findViewById(R.id.list_post);
        ListPostAdapter listPostAdapter = new ListPostAdapter(this, receivedIntent.getStringExtra(LoginActivity.KEY_FACEBOOK_ID));
        listPost.setAdapter(listPostAdapter);


//        new GetTask().execute();
        new PostTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu: {
                navigationBar.openDrawer(Gravity.START);
            }
            break;

            case R.id.btn_back: {
                setSearchBarVisibility(View.INVISIBLE);
            }
            break;

            case R.id.btn_search: {
                setSearchBarVisibility(View.VISIBLE);
            }
            break;

            case R.id.btn_clear: {
                editSearch.setText("");
            }
            break;

            case R.id.btn_option: {

            }
            break;

            case R.id.btn_logout: {
                finish();
            }

            default: {
                break;
            }
        }
    }

    private void setSearchBarVisibility(int visibility) {
        int revertState;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (visibility == View.VISIBLE) {
            revertState = View.INVISIBLE;
        } else {
            revertState = View.VISIBLE;
        }
        editSearch.setVisibility(visibility);
        buttonClear.setVisibility(visibility);
        buttonBack.setVisibility(visibility);

        buttonMenu.setVisibility(revertState);
        buttonSearch.setVisibility(revertState);
        textPageTitle.setVisibility(revertState);

        if (visibility == View.VISIBLE) {
            editSearch.requestFocus();
            inputMethodManager.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputMethodManager.hideSoftInputFromWindow(editSearch.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
        }
    }

    private class GetTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://188.166.247.59:3000/todo/getsubject");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    String result = readStream(inputStream);
                    Log.i(TAG, "doInBackground: " + result);
                } finally {
                    httpURLConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String readStream(InputStream inputStream) {
            byte contents[] = new byte[1024];
            int byteRead;
            String resultString = "";
            try {
                while ((byteRead = inputStream.read(contents)) != -1) {
                    resultString += new String(contents, 0, byteRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultString;
        }

        @Override
        protected void onProgressUpdate(String... uri) {

        }

        @Override
        protected void onPostExecute(String aVoid) {

        }
    }

    private class PostTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://188.166.247.59:3000/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setChunkedStreamingMode(0);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;  charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json;  charset=UTF-8");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", "123456789");
                jsonObject.put("name", "Trần Thanh Tùng");

                OutputStream writer = new BufferedOutputStream(httpURLConnection.getOutputStream());
                writer.write(jsonObject.toString().getBytes("UTF-8"));
                writer.close();

                StringBuilder resultString = new StringBuilder();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        resultString.append(line+"\n");
                    }
                    reader.close();
                    Log.i(TAG, "doInBackground: SUCCESS "+resultString);
                }else {
                    Log.i(TAG, "doInBackground: ERROR "+httpURLConnection.getResponseMessage());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
