package org.sopt.validator;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleValidator {

    public static boolean isBlank(String title){
        return title.isBlank();
    }

    public static boolean isExceedingTitleLimit(String title, int limit){

        BreakIterator iterator = BreakIterator.getCharacterInstance(Locale.getDefault());
        iterator.setText(title);

        int count = 0;
        int start = iterator.first();

        while(true){
            int end = iterator.next();
            if(end == BreakIterator.DONE) break;
            count++;
        }
//        System.out.println("count = " + count);
        return count > limit;
    }
}
