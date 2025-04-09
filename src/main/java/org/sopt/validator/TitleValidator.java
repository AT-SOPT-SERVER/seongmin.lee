package org.sopt.validator;

public class TitleValidator {
    public static boolean isBlank(String title){
        return title.isBlank();
    }

    public static boolean isExceedingCharLimit(String title, int limit){
        return title.codePointCount(0, title.length()) > limit;
    }
}
