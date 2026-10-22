package praktikum.model;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginModel {
   private String email;
   private String password;

public UserLoginModel (String email, String password){
    this.email = email;
    this.password = password;
}
}
