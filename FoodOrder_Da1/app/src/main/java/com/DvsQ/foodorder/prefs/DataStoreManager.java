package com.DvsQ.foodorder.prefs;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.DvsQ.foodorder.model.User;
import com.DvsQ.foodorder.utils.StringUtil;

public class DataStoreManager {

    // Key để lưu trữ thông tin người dùng trong SharedPreferences
    public static final String PREF_USER_INFOR = "PREF_USER_INFOR";

    // Đối tượng quản lý SharedPreferences
    private static DataStoreManager instance;
    private MySharedPreferences sharedPreferences;

    // Khởi tạo DataStoreManager và MySharedPreferences
    public static void init(Context context) {
        instance = new DataStoreManager();
        instance.sharedPreferences = new MySharedPreferences(context);
    }

    // Lấy đối tượng quản lý dữ liệu
    public static DataStoreManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    // Lưu thông tin người dùng vào SharedPreferences
    public static void setUser(@Nullable User user) {
        String jsonUser = "";
        if (user != null) {
            // Chuyển đối tượng User thành chuỗi JSON
            jsonUser = user.toJSon();
        }
        // Lưu chuỗi JSON vào SharedPreferences
        DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_USER_INFOR, jsonUser);
    }

    // Lấy thông tin người dùng từ SharedPreferences
    public static User getUser() {
        String jsonUser = DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_USER_INFOR);
        if (!StringUtil.isEmpty(jsonUser)) {
            // Chuyển chuỗi JSON thành đối tượng User
            return new Gson().fromJson(jsonUser, User.class);
        }
        return new User();
    }
}
