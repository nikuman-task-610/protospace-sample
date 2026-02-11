package in.tech_camp.protospace_sample.entity;

import lombok.Data;
import lombok.ToString;

@Data
public class CommentEntity {
  private Integer id;
  private String content;
  @ToString.Exclude
  private UserEntity user;
  @ToString.Exclude
  private PrototypeEntity prototype;
}
