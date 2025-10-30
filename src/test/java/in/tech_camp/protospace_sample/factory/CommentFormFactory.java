package in.tech_camp.protospace_sample.factory;

import com.github.javafaker.Faker;

import in.tech_camp.protospace_sample.form.CommentForm;

public class CommentFormFactory {
  private static final Faker faker = new Faker();

  public static CommentForm createComment() {
    CommentForm commentForm = new CommentForm();
    commentForm.setContent(faker.lorem().sentence());
    return commentForm;
  }
}
