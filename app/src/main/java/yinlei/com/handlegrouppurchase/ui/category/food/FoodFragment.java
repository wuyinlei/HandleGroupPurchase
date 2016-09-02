package yinlei.com.handlegrouppurchase.ui.category.food;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.widget.swipebackfragment.SwipeBackFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends SwipeBackFragment {


    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food, container, false);
        return attachToSwipeBack(v);
    }

}
