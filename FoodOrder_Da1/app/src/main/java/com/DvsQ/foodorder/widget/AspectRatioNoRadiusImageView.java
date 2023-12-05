package com.DvsQ.foodorder.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.DvsQ.foodorder.R;

public class AspectRatioNoRadiusImageView extends AppCompatImageView {

    // Hằng số định nghĩa đo lường chiều rộng và chiều cao
    public static final int MEASUREMENT_WIDTH = 0;
    public static final int MEASUREMENT_HEIGHT = 1;

    // Các giá trị mặc định
    private static final float DEFAULT_ASPECT_RATIO = 1f;
    private static final boolean DEFAULT_ASPECT_RATIO_ENABLED = false;
    private static final int DEFAULT_DOMINANT_MEASUREMENT = MEASUREMENT_WIDTH;

    // Các thuộc tính của AspectRatioNoRadiusImageView
    private float aspectRatio;
    private boolean aspectRatioEnabled;
    private int dominantMeasurement;

    public AspectRatioNoRadiusImageView(Context context) {
        this(context, null);
    }

    public AspectRatioNoRadiusImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioNoRadiusImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadStateFromAttrs(attrs);
    }

    @SuppressLint("CustomViewStyleable")
    private void loadStateFromAttrs(AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray a = null;
        try {
            // Lấy giá trị thuộc tính từ tập AttributeSet
            a = getContext().obtainStyledAttributes(attributeSet, R.styleable.AspectRatioView);
            aspectRatio = a.getFloat(R.styleable.AspectRatioView_aspectRatio, DEFAULT_ASPECT_RATIO);
            aspectRatioEnabled = a.getBoolean(R.styleable.AspectRatioView_aspectRatioEnabled,
                    DEFAULT_ASPECT_RATIO_ENABLED);
            dominantMeasurement = a.getInt(R.styleable.AspectRatioView_dominantMeasurement,
                    DEFAULT_DOMINANT_MEASUREMENT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Nếu không có tỷ lệ khung hình được kích hoạt, không làm gì cả
        if (!aspectRatioEnabled) return;

        int newWidth;
        int newHeight;
        switch (dominantMeasurement) {
            case MEASUREMENT_WIDTH:
                // Tính toán chiều rộng và chiều cao mới dựa trên tỷ lệ khung hình
                newWidth = getMeasuredWidth();
                newHeight = (int) (newWidth * aspectRatio);
                break;

            case MEASUREMENT_HEIGHT:
                // Tính toán chiều rộng và chiều cao mới dựa trên tỷ lệ khung hình
                newHeight = getMeasuredHeight();
                newWidth = (int) (newHeight * aspectRatio);
                break;

            default:
                throw new IllegalStateException("Unknown measurement with ID " + dominantMeasurement);
        }

        // Đặt kích thước mới cho ImageView
        setMeasuredDimension(newWidth, newHeight);
    }

    // Getter và Setter cho các thuộc tính

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        if (aspectRatioEnabled) {
            // Yêu cầu đo lại kích thước nếu tỷ lệ khung hình được kích hoạt
            requestLayout();
        }
    }

    public boolean getAspectRatioEnabled() {
        return aspectRatioEnabled;
    }

    public void setAspectRatioEnabled(boolean aspectRatioEnabled) {
        this.aspectRatioEnabled = aspectRatioEnabled;
        // Yêu cầu đo lại kích thước khi tỷ lệ khung hình được kích hoạt hoặc hủy kích thước tỷ lệ khung hình
        requestLayout();
    }

    public int getDominantMeasurement() {
        return dominantMeasurement;
    }

    public void setDominantMeasurement(int dominantMeasurement) {
        if (dominantMeasurement != MEASUREMENT_HEIGHT && dominantMeasurement != MEASUREMENT_WIDTH) {
            throw new IllegalArgumentException("Invalid measurement type.");
        }
        this.dominantMeasurement = dominantMeasurement;
        // Yêu cầu đo lại kích thước khi loại đo lường thay đổi
        requestLayout();
    }
}
