package hoshiko.mytrip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PopularFragment extends Fragment {
    //Mandatory constructor for instantiating the fragment
    public PopularFragment(){

    }

    /*Inflates the fragment layout and sets any image resources */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout  for this fragment
        View popularView =  inflater.inflate(R.layout.fragment_popular, container, false);

        return popularView;


    }
}

