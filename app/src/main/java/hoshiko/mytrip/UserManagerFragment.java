package hoshiko.mytrip;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * Created by An on 1/19/2018.
 */

public class UserManagerFragment extends Fragment {

    private static String TAG = UserManagerFragment.class.getSimpleName();
    private final int REQUEST_CODE = 2;

    private ImageButton btnBack, btnAvatarChange;
    private ImageView imgProfileImage;
    private EditText edtName, edtEmail;
    private TextView txtUID;
    private Button btnEdit, btnSave;

    private String lastDisplayName = "";
    private String lastEmail = "";
    private Bitmap bitmap;
    private FirebaseUser currentUser;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    public UserManagerFragment(){
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://mytrip-33a4a.appspot.com/Profile Images");

        lastDisplayName = currentUser.getDisplayName();
        lastEmail = currentUser.getEmail();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View userManagerView = inflater.inflate(R.layout.fragment_user_manager,
                container, false);
        Log.d(TAG, "onCreateView");
        addControls(userManagerView);
        if(currentUser != null){
            addEvents(userManagerView);
        }
        return userManagerView;
    }

    private void addControls(View userManagerView) {
        btnBack = userManagerView.findViewById(R.id.btnBack);
        btnAvatarChange = userManagerView.findViewById(R.id.btnAvatarChange);
        imgProfileImage = userManagerView.findViewById(R.id.profile_image);
        edtName = userManagerView.findViewById(R.id.edtUserName);
        edtEmail = userManagerView.findViewById(R.id.edtEmail);
        txtUID = userManagerView.findViewById(R.id.txtUid);
        btnEdit = userManagerView.findViewById(R.id.btnUserInfoChange);
        btnSave = userManagerView.findViewById(R.id.btnSaveUserInfo);
    }

    private void addEvents(View userManagerView) {
        edtName.setText(currentUser.getDisplayName());
        edtEmail.setText(currentUser.getEmail());
        txtUID.setText(currentUser.getUid());

        if(currentUser.getPhotoUrl()!= null){
            Glide.with(getActivity()).load(currentUser.getPhotoUrl().toString())
                    .into(imgProfileImage);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                SettingsFragment settingsFragment = new SettingsFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragmentContainer, settingsFragment).commit();
                }});

        btnAvatarChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý cập nhật ảnh đại diện.
                updateUserProfileImage();

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
                btnSave.setEnabled(false);
                edtName.setEnabled(false);
                edtEmail.setEnabled(false);
                btnEdit.setEnabled(true);
                //TODO: Xử lý cập nhật thông tin user
                updateNameAndEmail();
            }
        });

    }
//    private void setProfileImage(Uri url){
//        if(!url.toString().isEmpty()){
//            Glide.with(getActivity()).load(url).into(imgProfileImage);
//        }
//        else {
//            imgProfileImage.setImageResource(R.drawable.icon_profile_empty);
//        }
//    }
    private void updateNameAndEmail() {
        String hoTen = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        if(!hoTen.equals(lastDisplayName)){
            if (hoTen.isEmpty() || email.isEmpty()) {
                return;
            }
            if (hoTen.isEmpty()) {
                Log.e(TAG, "Họ tên bị bỏ trống !");
                Toast.makeText(getActivity(), "Họ tên bị bỏ trống !", Toast.LENGTH_SHORT).show();
                edtName.setText(currentUser.getDisplayName());
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
                                //currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                lastDisplayName = currentUser.getDisplayName();
                                Log.d(TAG, "User profile updated: " + currentUser.getDisplayName());
                                Toast.makeText(getActivity(), "Đã cập nhật Tên.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if(!email.equals(lastEmail)){
            if (email.isEmpty()) {
                Log.e(TAG, "Email tên bị bỏ trống !");
                Toast.makeText(getActivity(), "Email bị bỏ trống !", Toast.LENGTH_SHORT).show();
                edtEmail.setText(currentUser.getEmail());
                return;
            }
            currentUser.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                lastEmail = currentUser.getEmail();
                                Log.d(TAG, "User email address updated: " + currentUser.getEmail());
                                Toast.makeText(getActivity(), "Đã cập nhật Email.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void updateUserProfileImage() {

        // Intent chọn ảnh từ gallery
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
        galleryIntent.setType("image/*");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        // Intent lấy ảnh từ camera
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // Intent lựa chọn lấy ảnh camera hoặc gallery
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
        chooser.putExtra(Intent.EXTRA_TITLE , "Select Image From: ");
        Intent[] intentArray =  {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

        startActivityForResult(chooser, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null){

            if(data.getData() != null){
                try
                {   // Xử lý lấy ảnh từ gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    imgProfileImage.setImageBitmap(bitmap);
                    upLoadProfileImageToFirebaseStorage();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }else{
                //Xử lý lấy ảnh từ camera
                bitmap = (Bitmap) data.getExtras().get("data");
                imgProfileImage.setImageBitmap(bitmap);
                upLoadProfileImageToFirebaseStorage();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upLoadProfileImageToFirebaseStorage(){
        imgProfileImage.setDrawingCacheEnabled(true);
        imgProfileImage.buildDrawingCache();
        Bitmap bitmap = imgProfileImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        Calendar calendar = Calendar.getInstance();
        StorageReference imgRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "Upload profile image to Firebase Storage: FAILED");
                Toast.makeText(getContext(), "Cập nhật ảnh đại diện thất bại",
                        Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Log.d(TAG, "Upload profile image to Firebase Storage: SUCCESS");
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                // cập nhật lại photoUrl của currentUser
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(downloadUrl)
                        .build();
                currentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Log.d(TAG, "User photo url updated: " + downloadUrl.toString());
                                    Toast.makeText(getActivity(), "Đã cập nhật Avatar.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


}
