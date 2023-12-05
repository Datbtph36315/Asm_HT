package com.DvsQ.foodorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.databinding.ItemOrderBinding;
import com.DvsQ.foodorder.model.Order;
import com.DvsQ.foodorder.utils.DateTimeUtils;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private final List<Order> mListOrder;

    // Constructor, nhận Context và danh sách các đơn đặt hàng
    public OrderAdapter(Context mContext, List<Order> mListOrder) {
        this.mContext = mContext;
        this.mListOrder = mListOrder;
    }

    // Phương thức này được gọi khi RecyclerView cần tạo một ViewHolder mới
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng data binding để tạo ViewHolder từ layout XML
        ItemOrderBinding itemOrderBinding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new OrderViewHolder(itemOrderBinding);
    }

    // Phương thức này được gọi để hiển thị dữ liệu tại một vị trí cụ thể
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        // Lấy dữ liệu đơn đặt hàng từ danh sách
        Order order = mListOrder.get(position);
        if (order == null) {
            return;
        }

        // Kiểm tra trạng thái đơn đặt hàng và thay đổi màu nền tương ứng
        if (order.isCompleted()) {
            holder.mItemOrderBinding.layoutItem.setBackgroundColor(mContext.getResources().getColor(R.color.black_overlay));
        } else {
            holder.mItemOrderBinding.layoutItem.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        // Hiển thị thông tin đơn đặt hàng trong giao diện
        holder.mItemOrderBinding.tvId.setText(String.valueOf(order.getId()));
        holder.mItemOrderBinding.tvName.setText(order.getName());
        holder.mItemOrderBinding.tvPhone.setText(order.getPhone());
        holder.mItemOrderBinding.tvAddress.setText(order.getAddress());
        holder.mItemOrderBinding.tvMenu.setText(order.getFoods());
        holder.mItemOrderBinding.tvDate.setText(DateTimeUtils.convertTimeStampToDate(order.getId()));

        String strAmount = order.getAmount() + Constant.CURRENCY;
        holder.mItemOrderBinding.tvTotalAmount.setText(strAmount);

        // Hiển thị phương thức thanh toán
        String paymentMethod = "";
        if (Constant.TYPE_PAYMENT_CASH == order.getPayment()) {
            paymentMethod = Constant.PAYMENT_METHOD_CASH;
        }
        holder.mItemOrderBinding.tvPayment.setText(paymentMethod);
    }

    // Phương thức trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        if (mListOrder != null) {
            return mListOrder.size();
        }
        return 0;
    }

    // Phương thức giải phóng tài nguyên khi không cần sử dụng Adapter nữa
    public void release() {
        mContext = null;
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item trong RecyclerView
    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private final ItemOrderBinding mItemOrderBinding;

        // Constructor của ViewHolder, khởi tạo các thành phần giao diện
        public OrderViewHolder(@NonNull ItemOrderBinding itemOrderBinding) {
            super(itemOrderBinding.getRoot());
            this.mItemOrderBinding = itemOrderBinding;
        }
    }
}
