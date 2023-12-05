package com.DvsQ.foodorder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.DvsQ.foodorder.model.Food;

// Đánh dấu lớp là một Database với các entities và version tương ứng
@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "food.db";

    private static FoodDatabase instance;

    // Phương thức để lấy đối tượng FoodDatabase, sử dụng Singleton Pattern
    public static synchronized FoodDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FoodDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() // Cho phép thao tác trên main thread (chỉ sử dụng trong quá trình phát triển, không nên sử dụng trong sản phẩm thực tế)
                    .build();
        }
        return instance;
    }

    // Phương thức trừu tượng cung cấp đối tượng DAO cho bảng Food
    public abstract FoodDAO foodDAO();
}
