package hoshiko.mytrip;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    public final static String TAG = SplashScreenActivity.class.getSimpleName();

    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        Log.d(TAG, "onCreate");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        // Kiểm tra thông tin người dùng sau cùng.
        /**
         * Nếu người dùng dã đăng nhập thì điều hướng đến newsfeed
         *  nếu chưa đăng nhập thì điều hướng đến LoginActivity
         */
        if(currentUser != null){
            // Điều hướng đến main activity
            Intent mainActivity = new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(mainActivity);
        }else{
            // Điều hướng đến LoginActivity
            Intent loginIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(loginIntent);
        }
        finish();
    }
}
