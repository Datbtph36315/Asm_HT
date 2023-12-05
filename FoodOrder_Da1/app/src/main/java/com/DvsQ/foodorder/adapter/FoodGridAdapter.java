package com.DvsQ.foodorder.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.databinding.ItemFoodGridBinding;
import com.DvsQ.foodorder.listener.IOnClickFoodItemListener;
import com.DvsQ.foodorder.model.Food;
import com.DvsQ.foodorder.utils.GlideUtils;

import java.util.List;

public class FoodGridAdapter extends RecyclerView.Adapter<FoodGridAdapter.FoodGridViewHolder> {

    private final List<Food> mListFoods;
    public final IOnClickFoodItemListener iOnClickFoodItemListener;

    // Giao diện để xử lý sự kiện click trên mỗi mục thức ăn
    public FoodGridAdapter(List<Food> mListFoods, IOnClickFoodItemListener iOnClickFoodItemListener) {
        this.mListFoods = mListFoods;
        this.iOnClickFoodItemListener = iOnClickFoodItemListener;
    }

    // Phương thức được gọi khi RecyclerView cần tạo một ViewHolder mới
    @NonNull
    @Override
    public FoodGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng data binding để tạo ViewHolder từ layout XML
        ItemFoodGridBinding itemFoodGridBinding = ItemFoodGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodGridViewHolder(itemFoodGridBinding);
    }

    // Phương thức được gọi để hiển thị dữ liệu tại một vị trí cụ thể
    @Override
    public void onBindViewHolder(@NonNull FoodGridViewHolder holder, int position) {
        // Lấy dữ liệu thức ăn từ danh sách
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }

        // Load hình ảnh bằng Glide
        GlideUtils.loadUrl(food.getImage(), holder.mItemFoodGridBinding.imgFood);

        // Xử lý giá và giảm giá (nếu có)
        if (food.getSale() <= 0) {
            holder.mItemFoodGridBinding.tvSaleOff.setVisibility(View.GONE);
            holder.mItemFoodGridBinding.tvPrice.setVisibility(View.GONE);

            String strPrice = food.getPrice() + Constant.CURRENCY;
            holder.mItemFoodGridBinding.tvPriceSale.setText(strPrice);
        } else {
            holder.mItemFoodGridBinding.tvSaleOff.setVisibility(View.VISIBLE);
            holder.mItemFoodGridBinding.tvPrice.setVisibility(View.VISIBLE);

            String strSale = "Giảm " + food.getSale() + "%";
            holder.mItemFoodGridBinding.tvSaleOff.setText(strSale);

            String strOldPrice = food.getPrice() + Constant.CURRENCY;
            holder.mItemFoodGridBinding.tvPrice.setText(strOldPrice);
            holder.mItemFoodGridBinding.tvPrice.setPaintFlags(holder.mItemFoodGridBinding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String strRealPrice = food.getRealPrice() + Constant.CURRENCY;
            holder.mItemFoodGridBinding.tvPriceSale.setText(strRealPrice);
        }

        // Thiết lập tên thức ăn và xử lý sự kiện click
        holder.mItemFoodGridBinding.tvFoodName.setText(food.getName());

        holder.mItemFoodGridBinding.layoutItem.setOnClickListener(v -> iOnClickFoodItemListener.onClickItemFood(food));
    }

    // Phương thức trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return null == mListFoods ? 0 : mListFoods.size();
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item trong RecyclerView
    public static class FoodGridViewHolder extends RecyclerView.ViewHolder {

        private final ItemFoodGridBinding mItemFoodGridBinding;

        // Constructor của ViewHolder, khởi tạo các thành phần giao diện
        public FoodGridViewHolder(ItemFoodGridBinding itemFoodGridBinding) {
            super(itemFoodGridBinding.getRoot());
            this.mItemFoodGridBinding = itemFoodGridBinding;
        }
    }
}
