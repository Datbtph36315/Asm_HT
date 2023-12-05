package com.DvsQ.foodorder.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.DvsQ.foodorder.ControllerApplication;
import com.DvsQ.foodorder.R;
import com.DvsQ.foodorder.activity.FoodDetailActivity;
import com.DvsQ.foodorder.activity.MainActivity;
import com.DvsQ.foodorder.adapter.FoodGridAdapter;
import com.DvsQ.foodorder.adapter.FoodPopularAdapter;
import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.constant.GlobalFunction;
import com.DvsQ.foodorder.databinding.FragmentHomeBinding;
import com.DvsQ.foodorder.model.Food;
import com.DvsQ.foodorder.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding mFragmentHomeBinding;

    private List<Food> mListFood;
    private List<Food> mListFoodPopular;

    private final Handler mHandlerBanner = new Handler();
    private final Runnable mRunnableBanner = new Runnable() {
        @Override
        public void run() {
            // Xử lý tự động chuyển trang banner sau một khoảng thời gian
            if (mListFoodPopular == null || mListFoodPopular.isEmpty()) {
                return;
            }
            if (mFragmentHomeBinding.viewpager2.getCurrentItem() == mListFoodPopular.size() - 1) {
                mFragmentHomeBinding.viewpager2.setCurrentItem(0);
                return;
            }
            mFragmentHomeBinding.viewpager2.setCurrentItem(mFragmentHomeBinding.viewpager2.getCurrentItem() + 1);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        // Lấy danh sách thực phẩm từ Firebase
        getListFoodFromFirebase("");
        // Khởi tạo các sự kiện lắng nghe
        initListener();

        return mFragmentHomeBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            // Thiết lập thanh toolbar với tiêu đề "Home"
            ((MainActivity) getActivity()).setToolBar(true, getString(R.string.home));
        }
    }

    private void initListener() {
        // Lắng nghe sự kiện thay đổi nội dung của ô tìm kiếm
        mFragmentHomeBinding.edtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Xử lý sự kiện sau khi người dùng thay đổi nội dung ô tìm kiếm
                String strKey = s.toString().trim();
                if (strKey.equals("") || strKey.length() == 0) {
                    // Nếu ô tìm kiếm trống, hiển thị toàn bộ danh sách thực phẩm
                    if (mListFood != null) mListFood.clear();
                    getListFoodFromFirebase("");
                }
            }
        });

        // Lắng nghe sự kiện click nút tìm kiếm
        mFragmentHomeBinding.imgSearch.setOnClickListener(view -> searchFood());

        // Lắng nghe sự kiện khi nhấn phím Enter trên bàn phím
        mFragmentHomeBinding.edtSearchName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchFood();
                return true;
            }
            return false;
        });
    }

    private void displayListFoodPopular() {
        // Hiển thị danh sách thực phẩm phổ biến trong ViewPager2
        FoodPopularAdapter mFoodPopularAdapter = new FoodPopularAdapter(getListFoodPopular(), this::goToFoodDetail);
        mFragmentHomeBinding.viewpager2.setAdapter(mFoodPopularAdapter);
        mFragmentHomeBinding.indicator3.setViewPager(mFragmentHomeBinding.viewpager2);

        mFragmentHomeBinding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Xử lý sự kiện khi chuyển trang trong ViewPager2
                mHandlerBanner.removeCallbacks(mRunnableBanner);
                mHandlerBanner.postDelayed(mRunnableBanner, 3000);
            }
        });
    }

    private void displayListFoodSuggest() {
        // Hiển thị danh sách thực phẩm đề xuất trong RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFragmentHomeBinding.rcvFood.setLayoutManager(gridLayoutManager);

        FoodGridAdapter mFoodGridAdapter = new FoodGridAdapter(mListFood, this::goToFoodDetail);
        mFragmentHomeBinding.rcvFood.setAdapter(mFoodGridAdapter);
    }

    private List<Food> getListFoodPopular() {
        // Lấy danh sách thực phẩm phổ biến từ danh sách thực phẩm chính
        mListFoodPopular = new ArrayList<>();
        if (mListFood == null || mListFood.isEmpty()) {
            return mListFoodPopular;
        }
        for (Food food : mListFood) {
            if (food.isPopular()) {
                mListFoodPopular.add(food);
            }
        }
        return mListFoodPopular;
    }

    private void getListFoodFromFirebase(String key) {
        // Lấy danh sách thực phẩm từ Firebase theo từ khóa tìm kiếm
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity()).getFoodDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mFragmentHomeBinding.layoutContent.setVisibility(View.VISIBLE);
                mListFood = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    if (food == null) {
                        return;
                    }

                    if (StringUtil.isEmpty(key)) {
                        mListFood.add(0, food);
                    } else {
                        if (GlobalFunction.getTextSearch(food.getName()).toLowerCase().trim()
                                .contains(GlobalFunction.getTextSearch(key).toLowerCase().trim())) {
                            mListFood.add(0, food);
                        }
                    }
                }
                // Hiển thị danh sách thực phẩm phổ biến và đề xuất
                displayListFoodPopular();
                displayListFoodSuggest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý sự kiện khi có lỗi xảy ra khi lấy dữ liệu từ Firebase
                GlobalFunction.showToastMessage(getActivity(), getString(R.string.msg_get_date_error));
            }
        });
    }

    private void searchFood() {
        // Tìm kiếm thực phẩm dựa trên từ khóa nhập vào
        String strKey = mFragmentHomeBinding.edtSearchName.getText().toString().trim();
        if (mListFood != null) mListFood.clear();
        getListFoodFromFirebase(strKey);
        // Ẩn bàn phím sau khi tìm kiếm
        GlobalFunction.hideSoftKeyboard(getActivity());
    }

    private void goToFoodDetail(@NonNull Food food) {
        // Chuyển đến màn hình chi tiết thực phẩm khi người dùng nhấn vào một thực phẩm
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_INTENT_FOOD_OBJECT, food);
        GlobalFunction.startActivity(getActivity(), FoodDetailActivity.class, bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Dừng sự kiện tự động chuyển trang banner khi Fragment bị tạm dừng
        mHandlerBanner.removeCallbacks(mRunnableBanner);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khởi động sự kiện tự động chuyển trang banner khi Fragment được resume
        mHandlerBanner.postDelayed(mRunnableBanner, 3000);
    }
}
