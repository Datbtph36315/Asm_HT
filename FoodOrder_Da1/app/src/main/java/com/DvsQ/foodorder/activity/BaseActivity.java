package com.DvsQ.foodorder.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.DvsQ.foodorder.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected MaterialDialog progressDialog, alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo dialog tiến trình và dialog thông báo khi hoạt động được tạo
        createProgressDialog();
        createAlertDialog();
    }

    // Phương thức tạo đối tượng ProgressDialog
    public void createProgressDialog() {
        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.waiting_message)
                .progress(true, 0)
                .build();
    }

    // Phương thức hiển thị hoặc ẩn ProgressDialog
    public void showProgressDialog(boolean value) {
        if (value) {
            // Hiển thị ProgressDialog nếu không hiển thị
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
                progressDialog.setCancelable(false); // Không cho phép hủy bỏ ProgressDialog
            }
        } else {
            // Ẩn ProgressDialog nếu đang hiển thị
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    // Phương thức ẩn ProgressDialog và AlertDialog
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    // Phương thức tạo đối tượng AlertDialog
    public void createAlertDialog() {
        alertDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .positiveText(R.string.action_ok)
                .cancelable(false)
                .build();
    }

    // Phương thức hiển thị AlertDialog với nội dung là chuỗi thông báo
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    // Phương thức hiển thị AlertDialog với nội dung từ tài nguyên chuỗi
    public void showAlertDialog(@StringRes int resourceId) {
        alertDialog.setContent(resourceId);
        alertDialog.show();
    }

    // Phương thức cấu hình khả năng hủy bỏ ProgressDialog
    public void setCancelProgress(boolean isCancel) {
        if (progressDialog != null) {
            progressDialog.setCancelable(isCancel);
        }
    }

    @Override
    protected void onDestroy() {
        // Đảm bảo rằng ProgressDialog và AlertDialog được ẩn trước khi hoạt động bị hủy
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }
}
