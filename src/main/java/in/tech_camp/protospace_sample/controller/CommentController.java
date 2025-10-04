package in.tech_camp.protospace_sample.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace_sample.custom_user.CustomUserDetail;
import in.tech_camp.protospace_sample.entity.CommentEntity;
import in.tech_camp.protospace_sample.entity.PrototypeEntity;
import in.tech_camp.protospace_sample.form.CommentForm;
import in.tech_camp.protospace_sample.repository.CommentRepository;
import in.tech_camp.protospace_sample.repository.PrototypeRepository;
import in.tech_camp.protospace_sample.repository.UserRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CommentController {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final PrototypeRepository prototypeRepository;

  @PostMapping("/prototypes/{prototypeId}/comment")
  public String createComment(@PathVariable("prototypeId") Integer prototypeId, @ModelAttribute("commentForm") @Validated CommentForm commentForm, BindingResult result, @AuthenticationPrincipal CustomUserDetail currentUser, Model model) {

    PrototypeEntity prototype = prototypeRepository.findById(prototypeId);

    if (result.hasErrors()) {
        model.addAttribute("errorMessages", result.getAllErrors());
        model.addAttribute("prototype", prototype);
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("comments", prototype.getComments());
        return "prototypes/detail";
    }

    CommentEntity comment = new CommentEntity();
    comment.setContent(commentForm.getContent());
    comment.setPrototype(prototype);
    comment.setUser(userRepository.findById(currentUser.getId()));

    try {
      commentRepository.insert(comment);
    } catch (Exception e) {
      model.addAttribute("prototype", prototype);
      model.addAttribute("commentForm", commentForm);
      model.addAttribute("comments", prototype.getComments());
      System.out.println("エラー：" + e);
      return "prototypes/detail";
    }

    return "redirect:/prototypes/" + prototypeId;
  }
}