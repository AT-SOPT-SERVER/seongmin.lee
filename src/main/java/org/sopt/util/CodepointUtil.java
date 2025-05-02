package org.sopt.util;

public class CodepointUtil {

    public static boolean isSkinToneModifier(int codePoint) {
        return codePoint >= 0x1F3FB && codePoint <= 0x1F3FF;
    }

    public static boolean isPresentationSelector(int codePoint){
        return codePoint == 0xFE0F || codePoint == 0xFE0E;
    }
    public static boolean isKeyCap(int codePoint){
        return codePoint == 0x20E3;
    }

    public static boolean isZwj(int codePoint){
        return codePoint == 0x200D;
    }

    public static boolean isRegional(int codePoint){
        return codePoint >= 0x1F1E6 && codePoint <= 0x1F1FF;
    }

    public static boolean isIgnored(int codePoint){
        return isPresentationSelector(codePoint) || isSkinToneModifier(codePoint) || isKeyCap(codePoint);
    }

    public static int graphemeClusterLength(String s){
        int count = 0;
        int[] codePoints = s.codePoints().toArray();

        boolean zwjFlag = false;
        boolean regionalFlag = false;

        for (int codePoint : codePoints) {
            if (isIgnored(codePoint)) continue;
            if (isZwj(codePoint)) {
                zwjFlag = true;
                continue;
            }
            if (isRegional(codePoint)) {
                if (!regionalFlag) {
                    regionalFlag = true;
                    count++;
                }else{
                    regionalFlag = false;
                }
                continue;
            }
            regionalFlag = false;
            if (zwjFlag) {
                zwjFlag = false;
                continue;
            }
            count++;
        }
        return count;
    }

    public static boolean graphemeClusterLengthBreakByLimit(String s, int limit){
        int count = 0;
        int[] codePoints = s.codePoints().toArray();

        boolean zwjFlag = false;
        boolean regionalFlag = false;

        for (int codePoint : codePoints) {
            if(count > limit) break;
            if (isIgnored(codePoint)) continue;
            if (isZwj(codePoint)) {
                zwjFlag = true;
                continue;
            }
            if (isRegional(codePoint)) {
                if (!regionalFlag) {
                    regionalFlag = true;
                    count++;
                }else{
                    regionalFlag = false;
                }
                continue;
            }
            regionalFlag = false;
            if (zwjFlag) {
                zwjFlag = false;
                continue;
            }
            count++;
        }
        return count > limit;
    }
}
