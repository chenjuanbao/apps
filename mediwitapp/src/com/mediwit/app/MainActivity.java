package com.mediwit.app;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mediwit.app.util.DatetimeUtils;
import com.mediwit.app.widget.ButtonGroup;
import com.mediwit.app.constant.TableConstant;
import com.mediwit.app.dao.ParsDao;
import com.mediwit.app.db.DatabaseHelper;
import com.mediwit.app.model.MessagePage;
import com.mediwit.app.user.UserPage;
import com.mediwit.app.util.DeviceUuidFactory;
import com.mediwit.app.util.StringUtil;
import com.mediwit.app.widget.TabContainer;

public class MainActivity extends FragmentActivity {
    private UserPage userPage = null;
    ParsDao parsDao = null;



    public static final int FRAGMENT_DISCOVERY = 0;
    public static final int FRAGMENT_GAME = 1;
    public static final int FRAGMENT_RANK = 2;
    public static final int FRAGMENT_PERSONAL = 3;
    private FragmentManager mFragmentManager = null;
    private Fragment mFragment = null;

    private MessagePage mHomeFragment;
    private MessagePage mGameFragment;
    private MessagePage mRankFragment;
    private MessagePage mPersonalFragment;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private TabContainer mTabContainer;

    private ViewPager mViewPager;

    private int mWindowWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parsDao = new ParsDao(this.getApplicationContext());

        initActivity();

        mHomeFragment = new MessagePage();
        mGameFragment = new MessagePage();
        mRankFragment = new MessagePage();
        mPersonalFragment = new MessagePage();

        mFragments.add(mHomeFragment);
        mFragments.add(mGameFragment);
        mFragments.add(mRankFragment);
        mFragments.add(mPersonalFragment);

        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        // mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

        });
        mTabContainer = new TabContainer(getResources(),
                findViewById(R.id.tab_btn_group),
                mTabListener);


        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mWindowWidth = outMetrics.widthPixels;
        mTabContainer = new TabContainer(getResources(), findViewById(R.id.tab_btn_group),
                mTabListener);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mState = ViewPager.SCROLL_STATE_IDLE;
            private int mPrePosition = 0;

            @Override
            public void onPageSelected(int position) {
                if (mState == ViewPager.SCROLL_STATE_SETTLING) {
                    mTabContainer.updateIndicator(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                View tab = mTabContainer.getIndicator();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tab.getLayoutParams();
                layoutParams.width = mWindowWidth / 4;
                layoutParams.leftMargin = position * (mWindowWidth / 4) + positionOffsetPixels / 4;
                tab.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mState = state;
            }
        });
    }

    private TabContainer.TabListener mTabListener = new TabContainer.TabListener() {

        @Override
        public void onTabChange(View v) {
            int viewId = v.getId();
            if (viewId == R.id.discovery) {
                jumpToFragment(mHomeFragment);
            }
            else if (viewId == R.id.game) {
                jumpToFragment(mGameFragment);
            }
            else if (viewId == R.id.rank) {
                jumpToFragment(mRankFragment);
            }
            else if (viewId == R.id.personal) {
                jumpToFragment(mPersonalFragment);
            }
        }
    };

    private void jumpToFragment(Fragment fragment) {
        if (fragment == mFragment) {
            return;
        }

        Log.d("Simon", "jumpToFragment fragment: " + fragment);
        mViewPager.setCurrentItem(mFragments.indexOf(fragment));
    }

    private void initActivity() {
        try {
            String role = parsDao.getPars(TableConstant.PAR_USER_ROLE);
            if (StringUtil.isBlank(role)) {
                Intent intent = new Intent(MainActivity.this, RoleSelActivity.class);
                startActivityForResult(intent, 2000);
            } else {
//                //添加消息测试数据
//                DatabaseHelper dataBaseHelper = DatabaseHelper.getInst(this.getApplicationContext());
//                SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//                //获取用户角色,用户初始化按钮功能.
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
//                onClick(MessagePage.class.getName());
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

    /*
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

    */


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