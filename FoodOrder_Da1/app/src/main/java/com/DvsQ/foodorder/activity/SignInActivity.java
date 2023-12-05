package com.DvsQ.foodorder.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.databinding.ActivitySignInBinding;
import com.DvsQ.foodorder.model.User;
import com.DvsQ.foodorder.prefs.DataStoreManager;
import com.DvsQ.foodorder.utils.StringUtil;

public class SignInActivity extends BaseActivity {

    private ActivitySignInBinding mActivitySignInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kết nối đối tượng binding với layout
        mActivitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignInBinding.getRoot());

        // Mặc định chọn radio button "Người dùng"
        mActivitySignInBinding.rdbUser.setChecked(true);

        // Bắt sự kiện khi người dùng chọn chuyển đến SignUpActivity
        mActivitySignInBinding.layoutSignUp.setOnClickListener(
                v -> GlobalFunction.startActivity(SignInActivity.this, SignUpActivity.class));

        // Bắt sự kiện khi người dùng nhấn nút Đăng nhập
        mActivitySignInBinding.btnSignIn.setOnClickListener(v -> onClickValidateSignIn());

        // Bắt sự kiện khi người dùng nhấn Quên mật khẩu
        mActivitySignInBinding.tvForgotPassword.setOnClickListener(v -> onClickForgotPassword());
    }

    // Phương thức chuyển đến ForgotPasswordActivity
    private void onClickForgotPassword() {
        GlobalFunction.startActivity(this, ForgotPasswordActivity.class);
    }

    // Phương thức xử lý khi người dùng nhấn nút Đăng nhập
    private void onClickValidateSignIn() {
        // Lấy thông tin email và password từ giao diện người dùng
        String strEmail = mActivitySignInBinding.edtEmail.getText().toString().trim();
        String strPassword = mActivitySignInBinding.edtPassword.getText().toString().trim();

        // Kiểm tra các trường thông tin đăng nhập
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra loại người dùng và thực hiện đăng nhập
            if (mActivitySignInBinding.rdbAdmin.isChecked()) {
                if (!strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid_admin), Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(strEmail, strPassword);
                }
                return;
            }

            if (strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid_user), Toast.LENGTH_SHORT).show();
            } else {
                signInUser(strEmail, strPassword);
            }
        }
    }

    // Phương thức thực hiện đăng nhập người dùng
    private void signInUser(String email, String password) {
        // Hiển thị dialog tiến trình đang xử lý
        showProgressDialog(true);

        // Lấy đối tượng FirebaseAuth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Thực hiện đăng nhập bằng email và password
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Ẩn dialog tiến trình đang xử lý
                    showProgressDialog(false);

                    // Kiểm tra kết quả đăng nhập
                    if (task.isSuccessful()) {
                        // Nếu đăng nhập thành công, lấy đối tượng FirebaseUser
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // Tạo đối tượng User từ thông tin đăng nhập
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
                        // Nếu đăng nhập thất bại, thông báo lỗi
                        Toast.makeText(SignInActivity.this, getString(R.string.msg_sign_in_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
