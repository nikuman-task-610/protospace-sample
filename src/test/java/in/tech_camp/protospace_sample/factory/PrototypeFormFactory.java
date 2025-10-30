package in.tech_camp.protospace_sample.factory;

import org.springframework.mock.web.MockMultipartFile;

import com.github.javafaker.Faker;

import in.tech_camp.protospace_sample.form.PrototypeForm;

public class PrototypeFormFactory {
  private static final Faker faker = new Faker();

  public static PrototypeForm createPrototype() {
    PrototypeForm prototypeForm = new PrototypeForm();
    prototypeForm.setTitle(faker.lorem().sentence(10));
    prototypeForm.setCatchCopy(faker.lorem().sentence());
    prototypeForm.setConcept(faker.lorem().sentence());

    MockMultipartFile imageFile = new MockMultipartFile(
      "imageFile",
      "test-image.jpg",
      "image/jpeg",
      "dummy image content".getBytes()
      );
      prototypeForm.setImageFile(imageFile);
    return prototypeForm;
  }
}
