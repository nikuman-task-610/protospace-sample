package in.tech_camp.protospace_sample.system;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.protospace_sample.ProtospaceSampleApplication;
import in.tech_camp.protospace_sample.entity.CommentEntity;
import in.tech_camp.protospace_sample.entity.PrototypeEntity;
import in.tech_camp.protospace_sample.entity.UserEntity;
import in.tech_camp.protospace_sample.factory.CommentFormFactory;
import in.tech_camp.protospace_sample.factory.PrototypeEntityFactory;
import in.tech_camp.protospace_sample.factory.UserEntityFactory;
import in.tech_camp.protospace_sample.form.CommentForm;
import in.tech_camp.protospace_sample.repository.CommentRepository;
import in.tech_camp.protospace_sample.repository.PrototypeRepository;
import in.tech_camp.protospace_sample.repository.UserRepository;
import in.tech_camp.protospace_sample.service.UserService;

@ActiveProfiles("test")
@SpringBootTest(classes = ProtospaceSampleApplication.class)
@Transactional
@AutoConfigureMockMvc
public class CommentPostIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private PrototypeRepository prototypeRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  private UserEntity userEntity;
  private PrototypeEntity prototypeEntity;


  private static final String TEST_USER_EMAIL = "test-user@example.com";

  @BeforeEach
  public void setup() {
    userEntity = UserEntityFactory.createUser(TEST_USER_EMAIL);
    userService.createUserWithEncryptedPassword(userEntity);

    prototypeEntity = PrototypeEntityFactory.createPrototype(userEntity);
    prototypeRepository.insert(prototypeEntity);
  }

  @Nested
  class コメント投稿ができる時 {

    @Test
    @WithUserDetails(value = TEST_USER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void ログインしているユーザーがコメントを投稿するとuser_idとprototype_idが正しく設定される() throws Exception {
      CommentForm commentForm = CommentFormFactory.createComment();
      int beforeCount = commentRepository.findByPrototypeId(prototypeEntity.getId()).size();

      mockMvc.perform(post("/prototypes/" + prototypeEntity.getId() + "/comment")
          .param("content", commentForm.getContent())
          .with(csrf()))
          .andExpect(status().is3xxRedirection())
          .andExpect(redirectedUrl("/prototypes/" + prototypeEntity.getId()));
      
      int afterCount = commentRepository.findByPrototypeId(prototypeEntity.getId()).size();
      assertEquals(beforeCount + 1, afterCount);
    }
  }

  @Nested
  class コメント投稿ができない場合 {
    
    @Test
    public void ログインしていないユーザーはコメントを投稿できずログインページにリダイレクトされる() throws Exception {
      CommentForm commentForm = CommentFormFactory.createComment();
      int beforeCount = commentRepository.findByPrototypeId(prototypeEntity.getId()).size();
      
      mockMvc.perform(post("/prototypes/" + prototypeEntity.getId() + "/comment")
          .param("content", commentForm.getContent())
          .with(csrf()))  
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));
      
      int afterCount = commentRepository.findByPrototypeId(prototypeEntity.getId()).size();
    
      assertEquals(beforeCount, afterCount);
    }

    @Test
    @WithUserDetails(value = TEST_USER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void 存在しないプロトタイプにはコメントを投稿できない() throws Exception {
      CommentForm commentForm = CommentFormFactory.createComment();
      Integer nonExistentId = 99999;
      
      mockMvc.perform(post("/prototypes/" + nonExistentId + "/comment")
          .param("content", commentForm.getContent())
          .with(csrf()))
        .andExpect(status().isNotFound()); 
      
      
    }
  }

  @Nested
  class コメント表示の確認 {

    @Test
    @WithUserDetails(value = TEST_USER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void プロトタイプ詳細ページに投稿したコメントとその投稿者名が表示される() throws Exception {
      CommentForm commentForm = CommentFormFactory.createComment();
      CommentEntity commentEntity = new CommentEntity();
      commentEntity.setContent(commentForm.getContent());
      commentEntity.setPrototype(prototypeEntity);
      commentEntity.setUser(userEntity);
      commentRepository.insert(commentEntity);

      mockMvc.perform(get("/prototypes/" + prototypeEntity.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("prototypes/detail"))
        .andExpect(model().attributeExists("comments"))
        .andExpect(result -> {
          @SuppressWarnings("unchecked")
          List<CommentEntity> comments = (List<CommentEntity>) result.getModelAndView()
            .getModel().get("comments");
          
          // コメントが1件存在することを確認
          assertEquals(1, comments.size());
          
          // コメント内容と投稿者名を確認
          CommentEntity displayedComment = comments.get(0);
          assertEquals(commentEntity.getContent(), displayedComment.getContent());
          assertEquals(userEntity.getName(), displayedComment.getUser().getName());
        });
    }
  }
  
}
