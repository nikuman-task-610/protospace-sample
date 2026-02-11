package in.tech_camp.protospace_sample.entity;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
public class PrototypeEntity {
  private Integer id;
  private String title;
  private String catchCopy;
  private String concept;
  private String imageName;
  private String imageType;
  private byte[] imageData;
  @ToString.Exclude
  private UserEntity user;
  @ToString.Exclude
  private List<CommentEntity> comments;
}
