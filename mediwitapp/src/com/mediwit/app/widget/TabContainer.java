
package com.mediwit.app.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mediwit.app.R;

/**
 * TODO check null
 * 
 * @author wangxianbing
 */

public class TabContainer implements OnClickListener {
    public interface TabListener {
        void onTabChange(View v);
    }

    private TabListener mTabListener;
    private View mRoot;

    private List<TextView> mTabList;

    private View mIndicator;

    private int mTabTextNormalColor;
    private int mTabTextHightLightColor;

    public TabContainer(Resources resources, View view, TabListener tabListener) {
        mRoot = view;

        mTabListener = tabListener;

        mTabTextNormalColor = resources.getColor(R.color.tab_text_normal_color);
        mTabTextHightLightColor = resources.getColor(R.color.tab_text_highlight_color);

        TextView discoveryTextView = (TextView) view.findViewById(R.id.discovery);
        TextView gameTextView = (TextView) view.findViewById(R.id.game);
        TextView rankTextView = (TextView) view.findViewById(R.id.rank);
        TextView personalTextView = (TextView) view.findViewById(R.id.personal);
        mIndicator = view.findViewById(R.id.tab_indicator);

        mTabList = new ArrayList<TextView>();
        mTabList.add(discoveryTextView);
        mTabList.add(gameTextView);
        mTabList.add(rankTextView);
        mTabList.add(personalTextView);

        discoveryTextView.setText("discovery");
        gameTextView.setText("game");
        rankTextView.setText("rank");
        personalTextView.setText("personal");

        discoveryTextView.setOnClickListener(this);
        gameTextView.setOnClickListener(this);
        rankTextView.setOnClickListener(this);
        personalTextView.setOnClickListener(this);

        discoveryTextView.performClick();
        updateIndicator(0);
    }

    public void updateIndicator(int position) {
        TextView textView = mTabList.get(position);
        for (TextView t : mTabList) {
            t.setTextColor(mTabTextNormalColor);
        }
        textView.setTextColor(mTabTextHightLightColor);
    }

    public View getIndicator() {
        return mIndicator;
    }

    @Override
    public void onClick(View v) {
        if (mTabListener != null) {
            mTabListener.onTabChange(v);
        }
        switch (v.getId()) {
            case R.id.discovery:
                updateIndicator(0);
                break;
            case R.id.game:
                updateIndicator(1);
                break;
            case R.id.rank:
                updateIndicator(2);
                break;
            case R.id.personal:
                updateIndicator(3);
                break;
        }
    }
}
