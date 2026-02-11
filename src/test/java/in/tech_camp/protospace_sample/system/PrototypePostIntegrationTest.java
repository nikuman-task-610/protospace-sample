package in.tech_camp.protospace_sample.system;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.protospace_sample.ProtospaceSampleApplication;
import in.tech_camp.protospace_sample.entity.UserEntity;
import in.tech_camp.protospace_sample.factory.PrototypeFormFactory;
import in.tech_camp.protospace_sample.factory.UserEntityFactory;
import in.tech_camp.protospace_sample.form.PrototypeForm;
import in.tech_camp.protospace_sample.repository.PrototypeRepository;
import in.tech_camp.protospace_sample.service.UserService;

@ActiveProfiles("test")
@SpringBootTest(classes = ProtospaceSampleApplication.class)
@AutoConfigureMockMvc
@Transactional
public class PrototypePostIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Autowired
  private PrototypeRepository prototypeRepository;

  private UserEntity userEntity;
  private static final String TEST_USER_EMAIL = "test-user@example.com";

  @BeforeEach
  public void setUp() {
    userEntity = UserEntityFactory.createUser(TEST_USER_EMAIL);
    userService.createUserWithEncryptedPassword(userEntity);
  }

  @Nested
  class プロトタイプが投稿できる時 {
    @Test
    @WithUserDetails(value = TEST_USER_EMAIL, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void 実際のユーザーとして投稿でき外部キーが正しく設定される() throws Exception {
      PrototypeForm prototypeForm = PrototypeFormFactory.createPrototype();
      int beforeCount = prototypeRepository.findAll().size();

      mockMvc.perform(multipart("/prototypes")
              .file((MockMultipartFile) prototypeForm.getImageFile())
              .param("title", prototypeForm.getTitle())
              .param("catchCopy", prototypeForm.getCatchCopy())
              .param("concept", prototypeForm.getConcept())
              .with(csrf()))
              .andExpect(status().is3xxRedirection());
      
      int afterCount = prototypeRepository.findAll().size();
      assertThat(afterCount).isEqualTo(beforeCount + 1);
    }
  }  

  @Nested
  class プロトタイプが投稿できない時 {
    @Test
    public void 外部キーが付与されていなければプロトタイプを投稿できない() throws Exception {
      PrototypeForm prototypeForm = PrototypeFormFactory.createPrototype();
      
      mockMvc.perform(multipart("/prototypes")
              .file((MockMultipartFile) prototypeForm.getImageFile())
              .param("title", prototypeForm.getTitle())
              .param("catchCopy", prototypeForm.getCatchCopy())
              .param("concept", prototypeForm.getConcept())
              .with(csrf()))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern("**/login"));
    }
  }
}