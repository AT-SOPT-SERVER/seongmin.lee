package org.sopt.validator;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleValidator {

    private static final Pattern graphemePattern = Pattern.compile("\\X");
    private static final Matcher graphemeMatcher = graphemePattern.matcher("");

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
        return count > limit;
    }
}
