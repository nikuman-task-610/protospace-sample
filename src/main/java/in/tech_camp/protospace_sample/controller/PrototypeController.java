package in.tech_camp.protospace_sample.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.tech_camp.protospace_sample.custom_user.CustomUserDetail;
import in.tech_camp.protospace_sample.entity.PrototypeEntity;
import in.tech_camp.protospace_sample.form.CommentForm;
import in.tech_camp.protospace_sample.form.PrototypeForm;
import in.tech_camp.protospace_sample.repository.PrototypeRepository;
import in.tech_camp.protospace_sample.repository.UserRepository;
import in.tech_camp.protospace_sample.validation.ValidationPriority1;
import in.tech_camp.protospace_sample.validation.ValidationPriority2;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;





@Controller
@AllArgsConstructor
public class PrototypeController {
    private final PrototypeRepository prototypeRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String showTop(Model model) {
        List<PrototypeEntity> prototypes = prototypeRepository.findAll();
        model.addAttribute("prototypes", prototypes);
        return "prototypes/index";
    }

    @GetMapping("/prototypes/new")
    public String showPrototypeNew(Model model) {
        model.addAttribute("prototypeForm", new PrototypeForm());
        return "prototypes/new";
    }

    @PostMapping("/prototypes")
    public String createPrototype(
            @Validated(ValidationPriority1.class) @ModelAttribute("prototypeForm") PrototypeForm prototypeForm, 
            BindingResult result,
            @AuthenticationPrincipal CustomUserDetail currentUser, 
            Model model, 
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errorMessages", errorMessages);
            model.addAttribute("prototypeForm", prototypeForm);
            return "prototypes/new";
        }

        try {
            PrototypeEntity prototype = new PrototypeEntity();
            prototype.setTitle(prototypeForm.getTitle());
            prototype.setCatchCopy(prototypeForm.getCatchCopy());
            prototype.setConcept(prototypeForm.getConcept());
            prototype.setUser(userRepository.findById(currentUser.getId()));
            
            if (prototypeForm.getImageFile() != null && !prototypeForm.getImageFile().isEmpty()) {
                prototype.setImageName(prototypeForm.getImageFile().getOriginalFilename());
                prototype.setImageType(prototypeForm.getImageFile().getContentType());
                prototype.setImageData(prototypeForm.getImageFile().getBytes());
            }
            
            prototypeRepository.insert(prototype);
            
        } catch (IOException e) {
            System.out.println("画像処理エラー：" + e);
            model.addAttribute("errorMessages", List.of("画像の処理中にエラーが発生しました"));
            model.addAttribute("prototypeForm", prototypeForm);
            return "prototypes/new";
        } catch (Exception e) {
            System.out.println("データベースエラー：" + e);
            model.addAttribute("errorMessages", List.of("保存中にエラーが発生しました"));
            model.addAttribute("prototypeForm", prototypeForm);
            return "prototypes/new";
        }
        
        return "redirect:/";
    }

    @GetMapping("/prototypes/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        try {
            PrototypeEntity prototype = prototypeRepository.findById(id);
            
            if (prototype == null || prototype.getImageData() == null) {
                return ResponseEntity.notFound().build();
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(prototype.getImageType()));
            
            return new ResponseEntity<>(prototype.getImageData(), headers, HttpStatus.OK);
            
        } catch (Exception e) {
            System.out.println("画像取得エラー：" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/prototypes/{prototypeId}")
    public String showPrototypeDetail(@PathVariable("prototypeId") Integer prototypeId, Model model) {
        PrototypeEntity prototype = prototypeRepository.findById(prototypeId);
        CommentForm commentForm = new CommentForm();

        model.addAttribute("prototype", prototype);
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("comments", prototype.getComments());
        return "prototypes/detail";
    }

    @GetMapping("/prototypes/{prototypeId}/edit")
    public String editPrototype(@PathVariable("prototypeId") Integer prototypeId, @AuthenticationPrincipal CustomUserDetail currentUser, Model model) {
        PrototypeEntity prototype = prototypeRepository.findById(prototypeId);

        if (!prototype.getUser().getId().equals(currentUser.getId())) {
        return "redirect:/";  
    }
        PrototypeForm prototypeForm = new PrototypeForm();
        prototypeForm.setTitle(prototype.getTitle());
        prototypeForm.setCatchCopy(prototype.getCatchCopy());
        prototypeForm.setConcept(prototype.getConcept());

      model.addAttribute("prototypeForm", prototypeForm);
      model.addAttribute("prototypeId", prototypeId);
      model.addAttribute("prototype", prototype);
      return "prototypes/edit";
    }
    
    @PostMapping("/prototypes/{prototypeId}/update")
    public String updatePrototype(@ModelAttribute("prototypeForm") @Validated(ValidationPriority2.class) PrototypeForm prototypeForm, BindingResult result, @PathVariable("prototypeId") Integer prototypeId, Model model) {
        
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errorMessages", errorMessages);

            model.addAttribute("prototypeForm", prototypeForm);
            model.addAttribute("prototypeId", prototypeId);
            return "prototypes/edit";
        }
        PrototypeEntity prototype = prototypeRepository.findById(prototypeId);
        prototype.setTitle(prototypeForm.getTitle());
        prototype.setCatchCopy(prototypeForm.getCatchCopy());
        prototype.setConcept(prototypeForm.getConcept());
    
    // 画像ファイルがアップロードされた場合のみ画像を更新
    if (prototypeForm.getImageFile() != null && !prototypeForm.getImageFile().isEmpty()) {
       try {
            MultipartFile imageFile = prototypeForm.getImageFile();
            prototype.setImageName(imageFile.getOriginalFilename());
            prototype.setImageType(imageFile.getContentType());
            prototype.setImageData(imageFile.getBytes()); 
            
            prototypeRepository.update(prototype);
        } catch (IOException e) {
            // エラー処理
            model.addAttribute("errorMessage", "画像のアップロードに失敗しました");
            model.addAttribute("prototypeId", prototypeId);
            model.addAttribute("prototype", prototype);
            model.addAttribute("prototype", prototype);
            return "prototypes/edit";
        }
    } else {
        // 画像以外のデータのみ更新
        prototypeRepository.updateWithoutImage(prototype);
    }
    
    return "redirect:/prototypes/" + prototypeId;
    }

    @PostMapping("/prototypes/{prototypeId}/delete")
    public String deletePrototype(@PathVariable("prototypeId") Integer prototypeId, @AuthenticationPrincipal CustomUserDetail currentUser) {
         PrototypeEntity prototype = prototypeRepository.findById(prototypeId);

        if (!prototype.getUser().getId().equals(currentUser.getId())) {
        return "redirect:/";
    }

        try {
          prototypeRepository.deleteById(prototypeId);
        } catch (Exception e) {
            System.out.println("エラー：" + e);
            return "redirect:/";
        }
        return "redirect:/";
    }
    
    
}
