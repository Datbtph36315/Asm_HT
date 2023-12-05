package com.DvsQ.foodorder;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.DvsQ.foodorder.constant.Constant;
import com.DvsQ.foodorder.prefs.DataStoreManager;

public class ControllerApplication extends Application {

    // Biến toàn cục để thao tác với Firebase Database
    private FirebaseDatabase mFirebaseDatabase;

    // Phương thức static để lấy đối tượng ControllerApplication từ Context
    public static ControllerApplication get(Context context) {
        return (ControllerApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi tạo Firebase trong ứng dụng
        FirebaseApp.initializeApp(this);

        // Khởi tạo đối tượng FirebaseDatabase với URL của Firebase Database
        mFirebaseDatabase = FirebaseDatabase.getInstance(Constant.FIREBASE_URL);

        // Khởi tạo DataStoreManager để quản lý SharedPreferences
        DataStoreManager.init(getApplicationContext());
    }

    // Phương thức để lấy DatabaseReference cho dữ liệu Food từ Firebase Database
    public DatabaseReference getFoodDatabaseReference() {
        return mFirebaseDatabase.getReference("/food");
    }

    // Phương thức để lấy DatabaseReference cho dữ liệu Feedback từ Firebase Database
    public DatabaseReference getFeedbackDatabaseReference() {
        return mFirebaseDatabase.getReference("/feedback");
    }

    // Phương thức để lấy DatabaseReference cho dữ liệu Booking từ Firebase Database
    public DatabaseReference getBookingDatabaseReference() {
        return mFirebaseDatabase.getReference("/booking");
    }
}
