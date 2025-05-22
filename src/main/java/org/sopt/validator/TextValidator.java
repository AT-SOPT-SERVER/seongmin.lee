package org.sopt.validator;

import org.sopt.util.CodepointUtil;

import static org.sopt.util.CodepointUtil.*;

public class TextValidator {

    public static boolean isBlank(String title){
        return title.isBlank();
    }

    public static boolean isTextLengthBiggerThanLimit(String s, int limit){

        return s.length() > limit;
    }


}
