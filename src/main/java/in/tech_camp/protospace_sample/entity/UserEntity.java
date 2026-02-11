package in.tech_camp.protospace_sample.entity;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
public class UserEntity {
  private Integer id;
  private String name;
  private String email;
  private String encryptedPassword;
  private String profile;
  private String occupation;
  private String position;
  @ToString.Exclude
  private List<PrototypeEntity> prototypes;
  @ToString.Exclude
  private List<CommentEntity> comments;
}
