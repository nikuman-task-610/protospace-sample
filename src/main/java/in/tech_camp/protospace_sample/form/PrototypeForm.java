package in.tech_camp.protospace_sample.form;

import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace_sample.validation.ValidImage;
import in.tech_camp.protospace_sample.validation.ValidationPriority1;
import in.tech_camp.protospace_sample.validation.ValidationPriority2;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrototypeForm {
  
  @NotBlank(message = "Title can't be blank", groups = {ValidationPriority1.class, ValidationPriority2.class})
  private String title;

  @NotBlank(message = "CatchCopy can't be blank", groups = {ValidationPriority1.class, ValidationPriority2.class})
  private String catchCopy;

  @NotBlank(message = "Concept can't be blank", groups = {ValidationPriority1.class, ValidationPriority2.class})
  private String concept;

  private Integer userId;
  private String imageName;
  private String imageType;
  private byte[] imageData;

  @NotNull(message = "ImageFile can't be blank", groups = {ValidationPriority1.class})
  @ValidImage(message = "Invalid image file", groups = {ValidationPriority1.class, ValidationPriority2.class})
  private MultipartFile imageFile;
}
