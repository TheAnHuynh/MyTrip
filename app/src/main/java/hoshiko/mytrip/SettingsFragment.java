package hoshiko.mytrip;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by An on 1/18/2018.
 */

public class SettingsFragment extends Fragment {

    private TextView txtCurrentUserInfo;

    public SettingsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        txtCurrentUserInfo = settingsView.findViewById(R.id.txtCurrentUserInfo);
        txtCurrentUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragmentContainer,
                                        new UserManagerFragment()).commit();
            }
        });

        return settingsView;
    }
}
