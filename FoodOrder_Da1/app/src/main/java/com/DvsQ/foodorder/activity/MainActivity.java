package com.DvsQ.foodorder.activity;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.adapter.MainViewPagerAdapter;
import com.DvsQ.foodorder.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kết nối đối tượng binding với layout
        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());

        // Vô hiệu hóa đầu vào người dùng cho ViewPager2
        mActivityMainBinding.viewpager2.setUserInputEnabled(false);

        // Khởi tạo và thiết lập adapter cho ViewPager2
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this);
        mActivityMainBinding.viewpager2.setAdapter(mainViewPagerAdapter);

        // Bắt sự kiện khi trang được chọn trong ViewPager2
        mActivityMainBinding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Cập nhật trạng thái của BottomNavigation khi trang thay đổi
                updateBottomNavigation(position);
            }
        });

        // Bắt sự kiện khi mục được chọn trong BottomNavigation
        mActivityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            handleBottomNavigation(item);
            return true;
        });
    }

    // Phương thức xử lý khi nút quay lại được nhấn
    @Override
    public void onBackPressed() {
        // Hiển thị hộp thoại xác nhận thoát ứng dụng
        showConfirmExitApp();
    }

    // Phương thức hiển thị hộp thoại xác nhận thoát ứng dụng
    private void showConfirmExitApp() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive((dialog, which) -> finishAffinity())
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }

    // Phương thức cập nhật thanh công cụ (Toolbar) khi chuyển giữa các trang
    public void setToolBar(boolean isHome, String title) {
        if (isHome) {
            // Ẩn thanh công cụ khi ở trang chính
            mActivityMainBinding.toolbar.layoutToolbar.setVisibility(View.GONE);
        } else {
            // Hiển thị thanh công cụ và đặt tiêu đề
            mActivityMainBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
            mActivityMainBinding.toolbar.tvTitle.setText(title);
        }
    }

    // Phương thức cập nhật trạng thái của BottomNavigation khi chuyển giữa các trang
    private void updateBottomNavigation(int position) {
        // Dựa vào trang được chọn, cập nhật mục được chọn trong BottomNavigation
        switch (position) {
            case 0:
                mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_home).setChecked(true);
                break;

            case 1:
                mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_cart).setChecked(true);
                break;

            case 2:
                mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_feedback).setChecked(true);
                break;

            case 3:
                mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_contact).setChecked(true);
                break;

            case 4:
                mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_account).setChecked(true);
                break;
        }
    }

    // Phương thức xử lý khi mục được chọn trong BottomNavigation
    private void handleBottomNavigation(android.view.MenuItem item) {
        int id = item.getItemId();
        // Dựa vào mục được chọn, chuyển đến trang tương ứng trong ViewPager2
        if (id == R.id.nav_home) {
            mActivityMainBinding.viewpager2.setCurrentItem(0);
        } else if (id == R.id.nav_cart) {
            mActivityMainBinding.viewpager2.setCurrentItem(1);
        } else if (id == R.id.nav_feedback) {
            mActivityMainBinding.viewpager2.setCurrentItem(2);
        } else if (id == R.id.nav_contact) {
            mActivityMainBinding.viewpager2.setCurrentItem(3);
        } else if (id == R.id.nav_account) {
            mActivityMainBinding.viewpager2.setCurrentItem(4);
        }
    }
}
