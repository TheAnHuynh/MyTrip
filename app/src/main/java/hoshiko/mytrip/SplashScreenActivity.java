package hoshiko.mytrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    public final static String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Kiểm tra thông tin người dùng sau cùng.
        /**
         * Nếu người dùng dã đăng nhập thì:
         *  + cập nhật thông tin đăng nhập
         *  + điều hướng đến newsfeed
         *  nếu chưa đăng nhập thì điều hướng đến LoginActivity
         */
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            // Điều hướng đến main activity
            Intent mainActivity = new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(mainActivity);
            finish();
        }else{
            // Điều hướng đến LoginActivity
            Intent loginIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
