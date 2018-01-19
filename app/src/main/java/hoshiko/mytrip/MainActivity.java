package hoshiko.mytrip;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mData;

    private  User currentUser;



    private BottomNavigationView.OnNavigationItemSelectedListener mBottomNavigation
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    switchToPopularFragment();
                    break;

                case R.id.action_my_places:
                    switchToMyPlacesFragment();
                    break;
                case R.id.action_favorites:
                    switchToFavoritesFragment();
                    break;

                case R.id.action_settings:
                    switchToSettingsFragment();
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up for the first time
        switchToPopularFragment();

        //Khá»Ÿi táº¡o thanh Äiá»u hÆ°á»›ng phÃ­a dÆ°á»›i cá»§a app
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Helps Navigation Bar could Items display all Icon with text
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(mBottomNavigation);

    }

    public void switchToPopularFragment() {
        PopularFragment popularFragment = new PopularFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, popularFragment).commit();
    }

    private void switchToFavoritesFragment() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, favoritesFragment).commit();
    }

    private void switchToMyPlacesFragment() {
        MyPlacesFragment myPlacesFragment = new MyPlacesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, myPlacesFragment).commit();
    }

    private void switchToSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, settingsFragment).commit();
//        TextView txtCurrnentUI = findViewById(R.id.txtCurrentUserInfo);
//        txtCurrnentUI.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager manager = getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.fragmentContainer, new UserManagerFragment())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
    }

}
