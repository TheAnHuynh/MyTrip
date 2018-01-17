package hoshiko.mytrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    public final static String TAG = SplashScreenActivity.class.getSimpleName();
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Kiểm tra thông tin người dùng sau cùng.
        /**
         * Nếu người dùng dã đăng nhập thì:
         *  + cập nhật thông tin đăng nhập
         *  + điều hướng đến newsfeed
         *  nếu chưa đăng nhập thì điều hướng đến LoginActivity
         */
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Điều hướng đến main activity
            Intent mainActivity = new Intent(SplashScreenActivity.this,MainActivity.class);
        }else{
            // Điều hướng đến LoginActivity
            Intent loginIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }

//    public void updateUI(FirebaseUser currentUser){
//        // Cập nhật thông tin ngưới dùng.
//        Toast.makeText(SplashScreenActivity.this, currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(SplashScreenActivity.this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(SplashScreenActivity.this, currentUser.getProviderId(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(SplashScreenActivity.this, currentUser.getPhoneNumber(), Toast.LENGTH_SHORT).show();
//    }
}
