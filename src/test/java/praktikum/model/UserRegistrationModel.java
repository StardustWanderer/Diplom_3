package praktikum.model;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationModel {
    private String name;
    private String email;
    private String password;

    public UserRegistrationModel (String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
