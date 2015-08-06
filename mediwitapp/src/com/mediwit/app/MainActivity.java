package com.mediwit.app;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mediwit.app.widget.ButtonGroup;
import com.mediwit.app.widget.ButtonGroupListener;
import com.mediwit.app.constant.TableConstant;
import com.mediwit.app.dao.ParsDao;
import com.mediwit.app.db.DatabaseHelper;
import com.mediwit.app.model.IpageChangeListener;
import com.mediwit.app.model.MessagePage;
import com.mediwit.app.user.UserPage;
import com.mediwit.app.util.DeviceUuidFactory;
import com.mediwit.app.util.StringUtil;

public class MainActivity extends Activity implements ButtonGroupListener, IpageChangeListener {
    ButtonGroup btnPlace = null;
    private View prvelay = null;
    private MessagePage messagePage = null;
    private UserPage userPage = null;
    FrameLayout showPlace = null;
    int btnHeight = 150;
    int btnWidth = 800;
    float hwPercent = 100 / 800F;
    ParsDao parsDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parsDao = new ParsDao(this.getApplicationContext());

        initActivity();
    }

    private void initActivity() {
        try {
            setContentView(R.layout.activity_main);
            DatabaseHelper dataBaseHelper = DatabaseHelper.getInst(this.getApplicationContext());
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            //获取用户角色,用户初始化按钮功能.

            String role = parsDao.getPars(TableConstant.PAR_USER_ROLE);
            if (StringUtil.isBlank(role)) {
//                弹出选择角色
                Intent intent = new Intent(MainActivity.this, RoleSelActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 2000);
            } else {
                DisplayMetrics metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                int width = metric.widthPixels;  // 屏幕宽度（像素）
                int height = metric.heightPixels;  // 屏幕高度（像素）
//                btnWidth=width;
                float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
                int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
                btnPlace = (ButtonGroup) findViewById(R.id.buttonGroup);
                Float heightF = new Float(width * hwPercent);
                btnPlace.init(width, heightF.intValue());
                btnPlace.addListener(this);
                LinearLayout.LayoutParams btnlp = (LinearLayout.LayoutParams) btnPlace.getLayoutParams();
//                android LinearLayout 代码样式边框
                btnlp.height = btnHeight;
                btnlp.topMargin = 1;
                btnPlace.setLayoutParams(btnlp);
                System.out.println("-----------------[" + width * hwPercent + "]----------------------");
                showPlace = (FrameLayout) findViewById(R.id.showplace);
                LinearLayout.LayoutParams showlp = (LinearLayout.LayoutParams) showPlace.getLayoutParams();
                showlp.height = height - btnHeight;
                showPlace.setLayoutParams(showlp);
//                prvelay=(TextView)this.findViewById(R.id.l1);


                //添加消息测试数据

//                String now= DatetimeUtils.getDateTime();
//                String sql = "insert into message (title, content,type,fromId,fromName,num,reciveTime) values ('蛋糕','蛋糕好好吃,你也来一块?','1','sdfskdfjskjfdkjf','张磊',7,?)";
//                db.execSQL(sql,new Object[]{now});
//                String sql1 = "insert into message (title, content,type,fromId,fromName,num,reciveTime) values ('礼物','礼轻情重,请笑纳.','1','sdfskdfjskjfdkjf','张磊',0,?)";
//                db.execSQL(sql1,new Object[]{now});
//                String sql2 = "insert into message (title, content,type,fromId,fromName,num,reciveTime) values ('邮票','世界那么大,我想出去看看.','1','sdfskdfjskjfdkjf','张磊',1,?)";
//                db.execSQL(sql2,new Object[]{now});
//                String sql3 = "insert into message (title, content,type,fromId,fromName,num,reciveTime) values ('爱心','世界都有爱,温暖你我他.','1','sdfskdfjskjfdkjf','张磊',13,?)";
//                db.execSQL(sql3,new Object[]{now});
//                String sql4 = "insert into message (title, content,type,fromId,fromName,num,reciveTime) values ('鼠标','反应敏捷,操作流程.','1','sdfskdfjskjfdkjf','张磊',6,?)";
//                db.execSQL(sql4,new Object[]{now});
//                String sql5 = "insert into message (title, content,type,fromId,fromName,num) values ('音乐CD','我的音乐,我的世界.','1','sdfskdfjskjfdkjf','张磊',21)";
//                db.execSQL(sql5);
                try {
                    /*
                       //执行指定的 sql
                    String sql1 = "insert into message (title, content,type,fromId,fromName,num) values ('礼物','礼轻情重,请笑纳.','1','sdfskdfjskjfdkjf','张磊',0)";
                    db.execSQL(sql1);
                    String sql2 = "insert into message (title, content,type,fromId,fromName,num) values ('邮票','世界那么大,我想出去看看.','1','sdfskdfjskjfdkjf','张磊',1)";
                    db.execSQL(sql2);
                    String sql3 = "insert into message (title, content,type,fromId,fromName,num) values ('爱心','世界都有爱,温暖你我他.','1','sdfskdfjskjfdkjf','张磊',13)";
                    db.execSQL(sql3);
                    String sql4 = "insert into message (title, content,type,fromId,fromName,num) values ('鼠标','反应敏捷,操作流程.','1','sdfskdfjskjfdkjf','张磊',6)";
                    db.execSQL(sql4);
                    String sql5 = "insert into message (title, content,type,fromId,fromName,num) values ('音乐CD','我的音乐,我的世界.','1','sdfskdfjskjfdkjf','张磊',21)";
                    db.execSQL(sql5);
                    */
                } catch (SQLException ex) {
                    Log.e("sql insert error", ex.getMessage());
                }

//                dataBaseHelper.getReadableDatabase().execSQL("");
                onClick(MessagePage.class.getName());
            }
            //
            String userId = parsDao.getSelfId();
            if (StringUtil.isBlank(userId)) {
                DeviceUuidFactory uuidFactory = new DeviceUuidFactory(this.getApplicationContext());
                UUID uuid = uuidFactory.getDeviceUuid();
                userId = uuid.toString();
                parsDao.insertSelfId(userId);
                //匿名登录系统.

                Log.e("info", "userId=" + userId);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(String label) {
        System.out.println("选中的按钮：：：" + label);
        if (MessagePage.class.getName().equalsIgnoreCase(label)) {
            if (null == messagePage) {
                messagePage = new MessagePage(this.getApplicationContext());
                messagePage.addChangeListener(this);
                showPlace.addView(messagePage);
//                messagePage.freshListView();
            }

            if (messagePage != prvelay) {
                messagePage.setVisibility(View.VISIBLE);
                messagePage.bringToFront();
                if (null != prvelay)
                    prvelay.setVisibility(View.INVISIBLE);
                prvelay = messagePage;
            }
            messagePage.freshListView();
        } else if ("workBtn".equalsIgnoreCase(label)) {
            TextView lay = (TextView) this.findViewById(R.id.l2);
            if (lay != prvelay) {
                lay.setVisibility(View.VISIBLE);
                lay.bringToFront();
                if (null != prvelay)
                    prvelay.setVisibility(View.INVISIBLE);
                prvelay = lay;
            }

        } else if ("peopleBtn".equalsIgnoreCase(label)) {
            if (null == userPage) {
                userPage = new UserPage(this.getApplicationContext());
                showPlace.addView(userPage);

            }

            if (userPage != prvelay) {
                userPage.setVisibility(View.VISIBLE);
                userPage.bringToFront();
                if (null != prvelay)
                    prvelay.setVisibility(View.INVISIBLE);

                prvelay = userPage;
            }
        } else if ("sysBtn".equalsIgnoreCase(label)) {
            TextView lay = (TextView) this.findViewById(R.id.l4);
            if (lay != prvelay) {
                lay.setVisibility(View.VISIBLE);
                lay.bringToFront();
                if (null != prvelay)
                    prvelay.setVisibility(View.INVISIBLE);
                prvelay = lay;
            }
        }

    }

    @Override
    public void change(String type, int value) {
        Log.e("info-num", type + ":" + value);
        this.btnPlace.setMessageNum(type, value);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    int yourChose = -1;

    private String selRole() {
        String role = null;
        String[] mList = {"普通用户", "医生"};

        AlertDialog.Builder sinChosDia = new AlertDialog.Builder(MainActivity.this);
        sinChosDia.setTitle("请选择你的角色");
        sinChosDia.setSingleChoiceItems(mList, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yourChose = which;
            }
        });
        sinChosDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        sinChosDia.create().show();
//         showClickMessage(mList[yourChose]);
        if (-1 != yourChose) {
            role = mList[yourChose];
        }
        return role;

    }

}