package in.tech_camp.protospace_sample.form;

import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace_sample.validation.ValidImage;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrototypeForm {
  
  @NotBlank
  private String title;

  @NotBlank
  private String catchCopy;

  @NotBlank
  private String concept;

  private Integer userId;
  private String imageName;
  private String imageType;
  private byte[] imageData;

  @ValidImage
  private MultipartFile imageFile;
}
