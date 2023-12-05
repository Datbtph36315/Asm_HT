package com.DvsQ.foodorder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DvsQ.foodorder.ControllerApplication;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.activity.MainActivity;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.databinding.FragmentFeedbackBinding;
import com.DvsQ.foodorder.model.Feedback;
import com.DvsQ.foodorder.prefs.DataStoreManager;
import com.DvsQ.foodorder.utils.StringUtil;

public class FeedbackFragment extends BaseFragment {

    private FragmentFeedbackBinding mFragmentFeedbackBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho Fragment sử dụng FragmentFeedbackBinding
        mFragmentFeedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false);

        // Thiết lập email mặc định từ tài khoản người dùng đã đăng nhập
        mFragmentFeedbackBinding.edtEmail.setText(DataStoreManager.getUser().getEmail());

        // Thiết lập sự kiện click cho nút "Send Feedback"
        mFragmentFeedbackBinding.tvSendFeedback.setOnClickListener(v -> onClickSendFeedback());

        return mFragmentFeedbackBinding.getRoot();
    }

    // Xử lý sự kiện khi người dùng click nút "Send Feedback"
    private void onClickSendFeedback() {
        if (getActivity() == null) {
            return;
        }
        MainActivity activity = (MainActivity) getActivity();

        // Lấy thông tin từ các trường nhập liệu
        String strName = mFragmentFeedbackBinding.edtName.getText().toString();
        String strPhone = mFragmentFeedbackBinding.edtPhone.getText().toString();
        String strEmail = mFragmentFeedbackBinding.edtEmail.getText().toString();
        String strComment = mFragmentFeedbackBinding.edtComment.getText().toString();

        // Kiểm tra tính hợp lệ của dữ liệu nhập liệu
        if (StringUtil.isEmpty(strName)) {
            GlobalFunction.showToastMessage(activity, getString(R.string.name_require));
        } else if (StringUtil.isEmpty(strComment)) {
            GlobalFunction.showToastMessage(activity, getString(R.string.comment_require));
        } else {
            // Hiển thị dialog tiến trình khi gửi phản hồi
            activity.showProgressDialog(true);

            // Tạo đối tượng Feedback từ thông tin nhập liệu
            Feedback feedback = new Feedback(strName, strPhone, strEmail, strComment);

            // Gửi phản hồi đến cơ sở dữ liệu Firebase
            ControllerApplication.get(getActivity()).getFeedbackDatabaseReference()
                    .child(String.valueOf(System.currentTimeMillis()))
                    .setValue(feedback, (databaseError, databaseReference) -> {
                        // Ẩn dialog tiến trình khi gửi phản hồi thành công
                        activity.showProgressDialog(false);
                        // Xử lý khi gửi phản hồi thành công
                        sendFeedbackSuccess();
                    });
        }
    }

    // Xử lý khi gửi phản hồi thành công
    public void sendFeedbackSuccess() {
        // Ẩn bàn phím mềm sau khi gửi phản hồi
        GlobalFunction.hideSoftKeyboard(getActivity());
        // Hiển thị thông báo gửi phản hồi thành công
        GlobalFunction.showToastMessage(getActivity(), getString(R.string.send_feedback_success));
        // Xóa dữ liệu đã nhập trong các trường
        mFragmentFeedbackBinding.edtName.setText("");
        mFragmentFeedbackBinding.edtPhone.setText("");
        mFragmentFeedbackBinding.edtComment.setText("");
    }

    // Override phương thức initToolbar từ lớp cha BaseFragment
    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            // Thiết lập thanh toolbar với tiêu đề "Feedback" trong Activity chứa Fragment
            ((MainActivity) getActivity()).setToolBar(false, getString(R.string.feedback));
        }
    }
}
