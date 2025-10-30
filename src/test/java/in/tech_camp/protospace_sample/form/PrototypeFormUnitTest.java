package in.tech_camp.protospace_sample.form;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import in.tech_camp.protospace_sample.factory.PrototypeFormFactory;
import in.tech_camp.protospace_sample.validation.ValidationPriority1;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ActiveProfiles("test")
public class PrototypeFormUnitTest {
  private Validator validator;
  private PrototypeForm prototypeForm;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    prototypeForm = PrototypeFormFactory.createPrototype();
  }

  @Nested
  class プロトタイプを投稿できる場合 {
    @Test
    public void titleとcatchCopyとconceptとimageFileが存在していれば投稿できる () {
      Set<ConstraintViolation<PrototypeForm>> violations = validator.validate(prototypeForm, ValidationPriority1.class);
      assertEquals(0, violations.size());
        }
  }

  @Nested
  class プロトタイプを投稿できない場合 {
    @Test
    public void titleが空の場合バリデーションエラーが発生する () {
      prototypeForm.setTitle("");
      Set<ConstraintViolation<PrototypeForm>> violations = validator.validate(prototypeForm, ValidationPriority1.class);
      assertEquals(1, violations.size());
      assertEquals("Title can't be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void catchCopyが空の場合バリデーションエラーが発生する () {
      prototypeForm.setCatchCopy("");
      Set<ConstraintViolation<PrototypeForm>> violations = validator.validate(prototypeForm, ValidationPriority1.class);
      assertEquals(1, violations.size());
      assertEquals("CatchCopy can't be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void conceptが空の場合バリデーションエラーが発生する () {
      prototypeForm.setConcept("");
      Set<ConstraintViolation<PrototypeForm>> violations = validator.validate(prototypeForm, ValidationPriority1.class);
      assertEquals(1, violations.size());
      assertEquals("Concept can't be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void imageFileが空の場合バリデーションエラーが発生する () {
      prototypeForm.setImageFile(null);
      Set<ConstraintViolation<PrototypeForm>> violations = validator.validate(prototypeForm, ValidationPriority1.class);
      assertEquals(1, violations.size());
      assertEquals("ImageFile can't be blank", violations.iterator().next().getMessage());
    }
  }
}
