package com.mediwit.app.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mediwit.app.R;
import com.mediwit.app.model.IMessagePage;

public class UserPage extends LinearLayout   implements IMessagePage{

     private List<Map<String, Object>> userItem;
    private Integer[] imgeIDs = {R.drawable.grant,
            R.drawable.grant, R.drawable.zuzi,   
            R.drawable.grant, R.drawable.grant,   
            R.drawable.grant};   
    private String[] goodsNames = {"蛋糕", "礼物",    
            "邮票", "爱心", "鼠标", "音乐CD"};   
    private String[] goodsDetails = {   
            "蛋糕：好好吃。",    
            "礼物：礼轻情重。",    
            "邮票：环游世界。",    
            "爱心：世界都有爱。",   
            "鼠标：反应敏捷。",   
            "音乐CD：酷我音乐。"}; 


    public UserPage(Context context) {
        super(context);
        try{
//            setContentView(R.layout.activity_main);
            View view = View.inflate(context, R.layout.user_list, null);
            this.addView(view);
            this.setBackgroundColor(getResources().getColor(R.drawable.color_bg));

//            Button btn=this.findViewById(R.id.user_search_input);
            EditText searchInpu=(EditText)this.findViewById(R.id.user_search_input);
//            searchInpu.setText("检索用户");
        }catch(Exception e){
            Log.e("初始化消息列表错误!", e.getMessage());
            e.printStackTrace();
        }




    }
    /**
     * 初始化商品信息  
     */  
    private List<Map<String, Object>> getUserItems() {   
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();   
        for(int i = 0; i < goodsNames.length; i++) {   
            Map<String, Object> map = new HashMap<String, Object>();    
            map.put("image", imgeIDs[i]);               //图片资源   
            map.put("title", "物品名称：");              //物品标题   
            map.put("info", goodsNames[i]);     //物品名称   
            map.put("detail", goodsDetails[i]); //物品详情   
            listItems.add(map);   
        }      
        return listItems;   
    }   



}
