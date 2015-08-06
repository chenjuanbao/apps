package com.mediwit.app.model.message;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mediwit.app.R;
import com.mediwit.app.widget.BadgeView;
import com.mediwit.app.widget.RoundAngleImageView;

public class ListViewAdapter extends BaseAdapter {
    private Context context;                        //运行上下文
    private List<Map<String, Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器

    public ListViewAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listItems.size();
    }

    public void setListData(List<Map<String, Object>> listItems) {
        this.listItems = listItems;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int arg0) {

        return this.listItems.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("method", "getView");
        final int selectID = position;
        //自定义视图
        try {
            ListItemView listItemView = null;
            if (convertView == null) {
                listItemView = new ListItemView();
                //获取list_item布局文件的视图
                convertView = listContainer.inflate(R.layout.list_item, null);
                //获取控件对象
                listItemView.image = (RoundAngleImageView) convertView.findViewById(R.id.imageItem);
                listItemView.title = (TextView) convertView.findViewById(R.id.titleItem);
                listItemView.info = (TextView) convertView.findViewById(R.id.infoItem);
                listItemView.time = (TextView) convertView.findViewById(R.id.titleTime);

                convertView.setId(position);

                convertView.setTag(listItemView);
            } else {
                listItemView = (ListItemView) convertView.getTag();
            }
            listItemView.image.setBackgroundResource((Integer) listItems.get(position).get("image"));
            listItemView.title.setText((String) listItems.get(position).get("title"));
            listItemView.info.setText((String) listItems.get(position).get("info"));
            listItemView.time.setText((String) listItems.get(position).get("time"));
            int num = (Integer) listItems.get(position).get("num");
            if (num > 0) {
                if (null == listItemView.badge) {
                    listItemView.badge = new BadgeView(context, listItemView.image, 2);
                }
                String showNum;
                if (num > 10) {
                    showNum = "··";
                } else {
                    showNum = String.valueOf(num);
                }
                listItemView.badge.setText(showNum);
                listItemView.badge.show();
            } else {
                if (null != listItemView.badge) {
                    listItemView.badge.hide();
                }
            }
            listItemView.data = listItems.get(position);
            Log.e("msg", (String) listItems.get(position).get("time"));


        } catch (Exception e) {
            Log.e("列表Item初始化错误", e.getMessage());
        }

//         convertView.setMinimumWidth(parent.getWidth());
        return convertView;
    }

}
