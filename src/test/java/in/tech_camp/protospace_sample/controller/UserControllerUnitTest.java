package in.tech_camp.protospace_sample.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import in.tech_camp.protospace_sample.entity.UserEntity;
import in.tech_camp.protospace_sample.factory.UserFormFactory;
import in.tech_camp.protospace_sample.form.UserForm;
import in.tech_camp.protospace_sample.repository.UserRepository;
import in.tech_camp.protospace_sample.service.UserService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerUnitTest {
  
  @Mock
  private UserRepository userRepository;

  @Mock
  private UserService userService;

  @Mock
  private BindingResult bindingResult;
  
  @InjectMocks
  private UserController userController;

  private UserForm userForm;
  private Model model;
  
  @BeforeEach
  public void setUp() {
    userForm = UserFormFactory.createUser();
    model = new ExtendedModelMap();
    bindingResult = mock(BindingResult.class);
  }

  @Test
  public void 重複したemailを登録しようとするとバリデーションエラーが発生する() {
    when(userRepository.existsByEmail(userForm.getEmail())).thenReturn(true);
    when(bindingResult.hasErrors()).thenReturn(true);

    userController.createUser(userForm, bindingResult, model);

    verify(bindingResult).rejectValue("email", "error.user", "Email already exists");

    verify(userRepository).existsByEmail(userForm.getEmail());
    verify(userService, never()).createUserWithEncryptedPassword(any(UserEntity.class));

  }
}
