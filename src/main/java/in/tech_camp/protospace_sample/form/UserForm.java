package in.tech_camp.protospace_sample.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
  @NotBlank(message = "Name can't be blank")
  private String name;
  
  @NotBlank(message = "Email can't be blank")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "EncryptedPassword can't be blank")
  @Length(min = 6, message = "EncryptedPassword should be at least 6 characters")
  private String encryptedPassword;

  @NotBlank(message = "Profile can't be blank")
  private String profile;

  @NotBlank(message = "Occupation can't be blank")
  private String occupation;

  @NotBlank(message = "Position can't be blank")
  private String position;
 
  private String passwordConfirmation;

  public void validatePasswordConfirmation(BindingResult result) {
      if (encryptedPassword != null && !encryptedPassword.isBlank() 
        && !encryptedPassword.equals(passwordConfirmation)) {
        result.rejectValue("passwordConfirmation", "error.user", "Password confirmation doesn't match Password");
      }
  }
}
