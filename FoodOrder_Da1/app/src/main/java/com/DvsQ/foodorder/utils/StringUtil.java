package com.DvsQ.foodorder.utils;

public class StringUtil {

    // Phương thức kiểm tra tính hợp lệ của một địa chỉ email
    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    // Phương thức kiểm tra xem một chuỗi có là một trường hợp tốt không (không rỗng và độ dài lớn hơn hoặc bằng 6 ký tự)
    public static boolean isGoodField(String input) {
        return input != null && !input.isEmpty() && input.length() >= 6;
    }

    // Phương thức kiểm tra xem một chuỗi có rỗng hay không
    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty() || ("").equals(input.trim());
    }

    // Phương thức chuyển đổi số thành chuỗi có 2 chữ số (nếu số < 10, thêm số 0 vào trước)
    public static String getDoubleNumber(int number) {
        if (number < 10) {
            return "0" + number;
        } else return "" + number;
    }
}
