package com.mediwit.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.mediwit.app.constant.TableConstant;
import com.mediwit.app.dao.ParsDao;

public class WelcomeActivity extends Activity {

    private Timer timer;
    private ParsDao parsDao = new ParsDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String role = parsDao.getPars(TableConstant.PAR_USER_ROLE);
                if (!TextUtils.isEmpty(role)) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    WelcomeActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.RoleSelActivity.class);
                    WelcomeActivity.this.startActivity(intent);
                }
                finish();
            }
        }, 0);


    }


}
