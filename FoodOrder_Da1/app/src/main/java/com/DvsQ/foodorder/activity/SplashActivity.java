package com.DvsQ.foodorder.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.prefs.DataStoreManager;
import com.DvsQ.foodorder.utils.StringUtil;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đặt bố cục cho hoạt động là "activity_splash"
        setContentView(R.layout.activity_splash);

        // Tạo một đối tượng Handler để lên lịch một hành động sau 4 giây
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::goToNextActivity, 4000);
    }

    // Phương thức chuyển hướng đến hoạt động tiếp theo
    private void goToNextActivity() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (DataStoreManager.getUser() != null && !StringUtil.isEmpty(DataStoreManager.getUser().getEmail())) {
            // Nếu đã đăng nhập, chuyển đến MainActivity
            GlobalFunction.gotoMainActivity(this);
        } else {
            // Nếu chưa đăng nhập, chuyển đến SignInActivity
            GlobalFunction.startActivity(this, SignInActivity.class);
        }

        // Đóng màn hình SplashActivity
        finish();
    }
}
