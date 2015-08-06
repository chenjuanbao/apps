package com.mediwit.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=(Button)findViewById(R.id.sign_in_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView phone=(TextView)findViewById(R.id.login_phone);
        TextView pwd=(TextView)findViewById(R.id.login_password);
        Log.e("user info ",  phone.getText()+"::"+pwd.getText());
//        login_progress
        ProgressBar progress=(ProgressBar)findViewById(R.id.login_progress);
        progress.bringToFront();
        progress.setIndeterminate(false);
        progress.setVisibility(View.VISIBLE);
        progress.setProgress(0);
    }


}
