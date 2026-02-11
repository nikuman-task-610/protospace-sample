package in.tech_camp.protospace_sample.factory;

import in.tech_camp.protospace_sample.entity.UserEntity;
import in.tech_camp.protospace_sample.form.UserForm;

public class UserEntityFactory {
  public static UserEntity createUser(String email) {
    UserForm userForm = UserFormFactory.createUser();
    
    UserEntity user = new UserEntity();
    user.setEmail(email);
    user.setName(userForm.getName());
    user.setEncryptedPassword(userForm.getEncryptedPassword()); 
    user.setProfile(userForm.getProfile());
    user.setOccupation(userForm.getOccupation());
    user.setPosition(userForm.getPosition());

    return user;
  }
}
