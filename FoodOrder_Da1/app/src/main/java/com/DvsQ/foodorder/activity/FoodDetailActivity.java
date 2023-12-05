package com.DvsQ.foodorder.activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.adapter.MoreImageAdapter;
import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.database.FoodDatabase;
import com.DvsQ.foodorder.databinding.ActivityFoodDetailBinding;
import com.DvsQ.foodorder.event.ReloadListCartEvent;
import com.DvsQ.foodorder.model.Food;
import com.DvsQ.foodorder.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FoodDetailActivity extends BaseActivity {

    private ActivityFoodDetailBinding mActivityFoodDetailBinding;
    private Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFoodDetailBinding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(mActivityFoodDetailBinding.getRoot());

        getDataIntent();
        initToolbar();
        setDataFoodDetail();
        initListener();
    }

    private void getDataIntent() {
        // Lấy dữ liệu từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mFood = (Food) bundle.get(Constant.KEY_INTENT_FOOD_OBJECT);
        }
    }

    private void initToolbar() {
        // Cấu hình thanh toolbar
        mActivityFoodDetailBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);
        mActivityFoodDetailBinding.toolbar.tvTitle.setText(getString(R.string.food_detail_title));

        // Bắt sự kiện khi nút quay lại được nhấn
        mActivityFoodDetailBinding.toolbar.imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void setDataFoodDetail() {
        // Hiển thị thông tin chi tiết của món ăn
        if (mFood == null) {
            return;
        }

        // Hiển thị hình ảnh banner của món ăn
        GlideUtils.loadUrlBanner(mFood.getBanner(), mActivityFoodDetailBinding.imageFood);

        // Hiển thị giá và thông tin giảm giá nếu có
        if (mFood.getSale() <= 0) {
            mActivityFoodDetailBinding.tvSaleOff.setVisibility(View.GONE);
            mActivityFoodDetailBinding.tvPrice.setVisibility(View.GONE);

            String strPrice = mFood.getPrice() + Constant.CURRENCY;
            mActivityFoodDetailBinding.tvPriceSale.setText(strPrice);
        } else {
            mActivityFoodDetailBinding.tvSaleOff.setVisibility(View.VISIBLE);
            mActivityFoodDetailBinding.tvPrice.setVisibility(View.VISIBLE);

            String strSale = "Giảm " + mFood.getSale() + "%";
            mActivityFoodDetailBinding.tvSaleOff.setText(strSale);

            String strPriceOld = mFood.getPrice() + Constant.CURRENCY;
            mActivityFoodDetailBinding.tvPrice.setText(strPriceOld);
            mActivityFoodDetailBinding.tvPrice.setPaintFlags(mActivityFoodDetailBinding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String strRealPrice = mFood.getRealPrice() + Constant.CURRENCY;
            mActivityFoodDetailBinding.tvPriceSale.setText(strRealPrice);
        }

        // Hiển thị tên và mô tả món ăn
        mActivityFoodDetailBinding.tvFoodName.setText(mFood.getName());
        mActivityFoodDetailBinding.tvFoodDescription.setText(mFood.getDescription());

        // Hiển thị danh sách hình ảnh thêm của món ăn
        displayListMoreImages();

        // Cập nhật trạng thái nút "Thêm vào giỏ hàng"
        setStatusButtonAddToCart();
    }

    private void displayListMoreImages() {
        // Hiển thị danh sách hình ảnh thêm của món ăn
        if (mFood.getImages() == null || mFood.getImages().isEmpty()) {
            mActivityFoodDetailBinding.tvMoreImageLabel.setVisibility(View.GONE);
            return;
        }
        mActivityFoodDetailBinding.tvMoreImageLabel.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mActivityFoodDetailBinding.rcvImages.setLayoutManager(gridLayoutManager);

        MoreImageAdapter moreImageAdapter = new MoreImageAdapter(mFood.getImages());
        mActivityFoodDetailBinding.rcvImages.setAdapter(moreImageAdapter);
    }

    private void setStatusButtonAddToCart() {
        // Cập nhật trạng thái nút "Thêm vào giỏ hàng"
        if (isFoodInCart()) {
            mActivityFoodDetailBinding.tvAddToCart.setBackgroundResource(R.drawable.bg_gray_shape_corner_6);
            mActivityFoodDetailBinding.tvAddToCart.setText(getString(R.string.added_to_cart));
            mActivityFoodDetailBinding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
            mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.GONE);
        } else {
            mActivityFoodDetailBinding.tvAddToCart.setBackgroundResource(R.drawable.bg_green_shape_corner_6);
            mActivityFoodDetailBinding.tvAddToCart.setText(getString(R.string.add_to_cart));
            mActivityFoodDetailBinding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.white));
            mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFoodInCart() {
        // Kiểm tra xem món ăn đã có trong giỏ hàng chưa
        List<Food> list = FoodDatabase.getInstance(this).foodDAO().checkFoodInCart(mFood.getId());
        return list != null && !list.isEmpty();
    }

    private void initListener() {
        // Bắt sự kiện khi nút "Thêm vào giỏ hàng" được nhấn
        mActivityFoodDetailBinding.tvAddToCart.setOnClickListener(v -> onClickAddToCart());

        // Bắt sự kiện khi nút giỏ hàng được nhấn
        mActivityFoodDetailBinding.toolbar.imgCart.setOnClickListener(v -> onClickAddToCart());
    }

    public void onClickAddToCart() {
        // Xử lý khi nút "Thêm vào giỏ hàng" được nhấn
        if (isFoodInCart()) {
            // Nếu món ăn đã có trong giỏ hàng, không làm gì cả
            return;
        }

        // Hiển thị bottom sheet để thêm món ăn vào giỏ hàng
        @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_cart, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        // Ánh xạ các thành phần trong bottom sheet
        ImageView imgFoodCart = viewDialog.findViewById(R.id.img_food_cart);
        TextView tvFoodNameCart = viewDialog.findViewById(R.id.tv_food_name_cart);
        TextView tvFoodPriceCart = viewDialog.findViewById(R.id.tv_food_price_cart);
        TextView tvSubtractCount = viewDialog.findViewById(R.id.tv_subtract);
        TextView tvCount = viewDialog.findViewById(R.id.tv_count);
        TextView tvAddCount = viewDialog.findViewById(R.id.tv_add);
        TextView tvCancel = viewDialog.findViewById(R.id.tv_cancel);
        TextView tvAddCart = viewDialog.findViewById(R.id.tv_add_cart);

        // Hiển thị hình ảnh và thông tin của món ăn trong bottom sheet
        GlideUtils.loadUrl(mFood.getImage(), imgFoodCart);
        tvFoodNameCart.setText(mFood.getName());

        int totalPrice = mFood.getRealPrice();
        String strTotalPrice = totalPrice + Constant.CURRENCY;
        tvFoodPriceCart.setText(strTotalPrice);

        mFood.setCount(1);
        mFood.setTotalPrice(totalPrice);

        // Bắt sự kiện cho các nút trong bottom sheet
        tvSubtractCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvCount.getText().toString());
            if (count <= 1) {
                return;
            }
            int newCount = Integer.parseInt(tvCount.getText().toString()) - 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice1 = mFood.getRealPrice() * newCount;
            String strTotalPrice1 = totalPrice1 + Constant.CURRENCY;
            tvFoodPriceCart.setText(strTotalPrice1);

            mFood.setCount(newCount);
            mFood.setTotalPrice(totalPrice1);
        });

        tvAddCount.setOnClickListener(v -> {
            int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice2 = mFood.getRealPrice() * newCount;
            String strTotalPrice2 = totalPrice2 + Constant.CURRENCY;
            tvFoodPriceCart.setText(strTotalPrice2);

            mFood.setCount(newCount);
            mFood.setTotalPrice(totalPrice2);
        });

        tvCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvAddCart.setOnClickListener(v -> {
            // Thêm món ăn vào giỏ hàng
            FoodDatabase.getInstance(FoodDetailActivity.this).foodDAO().insertFood(mFood);
            bottomSheetDialog.dismiss();
            setStatusButtonAddToCart();
            EventBus.getDefault().post(new ReloadListCartEvent());
        });

        // Hiển thị bottom sheet
        bottomSheetDialog.show();
    }
}
