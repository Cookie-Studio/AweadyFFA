package cn.cookiestudio.aweadyffa.utils;

public class PlayerHealthUtil {
    public static String changeToString(double health,double maxHealth){
        String result = "";
        for (int i = 0; i <= health; i++) {
            result = result + "§c█";
        }
        if (health % 1 != 0){
            result = result + "§c▌";
        }
        for (int i = 0; i <= maxHealth - health; i++) {
            result = result + "§7█";
        }
        return result;
    }
}
