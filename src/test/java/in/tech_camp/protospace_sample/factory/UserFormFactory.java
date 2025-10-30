package in.tech_camp.protospace_sample.factory;

import com.github.javafaker.Faker;

import in.tech_camp.protospace_sample.form.UserForm;

public class UserFormFactory {
  private static final Faker faker = new Faker();
  
  public static UserForm createUser() {
    UserForm userForm = new UserForm();

    userForm.setEmail(faker.internet().emailAddress());
    userForm.setName(faker.name().username());
    userForm.setEncryptedPassword(faker.internet().password(6, 12));
    userForm.setPasswordConfirmation(userForm.getEncryptedPassword());
    userForm.setProfile(faker.lorem().sentence());
    userForm.setOccupation(faker.lorem().sentence());
    userForm.setPosition(faker.lorem().sentence());

    return userForm;
  }
}