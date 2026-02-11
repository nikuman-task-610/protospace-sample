package in.tech_camp.protospace_sample.form;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import in.tech_camp.protospace_sample.factory.CommentFormFactory;
import jakarta.validation.ConstraintViolation;
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
    commentForm = CommentFormFactory.createComment();
  }

  @Nested
  class コメント投稿ができる場合 {
    @Test
    public void contentが存在すれば投稿できる() {
      Set<ConstraintViolation<CommentForm>> violations = validator.validate(commentForm);
      assertEquals(0, violations.size());
    }
  }

  @Nested
  class コメント投稿ができない場合 {
    @Test
    public void contentが空ではバリデーションエラーが発生する() {
      commentForm.setContent("");
      Set<ConstraintViolation<CommentForm>> violations = validator.validate(commentForm);
      assertEquals(1, violations.size());
      assertEquals("Content can't be blank", violations.iterator().next().getMessage());
    }
  }
}
