package com.DvsQ.foodorder.listener;

import com.DvsQ.foodorder.model.Food;

public interface IOnManagerFoodListener {
    void onClickUpdateFood(Food food);
    void onClickDeleteFood(Food food);
}
