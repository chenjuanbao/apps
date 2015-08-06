package com.mediwit.app.model.message;

import java.util.Map;

import android.widget.TextView;

import com.mediwit.app.widget.BadgeView;
import com.mediwit.app.widget.RoundAngleImageView;

public class ListItemView {
    public BadgeView badge =null;
    public RoundAngleImageView image;
    public TextView title;     
    public TextView info;   
    public TextView time; 
    public Map<String,Object> data;
}
