package in.tech_camp.protospace_sample.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentForm {
  @NotBlank(message = "Content can't be blank")
  private String content;
}
