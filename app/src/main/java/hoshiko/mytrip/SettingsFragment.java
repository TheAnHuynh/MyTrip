package hoshiko.mytrip;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by An on 1/18/2018.
 */

public class SettingsFragment extends Fragment {

    private TextView txtCurrentUserInfo, txtLogout;

    public SettingsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);

        txtCurrentUserInfo = settingsView.findViewById(R.id.txtCurrentUserInfo);
        txtLogout = settingsView.findViewById(R.id.txtLogout);

        txtCurrentUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserManagerFragment fragment = new UserManagerFragment();

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent signInIntent = new Intent(getActivity(),LoginActivity.class);
                startActivity(signInIntent);
                getActivity().finish();
            }
        });
        return settingsView;
    }

}
