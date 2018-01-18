package hoshiko.mytrip;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by An on 1/19/2018.
 */

public class UserManagerFragment extends Fragment {

    private static String TAG = UserManagerFragment.class.getSimpleName();

    private ImageButton btnBack, btnAvatarChange;
    private ImageView imgProfileImage;
    private EditText edtName, edtEmail;
    private TextView txtUID;
    private Button btnEdit, btnSave;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public UserManagerFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View userManagerView = inflater.inflate(R.layout.fragment_user_manager,
                container, false);
        btnBack = userManagerView.findViewById(R.id.btnBack);
        btnAvatarChange = userManagerView.findViewById(R.id.btnAvatarChange);
        imgProfileImage = userManagerView.findViewById(R.id.profile_image);
        edtName = userManagerView.findViewById(R.id.edtUserName);
        edtEmail = userManagerView.findViewById(R.id.edtEmail);
        btnEdit = userManagerView.findViewById(R.id.btnUserInfoChange);
        btnSave = userManagerView.findViewById(R.id.btnSaveUserInfo);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            edtName.setText(currentUser.getDisplayName());
            edtEmail.setText(currentUser.getEmail());
            txtUID.setText(currentUser.getUid());

            if(!currentUser.getPhotoUrl().toString().isEmpty()){
                Glide.with(userManagerView).load(currentUser.getPhotoUrl())
                        .into(imgProfileImage);
            }


            btnAvatarChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Xử lý cập nhật ảnh đại diện.

                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.fragmentContainer,new SettingsFragment()).commit();
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnEdit.setEnabled(false);
                    edtName.setEnabled(true);
                    edtEmail.setEnabled(true);
                    btnSave.setEnabled(true);
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Xử lý cập nhật thông tin user
                    updateNameAndEmail();
                }
            });

        }

        return userManagerView;
    }

    private void updateNameAndEmail() {
        String hoTen = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        if (hoTen.isEmpty() || email.isEmpty()) {
            return;
        }

        if (hoTen.isEmpty()) {
            Log.e(TAG, "Họ tên bị bỏ trống !");
            Toast.makeText(getActivity(), "Họ tên bị bỏ trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()) {
            Log.e(TAG, "Email tên bị bỏ trống !");
            Toast.makeText(getActivity(), "Email bị bỏ trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(hoTen)
                .build();

        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            Toast.makeText(getActivity(), "Đã cập nhật Tên.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        currentUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            Toast.makeText(getActivity(), "Đã cập nhật Email.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
