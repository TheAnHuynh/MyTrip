package hoshiko.mytrip;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesFragment extends Fragment{

    public FavoritesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.fragment_favorites, container,false);

        return favoritesView;
    }
}
