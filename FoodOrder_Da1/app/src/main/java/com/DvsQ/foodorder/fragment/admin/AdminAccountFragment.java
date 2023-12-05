package com.DvsQ.foodorder.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.activity.AdminMainActivity;
import com.DvsQ.foodorder.activity.AdminReportActivity;
import com.DvsQ.foodorder.activity.ChangePasswordActivity;
import com.DvsQ.foodorder.activity.SignInActivity;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.databinding.FragmentAdminAccountBinding;
import com.DvsQ.foodorder.fragment.BaseFragment;
import com.DvsQ.foodorder.prefs.DataStoreManager;

public class AdminAccountFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAdminAccountBinding fragmentAdminAccountBinding = FragmentAdminAccountBinding.inflate(inflater, container, false);

        // Hiển thị địa chỉ email của người dùng
        fragmentAdminAccountBinding.tvEmail.setText(DataStoreManager.getUser().getEmail());

        // Xử lý sự kiện khi người dùng nhấn vào các tùy chọn
        fragmentAdminAccountBinding.layoutReport.setOnClickListener(v -> onClickReport());
        fragmentAdminAccountBinding.layoutSignOut.setOnClickListener(v -> onClickSignOut());
        fragmentAdminAccountBinding.layoutChangePassword.setOnClickListener(v -> onClickChangePassword());

        return fragmentAdminAccountBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        // Thiết lập thanh công cụ cho Fragment
        if (getActivity() != null) {
            ((AdminMainActivity) getActivity()).setToolBar(getString(R.string.account));
        }
    }

    // Xử lý sự kiện khi người dùng nhấn vào tùy chọn xem báo cáo
    private void onClickReport() {
        GlobalFunction.startActivity(getActivity(), AdminReportActivity.class);
    }

    // Xử lý sự kiện khi người dùng nhấn vào tùy chọn đổi mật khẩu
    private void onClickChangePassword() {
        GlobalFunction.startActivity(getActivity(), ChangePasswordActivity.class);
    }

    // Xử lý sự kiện khi người dùng nhấn vào tùy chọn đăng xuất
    private void onClickSignOut() {
        // Đăng xuất khỏi tài khoản Firebase
        FirebaseAuth.getInstance().signOut();

        // Xóa thông tin tài khoản khỏi DataStoreManager
        DataStoreManager.setUser(null);

        // Chuyển đến màn hình đăng nhập và kết thúc tất cả các Activity trước đó
        GlobalFunction.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }
}
