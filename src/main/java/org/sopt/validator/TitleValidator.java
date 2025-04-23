package org.sopt.validator;

import static org.sopt.util.CodepointUtil.*;

public class TitleValidator {

    public static boolean isBlank(String title){
        return title.isBlank();
    }

    public static int lengthWithEmoji(String s){
        int count = 0;
        int[] codePoints = s.codePoints().toArray();

        boolean zwjFlag = false;
        boolean regionalFlag = false;

        for (int codePoint : codePoints) {
            if (isSkinToneModifier(codePoint)) continue;

            if(isVS16(codePoint)) continue;

            if (isZwj(codePoint)) {
                zwjFlag = true;
                continue;
            }

            if (isRegional(codePoint)) {
                if (!regionalFlag) {
                    regionalFlag = true;
                    count++;
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

}
