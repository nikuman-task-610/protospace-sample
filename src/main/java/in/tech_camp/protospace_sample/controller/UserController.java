package in.tech_camp.protospace_sample.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace_sample.entity.PrototypeEntity;
import in.tech_camp.protospace_sample.entity.UserEntity;
import in.tech_camp.protospace_sample.form.UserForm;
import in.tech_camp.protospace_sample.repository.UserRepository;
import in.tech_camp.protospace_sample.service.UserService;
import in.tech_camp.protospace_sample.validation.ValidationOrder;
import lombok.AllArgsConstructor;





@Controller
@AllArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  private final UserService userService;

  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }

  @PostMapping("/users")
  public String createUser(@ModelAttribute("userForm") @Validated(ValidationOrder.class) UserForm userForm, BindingResult result, Model model) {
      userForm.validatePasswordConfirmation(result);
      if (userRepository.existsByEmail(userForm.getEmail())) {
        result.rejectValue("email", "error.user", "Email already exists");
      }

      if (result.hasErrors()) {
        List<String> errorMessages = result.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

        model.addAttribute("errorMessages", errorMessages);
        model.addAttribute("userForm", userForm);
        return "users/signUp";
      }

      UserEntity userEntity = new UserEntity();
      userEntity.setName(userForm.getName());
      userEntity.setEmail(userForm.getEmail());
      userEntity.setEncryptedPassword(userForm.getEncryptedPassword());
      userEntity.setProfile(userForm.getProfile());
      userEntity.setOccupation(userForm.getOccupation());
      userEntity.setPosition(userForm.getPosition());

      try {
          userService.createUserWithEncryptedPassword(userEntity);
      } catch (Exception e) {
        System.out.println("エラー：" + e);
        return "redirect:/";
      }
      
      return "redirect:/";
  }

  @GetMapping("/users/login")
  public String showLogin() {
      return "users/login";
  }

  @GetMapping("/users/{userId}")
  public String showMypage(@PathVariable("userId") Integer userId, Model model) {
      UserEntity user = userRepository.findById(userId);
      List<PrototypeEntity> prototypes = user.getPrototypes();

      model.addAttribute("user", user);
      model.addAttribute("prototypes", prototypes);
      return "users/detail";
  }
  
}
