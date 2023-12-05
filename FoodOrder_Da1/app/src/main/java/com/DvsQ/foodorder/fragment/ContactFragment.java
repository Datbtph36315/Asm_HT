package com.DvsQ.foodorder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.activity.MainActivity;
import com.DvsQ.foodorder.adapter.ContactAdapter;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.databinding.FragmentContactBinding;
import com.DvsQ.foodorder.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends BaseFragment {

    private ContactAdapter mContactAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho Fragment sử dụng FragmentContactBinding
        FragmentContactBinding mFragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);

        // Khởi tạo ContactAdapter để quản lý danh sách liên hệ và hiển thị chúng trong RecyclerView
        mContactAdapter = new ContactAdapter(getActivity(), getListContact(), () -> GlobalFunction.callPhoneNumber(getActivity()));

        // Thiết lập LayoutManager cho RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mFragmentContactBinding.rcvData.setNestedScrollingEnabled(false);
        mFragmentContactBinding.rcvData.setFocusable(false);
        mFragmentContactBinding.rcvData.setLayoutManager(layoutManager);

        // Thiết lập Adapter cho RecyclerView
        mFragmentContactBinding.rcvData.setAdapter(mContactAdapter);

        return mFragmentContactBinding.getRoot();
    }

    // Phương thức này trả về một danh sách các đối tượng Contact đại diện cho các loại liên hệ khác nhau
    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));
        contactArrayList.add(new Contact(Contact.SKYPE, R.drawable.ic_skype));
        contactArrayList.add(new Contact(Contact.YOUTUBE, R.drawable.ic_youtube));
        contactArrayList.add(new Contact(Contact.ZALO, R.drawable.ic_zalo));

        return contactArrayList;
    }

    // Override phương thức initToolbar từ lớp cha BaseFragment
    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            // Thiết lập thanh toolbar với tiêu đề là "Contact" trong Activity chứa Fragment
            ((MainActivity) getActivity()).setToolBar(false, getString(R.string.contact));
        }
    }

    // Phương thức onDestroy được gọi khi Fragment bị hủy
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên của ContactAdapter
        mContactAdapter.release();
    }
}
