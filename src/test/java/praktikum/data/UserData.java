package praktikum.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserData {

    private static final Faker userRus = new Faker(new Locale("ru"));
    private static final Faker userEng = new Faker(Locale.ENGLISH);

    public static String getGeneratedEmail(){
        return generateEmail();
    }

    public static String getGeneratedName(){
        return generateName();
    }

    public static String getGeneratedValidPassword(){
        return generateValidPassword();
    }

    public static String getGeneratedShortPassword(){
        return generateShortPassword();
    }

    private static String generateEmail() {
        String email = userEng.internet().emailAddress();
        String uniqueSuffix = userEng.regexify("[0-9]{6}");

        return email.replace("@", "-" + uniqueSuffix + "@");
    }

    private static String generateName() {
        return userRus.name().firstName();
    }

    private static String generateValidPassword() {
        return userEng.internet().password(6, 10);
    }

    private static String generateShortPassword() {
        return userEng.internet().password(2, 5);
    }
}
