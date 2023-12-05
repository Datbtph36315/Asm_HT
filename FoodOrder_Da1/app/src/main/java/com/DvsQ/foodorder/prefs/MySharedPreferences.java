package com.DvsQ.foodorder.prefs;

import android.content.Context;

public class MySharedPreferences {
    // Tên của SharedPreferences
    private static final String FRUITY_DROID_PREFERENCES = "MY_PREFERENCES";
    private Context mContext;

    // Hàm khởi tạo không tham số (không công khai)
    private MySharedPreferences() {
    }

    // Hàm khởi tạo với tham số Context
    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    // Lưu giá trị kiểu long vào SharedPreferences
    public void putLongValue(String key, long n) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, n);
        editor.apply();
    }

    // Lấy giá trị kiểu long từ SharedPreferences
    public long getLongValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getLong(key, 0);
    }

    // Lưu giá trị kiểu int vào SharedPreferences
    public void putIntValue(String key, int n) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, n);
        editor.apply();
    }

    // Lấy giá trị kiểu int từ SharedPreferences
    public int getIntValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getInt(key, 0);
    }

    // Lưu giá trị kiểu String vào SharedPreferences
    public void putStringValue(String key, String s) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    // Lấy giá trị kiểu String từ SharedPreferences
    public String getStringValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getString(key, "");
    }

    // Lấy giá trị kiểu String từ SharedPreferences với giá trị mặc định
    public String getStringValue(String key, String defaultValue) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getString(key, defaultValue);
    }

    // Lưu giá trị kiểu boolean vào SharedPreferences
    public void putBooleanValue(String key, Boolean b) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    // Lấy giá trị kiểu boolean từ SharedPreferences
    public boolean getBooleanValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getBoolean(key, false);
    }

    // Lưu giá trị kiểu float vào SharedPreferences
    public void putFloatValue(String key, float f) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, f);
        editor.apply();
    }

    // Lấy giá trị kiểu float từ SharedPreferences
    public float getFloatValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getFloat(key, 0.0f);
    }
}
