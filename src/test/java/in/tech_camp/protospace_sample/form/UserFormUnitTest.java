package in.tech_camp.protospace_sample.form;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;

import in.tech_camp.protospace_sample.factory.UserFormFactory;
import in.tech_camp.protospace_sample.validation.ValidationOrder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ActiveProfiles("test")
public class UserFormUnitTest {
  private UserForm userForm;
  private Validator validator;
  private BindingResult bindingResult;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    userForm = UserFormFactory.createUser();
    bindingResult = Mockito.mock(BindingResult.class);
  }

    @Nested
    class ユーザー作成ができる場合 {
      @Test
      public void nameとemailとencryptedPasswordとpasswordConfirmationとprofileとoccupationとpositionが存在すれば登録できる() {
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(0, violations.size());
      }

    }

    @Nested
    class ユーザー作成ができない場合 {
  
      @Test
      public void nameが空の場合バリデーションエラーが発生する() {
      userForm.setName("");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("Name can't be blank", violations.iterator().next().getMessage());
      }

      @Test
      public void emailが空の場合バリデーションエラーが発生する() {
      userForm.setEmail("");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("Email can't be blank", violations.iterator().next().getMessage());
      }

      @Test
      public void encryptedPasswordが空の場合バリデーションエラーが発生する() {
      userForm.setEncryptedPassword("");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("EncryptedPassword can't be blank", violations.iterator().next().getMessage());
        }

      @Test
      public void encryptedPasswordとpasswordConfirmationが不一致ではバリデーションエラーが発生する() {
        userForm.setPasswordConfirmation("differentPassword");
        userForm.validatePasswordConfirmation(bindingResult);
        verify(bindingResult).rejectValue("passwordConfirmation", "error.user", "Password confirmation doesn't match Password");
        }

      @Test
     public void emailはアットマークを含まないとバリデーションエラーが発生する() {
      userForm.setEmail("invalidEmail");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("Email should be valid", violations.iterator().next().getMessage());
        }

      @Test
      public void encryptedPasswordが5文字以下ではバリデーションエラーが発生する() {
      String password = "a".repeat(5);
      userForm.setEncryptedPassword(password); 
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("EncryptedPassword should be at least 6 characters", violations.iterator().next().getMessage());
        }

      @Test
      public void profileが空の場合バリデーションエラーが発生する() {
      userForm.setProfile("");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("Profile can't be blank", violations.iterator().next().getMessage());
        }

      @Test
      public void occupationが空の場合バリデーションエラーが発生する() {
      userForm.setOccupation("");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("Occupation can't be blank", violations.iterator().next().getMessage());
        }

      @Test
      public void positionが空の場合バリデーションエラーが発生する() {
      userForm.setPosition("");
      Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm, ValidationOrder.class);
      assertEquals(1, violations.size());
      assertEquals("Position can't be blank", violations.iterator().next().getMessage());
        }
  
   }
}
