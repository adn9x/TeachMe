package com.hcat.teachme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.hcat.teachme.ClickListener;
import com.hcat.teachme.ListPostAdapter;
import com.hcat.teachme.R;
import com.hcat.teachme.RecyclerTouchListener;
import com.hcat.teachme.RecyclerViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private ProfilePictureView userAvatar;
    private TextView textName, textEmail;
    private DrawerLayout navigationBar;
    private TabHost tabHost;
    private RecyclerView listPost;
    private EditText editSearch;
    private TextView textPageTitle;
    private ImageButton buttonMenu, buttonBack, buttonSearch, buttonClear, buttonOption;
    private ScaleAnimation editSearchShowAnimation, editSearchHideAnimation;

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
        tab1.setIndicator(getResources().getString(R.string.teacher_tab_title));
        tab1.setContent(R.id.tab_1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("TAB_2");
        tab2.setIndicator(getResources().getString(R.string.find_teacher_tab_title));
        tab2.setContent(R.id.tab_2);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        listPost = (RecyclerView) findViewById(R.id.recycler_view);
        listPost.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listPost.setItemAnimator(new DefaultItemAnimator());
        listPost.addItemDecoration(new RecyclerViewDivider(this, RecyclerViewDivider.VERTICAL_LIST));
        ListPostAdapter listPostAdapter = new ListPostAdapter(this, receivedIntent.getStringExtra(LoginActivity.KEY_FACEBOOK_ID));
        listPost.setAdapter(listPostAdapter);
        listPost.addOnItemTouchListener(new RecyclerTouchListener(this, listPost, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(this, )
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }){

        });

        editSearchShowAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.search_bar_show_aniamtion);
        editSearchShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                editSearch.setVisibility(View.VISIBLE);
                buttonClear.setVisibility(View.VISIBLE);
                buttonBack.setVisibility(View.VISIBLE);

                buttonMenu.setVisibility(View.INVISIBLE);
                buttonSearch.setVisibility(View.INVISIBLE);
                textPageTitle.setVisibility(View.INVISIBLE);

                editSearch.requestFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        editSearchHideAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.search_bar_hide_aniamtion);
        editSearchHideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                editSearch.setText("");
                editSearch.setVisibility(View.INVISIBLE);
                editSearch.setVisibility(View.INVISIBLE);
                buttonClear.setVisibility(View.INVISIBLE);
                buttonBack.setVisibility(View.INVISIBLE);

                buttonMenu.setVisibility(View.VISIBLE);
                buttonSearch.setVisibility(View.VISIBLE);
                textPageTitle.setVisibility(View.VISIBLE);
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editSearch.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu: {
                navigationBar.openDrawer(Gravity.START);
            }
            break;

            case R.id.btn_back: {
                editSearch.startAnimation(editSearchHideAnimation);
            }
            break;

            case R.id.btn_search: {
                editSearch.startAnimation(editSearchShowAnimation);
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
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (visibility == View.VISIBLE) {
            editSearch.startAnimation(editSearchShowAnimation);


            inputMethodManager.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
        }else {
            editSearch.startAnimation(editSearchHideAnimation);

            editSearch.setVisibility(View.INVISIBLE);
            buttonClear.setVisibility(View.INVISIBLE);
            buttonBack.setVisibility(View.INVISIBLE);

            buttonMenu.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            textPageTitle.setVisibility(View.VISIBLE);
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
                URL url = new URL("http://188.166.247.59:3000/todo/create");
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
