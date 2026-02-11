package in.tech_camp.protospace_sample.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import in.tech_camp.protospace_sample.validation.ValidationPriority1;
import in.tech_camp.protospace_sample.validation.ValidationPriority2;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
  @NotBlank(message = "Name can't be blank", groups=ValidationPriority1.class)
  private String name;
  
  @NotBlank(message = "Email can't be blank", groups=ValidationPriority1.class)
  @Email(message = "Email should be valid", groups=ValidationPriority2.class)
  private String email;

  @NotBlank(message = "EncryptedPassword can't be blank", groups=ValidationPriority1.class)
  @Length(min = 6, message = "EncryptedPassword should be at least 6 characters", groups=ValidationPriority2.class)
  private String encryptedPassword;

  @NotBlank(message = "Profile can't be blank", groups=ValidationPriority1.class)
  private String profile;

  @NotBlank(message = "Occupation can't be blank", groups=ValidationPriority1.class)
  private String occupation;

  @NotBlank(message = "Position can't be blank", groups=ValidationPriority1.class)
  private String position;
 
  private String passwordConfirmation;

  public void validatePasswordConfirmation(BindingResult result) {
      if (!encryptedPassword.equals(passwordConfirmation)) {
        result.rejectValue("passwordConfirmation", "error.user", "Password confirmation doesn't match Password");
      }
  }
}
