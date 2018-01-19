package hoshiko.mytrip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private final static String TAG = ForgotPasswordActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private EditText edtRegisteredEmailid;
    private TextView txtSend;
    private TextView txtBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);
        mAuth = FirebaseAuth.getInstance();

        addControls();
        addEvents();

    }

    private void addControls() {
        edtRegisteredEmailid = findViewById(R.id.registered_emailid);
        txtBack = findViewById(R.id.txtBackToLogin);
        txtSend = findViewById(R.id.txtSend);
    }

    private void addEvents() {
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(ForgotPasswordActivity.this,
                                        LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = edtRegisteredEmailid.getText().toString().trim();
                if(emailAddress.isEmpty()){
                    Log.d(TAG , "Email EMPTY");
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Email bị bỏ trống",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Pattern p = Pattern.compile(Constant.regEx);
                    Matcher m = p.matcher(emailAddress);
                    if (!m.find()){
                        Log.d(TAG , "Email INVALIDED");
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Email chứa ký tự không hợp lệ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                mAuth.setLanguageCode("vi");
                mAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(ForgotPasswordActivity.this,
                                            "Đã gửi Email",Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(ForgotPasswordActivity.this,
                                            LoginActivity.class);
                                    startActivity(loginIntent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}
