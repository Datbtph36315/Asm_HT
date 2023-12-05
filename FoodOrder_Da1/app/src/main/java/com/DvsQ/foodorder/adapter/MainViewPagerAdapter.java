package com.DvsQ.foodorder.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.DvsQ.foodorder.fragment.AccountFragment;
import com.DvsQ.foodorder.fragment.CartFragment;
import com.DvsQ.foodorder.fragment.ContactFragment;
import com.DvsQ.foodorder.fragment.FeedbackFragment;
import com.DvsQ.foodorder.fragment.HomeFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    // Constructor, nhận một FragmentActivity để quản lý các fragment
    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // Phương thức này được gọi khi cần tạo một Fragment mới tại một vị trí cụ thể
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tùy thuộc vào vị trí, trả về fragment tương ứng
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new CartFragment();

            case 2:
                return new FeedbackFragment();

            case 3:
                return new ContactFragment();

            case 4:
                return new AccountFragment();

            default:
                return new HomeFragment();
        }
    }

    // Phương thức này trả về tổng số lượng fragment trong ViewPager2
    @Override
    public int getItemCount() {
        return 5; // Có thể điều chỉnh số này tùy thuộc vào số lượng fragment bạn muốn hiển thị
    }
}
