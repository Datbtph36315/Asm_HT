package com.DvsQ.foodorder.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.databinding.ActivitySignUpBinding;
import com.DvsQ.foodorder.model.User;
import com.DvsQ.foodorder.prefs.DataStoreManager;
import com.DvsQ.foodorder.utils.StringUtil;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding mActivitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kết nối đối tượng binding với layout
        mActivitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignUpBinding.getRoot());

        // Mặc định chọn radio button "Người dùng"
        mActivitySignUpBinding.rdbUser.setChecked(true);

        // Bắt sự kiện khi người dùng nhấn nút quay lại
        mActivitySignUpBinding.imgBack.setOnClickListener(v -> onBackPressed());

        // Bắt sự kiện khi người dùng nhấn chuyển đến SignInActivity
        mActivitySignUpBinding.layoutSignIn.setOnClickListener(v -> finish());

        // Bắt sự kiện khi người dùng nhấn nút Đăng ký
        mActivitySignUpBinding.btnSignUp.setOnClickListener(v -> onClickValidateSignUp());
    }

    // Phương thức xử lý khi người dùng nhấn nút Đăng ký
    private void onClickValidateSignUp() {
        // Lấy thông tin email và password từ giao diện người dùng
        String strEmail = mActivitySignUpBinding.edtEmail.getText().toString().trim();
        String strPassword = mActivitySignUpBinding.edtPassword.getText().toString().trim();

        // Kiểm tra các trường thông tin đăng ký
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra loại người dùng và thực hiện đăng ký
            if (mActivitySignUpBinding.rdbAdmin.isChecked()) {
                if (!strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_invalid_admin), Toast.LENGTH_SHORT).show();
                } else {
                    signUpUser(strEmail, strPassword);
                }
                return;
            }

            if (strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_invalid_user), Toast.LENGTH_SHORT).show();
            } else {
                signUpUser(strEmail, strPassword);
            }
        }
    }

    // Phương thức thực hiện đăng ký người dùng
    private void signUpUser(String email, String password) {
        // Hiển thị dialog tiến trình đang xử lý
        showProgressDialog(true);

        // Lấy đối tượng FirebaseAuth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Thực hiện đăng ký bằng email và password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Ẩn dialog tiến trình đang xử lý
                    showProgressDialog(false);

                    // Kiểm tra kết quả đăng ký
                    if (task.isSuccessful()) {
                        // Nếu đăng ký thành công, lấy đối tượng FirebaseUser
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // Tạo đối tượng User từ thông tin đăng ký
                            User userObject = new User(user.getEmail(), password);

                            // Nếu là admin, đặt thuộc tính admin
                            if (user.getEmail() != null && user.getEmail().contains(Constant.ADMIN_EMAIL_FORMAT)) {
                                userObject.setAdmin(true);
                            }

                            // Lưu thông tin người dùng vào DataStoreManager
                            DataStoreManager.setUser(userObject);

                            // Chuyển đến MainActivity và kết thúc tất cả các hoạt động trước đó
                            GlobalFunction.gotoMainActivity(this);
                            finishAffinity();
                        }
                    } else {
                        // Nếu đăng ký thất bại, thông báo lỗi
                        Toast.makeText(SignUpActivity.this, getString(R.string.msg_sign_up_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
