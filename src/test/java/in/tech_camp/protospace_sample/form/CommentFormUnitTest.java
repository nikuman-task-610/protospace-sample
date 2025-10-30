package in.tech_camp.protospace_sample.form;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ActiveProfiles("test")
public class CommentFormUnitTest {
  private Validator validator;
  private CommentForm commentForm;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }
}
