package com.DvsQ.foodorder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DvsQ.foodorder.databinding.ItemFoodPopularBinding;
import com.DvsQ.foodorder.listener.IOnClickFoodItemListener;
import com.DvsQ.foodorder.model.Food;
import com.DvsQ.foodorder.utils.GlideUtils;

import java.util.List;

public class FoodPopularAdapter extends RecyclerView.Adapter<FoodPopularAdapter.FoodPopularViewHolder> {

    private final List<Food> mListFoods;
    public final IOnClickFoodItemListener iOnClickFoodItemListener;

    // Giao diện để xử lý sự kiện click trên mỗi mục thức ăn phổ biến
    public FoodPopularAdapter(List<Food> mListFoods, IOnClickFoodItemListener iOnClickFoodItemListener) {
        this.mListFoods = mListFoods;
        this.iOnClickFoodItemListener = iOnClickFoodItemListener;
    }

    // Phương thức được gọi khi RecyclerView cần tạo một ViewHolder mới
    @NonNull
    @Override
    public FoodPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng data binding để tạo ViewHolder từ layout XML
        ItemFoodPopularBinding itemFoodPopularBinding = ItemFoodPopularBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodPopularViewHolder(itemFoodPopularBinding);
    }

    // Phương thức được gọi để hiển thị dữ liệu tại một vị trí cụ thể
    @Override
    public void onBindViewHolder(@NonNull FoodPopularViewHolder holder, int position) {
        // Lấy dữ liệu thức ăn từ danh sách
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }

        // Load hình ảnh banner bằng Glide
        GlideUtils.loadUrlBanner(food.getBanner(), holder.mItemFoodPopularBinding.imageFood);

        // Xử lý giảm giá (nếu có)
        if (food.getSale() <= 0) {
            holder.mItemFoodPopularBinding.tvSaleOff.setVisibility(View.GONE);
        } else {
            holder.mItemFoodPopularBinding.tvSaleOff.setVisibility(View.VISIBLE);
            String strSale = "Giảm " + food.getSale() + "%";
            holder.mItemFoodPopularBinding.tvSaleOff.setText(strSale);
        }

        // Xử lý sự kiện click
        holder.mItemFoodPopularBinding.layoutItem.setOnClickListener(v -> iOnClickFoodItemListener.onClickItemFood(food));
    }

    // Phương thức trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        if (mListFoods != null) {
            return mListFoods.size();
        }
        return 0;
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item trong RecyclerView
    public static class FoodPopularViewHolder extends RecyclerView.ViewHolder {

        private final ItemFoodPopularBinding mItemFoodPopularBinding;

        // Constructor của ViewHolder, khởi tạo các thành phần giao diện
        public FoodPopularViewHolder(@NonNull ItemFoodPopularBinding itemFoodPopularBinding) {
            super(itemFoodPopularBinding.getRoot());
            this.mItemFoodPopularBinding = itemFoodPopularBinding;
        }
    }
}
