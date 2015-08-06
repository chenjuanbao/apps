package com.mediwit.app.widget;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mediwit.app.R;
import com.mediwit.app.model.MessagePage;

public class ButtonGroup extends LinearLayout implements  Button.OnClickListener{
    private Button  inforBtn;
    private Button  workBtn;
    private Button  peopleBtn;
    private Button  sysBtn;
    private Button  prveBtn;
    private Map<String ,Button> buttonArray=null;
    private int btnNum=4;
    private ButtonGroupListener clickListener;
    public ButtonGroup(Context context){
        super(context);
        try {
            if(null==buttonArray){
                buttonArray=new HashMap<String, Button>();
            }
            System.out.println("调用构造函数1");
            this.setPadding(2, 1, 1, 1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public ButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    public void init(int width,int height){
        Context context=this.getContext();
//        System.out.print("window width:::"+width);
        if(null==buttonArray){
            buttonArray=new HashMap<String, Button>();
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lp.gravity=Gravity.CENTER;
//        lp.bottomMargin=-3;

//        lp.p
        //lp.height
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        System.out.println("调用构造函数2");
        LinearLayout.LayoutParams btnlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        btnlp.gravity=Gravity.CENTER;
        btnlp.bottomMargin=0;
        btnlp.topMargin=0;
        btnlp.width=120;
        btnlp.height=118;
        int margin=(width-120*this.btnNum)/(this.btnNum*2);
        btnlp.setMargins(margin, 0, margin, 0);

        inforBtn=new Button(context);
        inforBtn.setLayoutParams(btnlp);
        inforBtn.setBackgroundResource(R.drawable.btn);

        inforBtn.setWidth(width / 4);
        inforBtn.setPadding(1, 1, 1, 1);
        inforBtn.setId(1);
        inforBtn.setTag(MessagePage.class.getName());
        inforBtn.setOnClickListener(this);
        buttonArray.put(MessagePage.class.getName(), inforBtn);
        this.addView(inforBtn);
        /***我的最爱**/
        workBtn=new Button(context);
        workBtn.setLayoutParams(btnlp);
        workBtn.setBackgroundResource(R.drawable.btn);
        workBtn.setId(2);
        workBtn.setTag("workBtn");
        workBtn.setOnClickListener(this);
        buttonArray.put("workBtn", workBtn);
        this.addView(workBtn);

        /***同僚**/
        peopleBtn=new Button(context);
        peopleBtn.setLayoutParams(btnlp);
        peopleBtn.setBackgroundResource(R.drawable.skin_tab_icon_contact_normal);
        peopleBtn.setId(3);
        peopleBtn.setOnClickListener(this);
        peopleBtn.setPadding(0, 0, 0, 0);
        //peopleBtn.setBackgroundColor(color);r(R.drawable.blue);
        this.addView(peopleBtn);


        sysBtn=new Button(context);
        sysBtn.setLayoutParams(btnlp);
        sysBtn.setBackgroundResource(R.drawable.skin_tab_icon_setup_normal);
        sysBtn.setId(4);
        sysBtn.setOnClickListener(this);
        this.addView(sysBtn);
        this.inforBtn.setBackgroundResource(R.drawable.skin_tab_icon_conversation_selected);
        this.prveBtn=this.inforBtn;
    }


    /** 
     * 设置显示的文字 
     */ 

    public void setTextViewText(String text) {  

        inforBtn.setText(text);
    }
    
    @Override
    public void onClick(View v) {
        if(v==inforBtn && this.prveBtn!=this.inforBtn){
             clearSel();
             this.prveBtn=this.inforBtn;
             this.inforBtn.setBackgroundResource(R.drawable.skin_tab_icon_conversation_selected);
             if(null!=this.clickListener)
                 this.clickListener.onClick(String.valueOf(this.inforBtn.getTag()));
         }else if(v==workBtn  && this.prveBtn!=this.workBtn){
             clearSel();
             this.prveBtn=this.workBtn;
             this.workBtn.setBackgroundResource(R.drawable.skin_tab_icon_conversation_selected);
             if(null!=this.clickListener)
                 this.clickListener.onClick("workBtn");
         }else if(v==peopleBtn && this.prveBtn!=this.peopleBtn){
             clearSel();
             this.prveBtn=this.peopleBtn;
             this.peopleBtn.setBackgroundResource(R.drawable.skin_tab_icon_contact_selected);
             if(null!=this.clickListener)
                 this.clickListener.onClick("peopleBtn");
         }else if(v==sysBtn && this.prveBtn!=this.sysBtn){
             clearSel();
             this.prveBtn=this.sysBtn;
             this.sysBtn.setBackgroundResource(R.drawable.skin_tab_icon_setup_selected);
             if(null!=this.clickListener)
                 this.clickListener.onClick("sysBtn");
         }

    }
    public void setMessageNum(String pageName,int num){
        Button tButton=buttonArray.get(pageName);
        BadgeView badge =(BadgeView)tButton.getTag(R.string.btnbagde);
        if(null==badge){
            badge = new BadgeView(this.getContext(), tButton, 2);
            tButton.setTag(R.string.btnbagde,badge);
        }
        String showNum="1";
        if(num>0){
            if(num<10){
                showNum=String.valueOf(num);
            }else{
                showNum="··";
            }
            badge.setText(showNum);
            //消失与现实
            badge.show();
        }else{
            badge.hide();
        }

    }
    private void clearSel(){
        if(this.inforBtn==this.prveBtn){
            this.inforBtn.setBackgroundResource(R.drawable.btn);
        }else if(this.workBtn==this.prveBtn){
            this.workBtn.setBackgroundResource(R.drawable.btn);
        }else if(this.peopleBtn==this.prveBtn){
            this.peopleBtn.setBackgroundResource(R.drawable.skin_tab_icon_contact_normal);
        }else if(this.sysBtn==this.prveBtn){
            this.sysBtn.setBackgroundResource(R.drawable.skin_tab_icon_setup_normal);
        }

    }

    public void addListener(ButtonGroupListener listener){
        this.clickListener=listener;
    }

}
