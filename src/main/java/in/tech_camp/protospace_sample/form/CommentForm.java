package in.tech_camp.protospace_sample.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentForm {
  @NotBlank
  private String content;
}
