package org.sopt.util;

public class CodepointUtil {

    public static boolean isSkinToneModifier(int codePoint) {
        return codePoint >= 0x1F3FB && codePoint <= 0x1F3FF;
    }

    public static boolean isVS16(int codePoint){
        return codePoint == 0xFE0F;
    }

    public static boolean isZwj(int codePoint){
        return codePoint == 0x200D;
    }

    public static boolean isRegional(int codePoint){
        return codePoint >= 0x1F1E6 && codePoint <= 0x1F1FF;
    }

}
