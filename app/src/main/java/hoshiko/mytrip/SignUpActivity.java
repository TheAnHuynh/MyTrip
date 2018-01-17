package hoshiko.mytrip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private  String TAG = SignUpActivity.class.getSimpleName();

    private EditText edtFullName, edtEmail,edtPassword,edtConfirmPassword;
    private CheckBox chkTermsAndConditions;
    private Button btnSignUp;
    private TextView txtSignIn;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mData = database.getReference();
        addControls();
        addEvennts();
    }

    private void addControls() {
        edtFullName = findViewById(R.id.fullName);
        edtEmail = findViewById(R.id.userEmailId);
        edtPassword = findViewById(R.id.password);
        edtConfirmPassword = findViewById(R.id.confirmPassword);
        chkTermsAndConditions = findViewById(R.id.terms_conditions);
        btnSignUp = findViewById(R.id.signUpBtn);
        txtSignIn = findViewById(R.id.already_user);

    }

    private void addEvennts() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = edtFullName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirm_password = edtConfirmPassword.getText().toString().trim();

                if (hoTen.isEmpty()){
                    Log.e(TAG, "Họ tên bị bỏ trống !");
                    Toast.makeText(SignUpActivity.this,"Họ tên bị bỏ trống !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()){
                    Log.e(TAG, "Email tên bị bỏ trống !");
                    Toast.makeText(SignUpActivity.this,"Email bị bỏ trống !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.isEmpty()){
                    Log.e(TAG, "Password tên bị bỏ trống !");
                    Toast.makeText(SignUpActivity.this,"Password bị bỏ trống !", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(password.length() < 6){
                        Log.e(TAG, "Password ít hơn 6 ký tự !");
                        Toast.makeText(SignUpActivity.this,"Password ít hơn 6 ký tự !", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        if(!password.equals(confirm_password)){
                            Log.e(TAG, "Xác nhận password không khớp!");
                            Toast.makeText(SignUpActivity.this,"Xác nhận password không khớp !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                if(!chkTermsAndConditions.isChecked()){
                    Log.e(TAG, "Chưa đồng ý với điều khoản");
                    Toast.makeText(SignUpActivity.this,"Bạn chưa đồng ý với điều khoản của chúng tôi !", Toast.LENGTH_SHORT).show();
                    return;
                }
                dangKy(hoTen,email,password);
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void dangKy(final String hoTen, final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "Authentication successful.");
                            Toast.makeText(SignUpActivity.this, "Đăng ký tài khoản thành công !.",
                                    Toast.LENGTH_SHORT).show();
                            xuLyDangKyTaiKhoanThanhCong(new User(hoTen,email));
                            Intent mainActivityIntent = new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "Authentication failed.");
                            Toast.makeText(SignUpActivity.this, "Đăng ký tài khoản thất bại !.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void xuLyDangKyTaiKhoanThanhCong(User user){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUID = currentUser.getUid();
        mData.child(Constant.USERS).child(currentUID).setValue(user);
    }

}