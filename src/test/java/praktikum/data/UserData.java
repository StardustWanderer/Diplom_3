package praktikum.data;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.UUID;

public class UserData {

    private final Faker faker = new Faker(Locale.ENGLISH);

    public String generateEmail() {
        String email = faker.internet().emailAddress();
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        return email.replace("@", "-" + uuid + "@");
    }

    public String generateName() {
        return faker.name().firstName();
    }

    public String generateValidPassword() {
        return faker.internet().password(6, 10);
    }

    public String generateInvalidPassword() {
        return faker.internet().password(2, 5);
    }

}
