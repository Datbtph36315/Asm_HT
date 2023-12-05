package com.DvsQ.foodorder.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.databinding.ItemCartBinding;
import com.DvsQ.foodorder.model.Food;
import com.DvsQ.foodorder.utils.GlideUtils;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Food> mListFoods;
    private final IClickListener iClickListener;

    // Giao diện để xử lý sự kiện click trên item trong giỏ hàng
    public interface IClickListener {
        void clickDeteteFood(Food food, int position);

        void updateItemFood(Food food, int position);
    }

    public CartAdapter(List<Food> mListFoods, IClickListener iClickListener) {
        this.mListFoods = mListFoods;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng data binding để tạo ViewHolder từ layout XML
        ItemCartBinding itemCartBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(itemCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Lấy dữ liệu thức ăn từ danh sách
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }

        // Load hình ảnh bằng Glide
        GlideUtils.loadUrl(food.getImage(), holder.mItemCartBinding.imgFoodCart);

        // Thiết lập tên thức ăn
        holder.mItemCartBinding.tvFoodNameCart.setText(food.getName());

        // Thiết lập giá thức ăn và xử lý giảm giá nếu có
        String strFoodPriceCart = food.getPrice() + Constant.CURRENCY;
        if (food.getSale() > 0) {
            strFoodPriceCart = food.getRealPrice() + Constant.CURRENCY;
        }
        holder.mItemCartBinding.tvFoodPriceCart.setText(strFoodPriceCart);

        // Thiết lập số lượng thức ăn và xử lý sự kiện tăng/giảm số lượng
        holder.mItemCartBinding.tvCount.setText(String.valueOf(food.getCount()));

        holder.mItemCartBinding.tvSubtract.setOnClickListener(v -> {
            String strCount = holder.mItemCartBinding.tvCount.getText().toString();
            int count = Integer.parseInt(strCount);
            if (count <= 1) {
                return;
            }
            int newCount = count - 1;
            holder.mItemCartBinding.tvCount.setText(String.valueOf(newCount));

            int totalPrice = food.getRealPrice() * newCount;
            food.setCount(newCount);
            food.setTotalPrice(totalPrice);

            iClickListener.updateItemFood(food, holder.getAdapterPosition());
        });

        holder.mItemCartBinding.tvAdd.setOnClickListener(v -> {
            int newCount = Integer.parseInt(holder.mItemCartBinding.tvCount.getText().toString()) + 1;
            holder.mItemCartBinding.tvCount.setText(String.valueOf(newCount));

            int totalPrice = food.getRealPrice() * newCount;
            food.setCount(newCount);
            food.setTotalPrice(totalPrice);

            iClickListener.updateItemFood(food, holder.getAdapterPosition());
        });

        // Xử lý sự kiện khi người dùng click vào nút xóa
        holder.mItemCartBinding.tvDelete.setOnClickListener(v
                -> iClickListener.clickDeteteFood(food, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return null == mListFoods ? 0 : mListFoods.size();
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item trong RecyclerView
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private final ItemCartBinding mItemCartBinding;

        public CartViewHolder(ItemCartBinding itemCartBinding) {
            super(itemCartBinding.getRoot());
            this.mItemCartBinding = itemCartBinding;
        }
    }
}
