package in.tech_camp.protospace_sample.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
  @NotBlank
  private String name;
  
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Length(min = 6)
  private String encryptedPassword;

  @NotBlank
  private String profile;

  @NotBlank
  private String occupation;

  @NotBlank
  private String position;

  private String passwordConfirmation;

  public void validatePasswordConfirmation(BindingResult result) {
      if (!encryptedPassword.equals(passwordConfirmation)) {
        result.rejectValue("passwordConfirmation", null);
      }
  }
}
