package com.DvsQ.foodorder.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.DvsQ.foodorder.R;

public class GlideUtils {

    // Phương thức load hình từ URL vào ImageView (được sử dụng cho banner)
    public static void loadUrlBanner(String url, ImageView imageView) {
        // Kiểm tra xem URL có rỗng hay không
        if (StringUtil.isEmpty(url)) {
            // Nếu rỗng, hiển thị hình mặc định
            imageView.setImageResource(R.drawable.img_no_image);
            return;
        }

        // Sử dụng thư viện Glide để tải hình từ URL và hiển thị vào ImageView
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.img_no_image) // Hình mặc định nếu có lỗi khi tải
                .dontAnimate() // Không sử dụng animation
                .into(imageView);
    }

    // Phương thức load hình từ URL vào ImageView (được sử dụng cho các trường hợp khác)
    public static void loadUrl(String url, ImageView imageView) {
        // Kiểm tra xem URL có rỗng hay không
        if (StringUtil.isEmpty(url)) {
            // Nếu rỗng, hiển thị hình mặc định
            imageView.setImageResource(R.drawable.image_no_available);
            return;
        }

        // Sử dụng thư viện Glide để tải hình từ URL và hiển thị vào ImageView
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.image_no_available) // Hình mặc định nếu có lỗi khi tải
                .dontAnimate() // Không sử dụng animation
                .into(imageView);
    }
}
