package hoshiko.mytrip;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by An on 1/18/2018.
 */

public class MyPlacesFragment extends Fragment {

    public MyPlacesFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myPlacesView = inflater.inflate(R.layout.fragment_myplaces, container, false);

        return myPlacesView;
    }
}
