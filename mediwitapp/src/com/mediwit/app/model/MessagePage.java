package com.mediwit.app.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mediwit.app.R;
import com.mediwit.app.widget.BadgeView;
import com.mediwit.app.constant.TableConstant;
import com.mediwit.app.db.DatabaseHelper;
import com.mediwit.app.model.message.ListItemView;
import com.mediwit.app.model.message.ListViewAdapter;
import com.mediwit.app.model.message.MessageSql;
import com.mediwit.app.util.DatetimeUtils;

public class MessagePage extends LinearLayout   implements IMessagePage, OnItemClickListener,OnItemLongClickListener{

    private ListView listView =null;
    private IpageChangeListener changeListener=null;
    private List<Map<String, Object>> listItems;


    private ListViewAdapter listViewAdapter;
    int waitNum=0;
    public void addChangeListener(IpageChangeListener changeListener){
        this.changeListener=changeListener;
    }
    public MessagePage(Context context) {
        super(context);
        try{
            this.setBackgroundColor(getResources().getColor(R.drawable.white));
            listView=new ListView(this.getContext());
            listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
//            listView.setBackgroundColor(getResources().getColor(R.drawable.blue));
//            listView.setDivider(getResources().getDrawable(R.drawable.skin_msgbox_bg_top));
            listView.setDivider(getResources().getDrawable(R.drawable.skin_msgbox_bg_top));
            listView.setDividerHeight(3);
            listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.skin_msgbox_bg_top));
            listView.setCacheColorHint(R.drawable.blue);
            listView.setItemsCanFocus(true);

            this.addView(listView);
            //listItems = getListItems();
            listItems=new  ArrayList<Map<String, Object>>();
            listViewAdapter= new ListViewAdapter(this.getContext(), listItems);
            listView.setAdapter(listViewAdapter);
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);

        }catch(Exception e){
            Log.e("初始化消息列表错误!", e.getMessage());
            e.printStackTrace();
        }
    }

    public void freshListView(){
        listItems = getListItems();
        listViewAdapter.setListData(listItems);
        this.changeListener.change(this.getClass().getName().toString(), waitNum);
    }
    private void numChange(int cNum){
        waitNum=waitNum+cNum;
        this.changeListener.change(this.getClass().getName().toString(), waitNum);
    }
    /**
     * 初始化商品信息  
     */  
    private List<Map<String, Object>> getListItems() {
        String sql="select id,title,content,type,reciveTime,fromId,fromName,num,topTime from message order by topTime desc,id desc";
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();   
        DatabaseHelper dataBaseHelper=DatabaseHelper.getInst(this.getContext());
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        SimpleDateFormat dayformat = new SimpleDateFormat("yyyy-MM-dd"); 
        waitNum=0;
        while (cursor.moveToNext()) {
//                title, content,type ,reciveTime,fromId ,fromName
                String id=cursor.getString(0);
               String title = cursor.getString(1); //获取第一列的值,第一列的索引从0开始
               String content = cursor.getString(2);//获取第二列的值
               String type = cursor.getString(3);//获取第三列的值
               String reciveTimeStr = cursor.getString(4);

               String fromId = cursor.getString(5);
               String fromName = cursor.getString(6);
               long topTime=cursor.getLong(8);
               int num = cursor.getInt(7);

               Map<String, Object> map = new HashMap<String, Object>();
               map.put("id", id);
               map.put("image", R.drawable.zuzi);               //图片资源   
               map.put("title", title);              //物品标题   
               map.put("info",content );     //物品名称   
               Date reciveTime=null;
               try{
                   reciveTime=format.parse(reciveTimeStr);
                   Date now=new Date();
                   double diffHour=DatetimeUtils.hDiff(reciveTime, now);
                   if(diffHour<24){
                       String hourStr=DatetimeUtils.getTime(reciveTime);

                       if(reciveTime.getHours()<12){
                           reciveTimeStr="早上"+hourStr;
                       }else{
                           reciveTimeStr="下午"+hourStr;
                       }
                   }else if(diffHour<48){
                       reciveTimeStr="昨天";
                   }else{
                       reciveTimeStr=dayformat.format(reciveTime);
                   }
               }catch(Exception e){

               }
               
               map.put("time", reciveTimeStr); //物品详情   
               map.put("num", num);
               map.put("topTime", topTime);
               listItems.add(map);   
               waitNum+=num;
        }
        cursor.close();
        return listItems;   
    }
    @Override
    public void onItemClick(AdapterView<?> adaptView, View itemView, int arg2, long arg3) {
        ListItemView listItemView=(ListItemView)itemView.getTag();
        itemView.setBackgroundColor(R.drawable.blue);
        Log.e("msg", "id="+listItemView.data.get("title"));
        Toast.makeText(this.getContext(),
                "您选择的是" +listItemView.data.get("title"), 
                Toast.LENGTH_SHORT).show(); 
        //清除数量标示
        clearMessageNum(listItemView);

        itemView.setBackgroundColor(0);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adaptView, View listItem, int arg2,long arg3) {
        listItem.setBackgroundColor(R.drawable.color_item_selected);
        MyOnCreateContextMenuListener createMenuListener=new MyOnCreateContextMenuListener(this.getContext(),listItem);
        listView.setOnCreateContextMenuListener(createMenuListener);

        return false;
    }

    private void clearMessageNum(ListItemView listItemData){
        Map<String,Object> data=listItemData.data;
        DatabaseHelper dataBaseHelper=DatabaseHelper.getInst(this.getContext());
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int num=(Integer)data.get("num");
        if(num>0){
            numChange(-num);
            num=0;
            listItemData.badge.hide();
            listItemData.data.put("num", num);


            db.execSQL(MessageSql.updateNum,new Object[]{num,data.get("id")});
        }

    }

class MyOnCreateContextMenuListener implements OnCreateContextMenuListener{
    private Context context;
    private View listItem;
    public MyOnCreateContextMenuListener(Context context,View listItem){
        this.context=context;
        this.listItem=listItem;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View listItemView,
            ContextMenuInfo arg2) {
        int position=listItemView.getId();
        Map<String,Object> data=((ListItemView)this.listItem.getTag()).data;
        MyMenuItemClickListener menuItemClickListener=new MyMenuItemClickListener(this.context,this.listItem);
        String labelStr="标记";
        int num=(Integer)data.get("num");
        if(num>0){
            labelStr="标记为已读";
        }else{
            labelStr="标记为未读";
        }
        long topTime=(Long)data.get("topTime");
        String topLabel=null;
        if(1==topTime){
            topLabel="置顶";
        }else{
            topLabel="取消置顶";
        }
        MenuItem mitem=menu.add(0, 0, 0, labelStr).setOnMenuItemClickListener(menuItemClickListener);
        menu.add(0, 1, 0, topLabel).setOnMenuItemClickListener(menuItemClickListener); 
        menu.add(0, 2, 0, "删除该聊天").setOnMenuItemClickListener(menuItemClickListener); 

    }

}


class MyMenuItemClickListener implements OnMenuItemClickListener {
    private Context context;
    private View listItem;
    public MyMenuItemClickListener(Context context,View listItemView){
        this.context=context;
        this.listItem=listItemView;
    }

    private void OnMenuVisibilityListener(MenuItem menuItem) {
        Log.e("OnMenuVisibilityListener", null);

    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        ListItemView listItemData=(ListItemView)listItem.getTag();

        int position=listItem.getId();
        Log.e("item postion", ""+position);
        listItem.setBackgroundColor(0);
        switch(menuItem.getItemId()){
        case 0:
            updateMessageNum(listItemData);
            ;
            break;
        case 1:
            updateMessageTopChange(listItemData);

            break;
        case 2:
            listItems.remove(position);
            listViewAdapter.notifyDataSetChanged();
            listView.invalidate();
            String dataId=(String)listItemData.data.get("id");
            DatabaseHelper dataBaseHelper=DatabaseHelper.getInst(this.context);
            //
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            db.delete(TableConstant.TABLE_MESSAGE, "id=?", new String[]{dataId});
            listItems.remove(listItemData.data);
            break;

        }
        /*Toast.makeText(this.context,
                listItemView.data.get("title")+"::"+menuItem.getTitle()+" id:"+menuItem.getItemId(),
                Toast.LENGTH_SHORT).show(); */

        return false;
    }
    private void updateMessageNum(ListItemView listItemData){
        Map<String,Object> data=listItemData.data;
        DatabaseHelper dataBaseHelper=DatabaseHelper.getInst(this.context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int num=(Integer)data.get("num");
        if(num>0){
            numChange(-num);
            num=0;
            listItemData.badge.hide();

        }else{

            num=1;
            numChange(num);
            if(null==listItemData.badge){
                listItemData.badge=new BadgeView(this.context,listItemData.image,2);
                listItemData.badge.setText(String.valueOf(num));
            }
            listItemData.badge.show();
        }
        listItemData.data.put("num", num);
        db.execSQL(MessageSql.updateNum,new Object[]{num,data.get("id")});
    }

    private void updateMessageTopChange(ListItemView listItemData){
        Map<String,Object> data=listItemData.data;
        DatabaseHelper dataBaseHelper=DatabaseHelper.getInst(this.context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        long topTime=(Long)data.get("topTime");
        long uTopTime=1;
        listItems.remove(listItemData.data);
        if(1==topTime){
            listItems.add(0, listItemData.data);
            uTopTime=new Date().getTime();
            listViewAdapter.notifyDataSetChanged();
            data.put("topTime",uTopTime);
            db.execSQL(MessageSql.updateTopTime,new Object[]{uTopTime,data.get("id")});
        }else{
            db.execSQL(MessageSql.updateTopTime,new Object[]{uTopTime,data.get("id")});
            freshListView();
        }

    }
}

}
