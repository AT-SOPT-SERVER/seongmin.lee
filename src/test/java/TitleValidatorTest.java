import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sopt.validator.TitleValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class TitleValidatorTest {

    @Test
    void test(){
        String s = "🤍🧒🏿😊👨‍👩🏿‍👧‍👦🐘👨‍👩‍👧‍👦❗👨‍🏭";
        Assertions.assertThat(TitleValidator.lengthWithEmoji(s)).isEqualTo(8);
    }
}
