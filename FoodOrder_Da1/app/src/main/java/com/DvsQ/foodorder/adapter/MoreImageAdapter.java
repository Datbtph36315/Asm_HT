package com.DvsQ.foodorder.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DvsQ.foodorder.databinding.ItemMoreImageBinding;
import com.DvsQ.foodorder.model.Image;
import com.DvsQ.foodorder.utils.GlideUtils;

import java.util.List;

public class MoreImageAdapter extends RecyclerView.Adapter<MoreImageAdapter.MoreImageViewHolder> {

    private final List<Image> mListImages;

    // Constructor, nhận danh sách các hình ảnh để hiển thị
    public MoreImageAdapter(List<Image> mListImages) {
        this.mListImages = mListImages;
    }

    // Phương thức này được gọi khi RecyclerView cần tạo một ViewHolder mới
    @NonNull
    @Override
    public MoreImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng data binding để tạo ViewHolder từ layout XML
        ItemMoreImageBinding itemMoreImageBinding = ItemMoreImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoreImageViewHolder(itemMoreImageBinding);
    }

    // Phương thức này được gọi để hiển thị dữ liệu tại một vị trí cụ thể
    @Override
    public void onBindViewHolder(@NonNull MoreImageViewHolder holder, int position) {
        // Lấy dữ liệu hình ảnh từ danh sách
        Image image = mListImages.get(position);
        if (image == null) {
            return;
        }

        // Load hình ảnh bằng Glide
        GlideUtils.loadUrl(image.getUrl(), holder.mItemMoreImageBinding.imageFood);
    }

    // Phương thức trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return null == mListImages ? 0 : mListImages.size();
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item trong RecyclerView
    public static class MoreImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemMoreImageBinding mItemMoreImageBinding;

        // Constructor của ViewHolder, khởi tạo các thành phần giao diện
        public MoreImageViewHolder(ItemMoreImageBinding itemMoreImageBinding) {
            super(itemMoreImageBinding.getRoot());
            this.mItemMoreImageBinding = itemMoreImageBinding;
        }
    }
}
