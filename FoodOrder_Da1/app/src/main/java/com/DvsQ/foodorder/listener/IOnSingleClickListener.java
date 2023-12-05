package com.DvsQ.foodorder.listener;

import android.os.SystemClock;
import android.view.View;

public abstract class IOnSingleClickListener implements View.OnClickListener {

    // Thời gian tối thiểu giữa các lần click để xem là lần click duy nhất
    private static final long MIN_CLICK_INTERVAL = 600;

    // Biến để lưu thời điểm click gần đây nhất
    private long mLastClickTime;

    // Phương thức trừu tượng để xử lý sự kiện khi chỉ có một lần click
    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        // Lấy thời điểm click hiện tại
        long currentClickTime = SystemClock.uptimeMillis();

        // Tính thời gian giữa hai lần click
        long elapsedTime = currentClickTime - mLastClickTime;

        // Cập nhật thời điểm click gần đây nhất
        mLastClickTime = currentClickTime;

        // Nếu khoảng thời gian giữa hai lần click nhỏ hơn hoặc bằng giá trị MIN_CLICK_INTERVAL, không xử lý sự kiện
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;

        // Nếu khoảng thời gian giữa hai lần click lớn hơn MIN_CLICK_INTERVAL, xử lý sự kiện click duy nhất
        onSingleClick(v);
    }
}
