import org.junit.jupiter.api.Test;

import static cn.cookiestudio.aweadyffa.utils.PlayerHealthUtil.changeToString;

public class TestPlayerHealthUtil {
    @Test
    public void testPlayerHealthUtil(){
        for (double h = 0;h <=20.0;h = h + 0.5)
            System.out.println(changeToString(h,20.0));
    }
}
