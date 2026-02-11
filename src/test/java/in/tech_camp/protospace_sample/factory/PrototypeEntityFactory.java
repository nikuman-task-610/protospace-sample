package in.tech_camp.protospace_sample.factory;

import java.io.IOException;

import in.tech_camp.protospace_sample.entity.PrototypeEntity;
import in.tech_camp.protospace_sample.entity.UserEntity;
import in.tech_camp.protospace_sample.form.PrototypeForm;

public class PrototypeEntityFactory {
  public static PrototypeEntity createPrototype(UserEntity userEntity) {
    PrototypeForm prototypeForm = PrototypeFormFactory.createPrototype();

    PrototypeEntity prototype = new PrototypeEntity();
    prototype.setTitle(prototypeForm.getTitle());
    prototype.setCatchCopy(prototypeForm.getCatchCopy());
    prototype.setConcept(prototypeForm.getConcept());

    try {
      prototype.setImageData(prototypeForm.getImageFile().getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to read image data", e);
    }
    
    prototype.setImageName(prototypeForm.getImageFile().getOriginalFilename());
    prototype.setImageType(prototypeForm.getImageFile().getContentType());
    prototype.setUser(userEntity);

    return prototype;
    
  }
}