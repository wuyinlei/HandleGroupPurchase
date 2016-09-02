package yinlei.com.handlegrouppurchase.ui.category.movie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import yinlei.com.handlegrouppurchase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    @Bind(R.id.layout_id)
    LinearLayout mLayoutId;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ImageView movieImage;
    private TextView movieTitle, movieCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        ButterKnife.bind(this, view);


        for (int i = 0; i < 6; i++) {
            View viewMovie = getActivity().getLayoutInflater().inflate(R.layout.movie_item,null);
            movieImage = (ImageView) viewMovie.findViewById(R.id.movie_image);
            movieTitle = (TextView) viewMovie.findViewById(R.id.movie_title);
            movieCount = (TextView) viewMovie.findViewById(R.id.movie_count);
            movieImage.setImageResource(R.drawable.nav_car);
            movieTitle.setText("送悟空");
            movieCount.setText("9.6分");
            mLayoutId.addView(viewMovie);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
