package in.tech_camp.protospace_sample.form;

import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace_sample.validation.ValidImage;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrototypeForm {
  
  @NotBlank(message = "Title can't be blank")
  private String title;

  @NotBlank(message = "CatchCopy can't be blank")
  private String catchCopy;

  @NotBlank(message = "Concept can't be blank")
  private String concept;

  private Integer userId;
  private String imageName;
  private String imageType;
  private byte[] imageData;

  @ValidImage(message = "ImageFile can't be blank")
  private MultipartFile imageFile;
}
