
package com.mediwit.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DiscoveryFragment extends Fragment {

    private View mRootView;

    public DiscoveryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = new TextView(getActivity());//inflater.inflate(R.layout.fragment_discovery, null);
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRootView != null) {
            ViewGroup tmp = ((ViewGroup) mRootView.getParent());
            if (tmp != null) {
                tmp.removeView(mRootView);
            }
        }
    }


}
