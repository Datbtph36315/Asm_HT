package com.DvsQ.foodorder.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.DvsQ.foodorder.model.Food;

import java.util.List;

// Đánh dấu lớp là một DAO (Data Access Object)
@Dao
public interface FoodDAO {

    // Phương thức để chèn một đối tượng Food vào cơ sở dữ liệu
    @Insert
    void insertFood(Food food);

    // Phương thức để lấy danh sách các món ăn trong giỏ hàng
    @Query("SELECT * FROM food")
    List<Food> getListFoodCart();

    // Phương thức để kiểm tra xem một món ăn có trong giỏ hàng không
    @Query("SELECT * FROM food WHERE id=:id")
    List<Food> checkFoodInCart(long id);

    // Phương thức để xóa một món ăn khỏi giỏ hàng
    @Delete
    void deleteFood(Food food);

    // Phương thức để cập nhật thông tin của một món ăn trong giỏ hàng
    @Update
    void updateFood(Food food);

    // Phương thức để xóa tất cả món ăn trong giỏ hàng
    @Query("DELETE from food")
    void deleteAllFood();
}
