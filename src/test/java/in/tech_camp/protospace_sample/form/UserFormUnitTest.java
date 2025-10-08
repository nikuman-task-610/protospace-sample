package in.tech_camp.protospace_sample.form;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ActiveProfiles("test")
public class UserFormUnitTest {
    @Test
    public void nameが空の場合バリデーションエラーが発生する() {
      UserForm userForm = new UserForm();
      userForm.setName(""); 
      userForm.setEmail("test@test.com"); 
      userForm.setEncryptedPassword("techcamp123"); 
      userForm.setPasswordConfirmation("techcamp123");
      userForm.setProfile("profile:test");
      userForm.setOccupation(":oops");
      userForm.setPosition("position:yes");

      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Validator validator = factory.getValidator();
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);

      assertEquals(1, violations.size());
      assertEquals("Name can't be blank", violations.iterator().next().getMessage());
    }
}
