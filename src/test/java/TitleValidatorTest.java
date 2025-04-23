import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sopt.util.CodepointUtil;
import org.sopt.validator.TextValidator;

public class TitleValidatorTest {

    @Test
    void test(){
        String s = "🤍🧒🏿😊👨‍👩🏿‍👧‍👦🐘👨‍👩‍👧‍👦❗👨‍🏭♑♒🅰️*️⃣#️⃣🏳️‍🌈🩷";
        Assertions.assertThat(CodepointUtil.graphemeClusterLength(s)).isEqualTo(15);
    }
}
