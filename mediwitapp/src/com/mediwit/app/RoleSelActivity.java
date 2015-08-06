package com.mediwit.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mediwit.app.constant.TableConstant;
import com.mediwit.app.dao.ParsDao;

/**
 * Created by wangxianbing on 15/8/5.
 */
public class RoleSelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_sel);

        Button okBtn = (Button) findViewById(R.id.role_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rolegroup = (RadioGroup) findViewById(R.id.roleGroup);
                RadioButton radioButton = (RadioButton) findViewById(rolegroup.getCheckedRadioButtonId());
                String role = radioButton.getText().toString();

                ParsDao dao = new ParsDao(RoleSelActivity.this);
                dao.insertPars(TableConstant.PAR_USER_ROLE, role);

                Intent intent = new Intent(RoleSelActivity.this, MainActivity.class);
                RoleSelActivity.this.startActivity(intent);
            }
        });
    }
}

