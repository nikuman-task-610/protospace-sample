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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.tech_camp.protospace_sample.custom_user.CustomUserDetail;
import in.tech_camp.protospace_sample.entity.PrototypeEntity;
import in.tech_camp.protospace_sample.form.PrototypeForm;
import in.tech_camp.protospace_sample.repository.PrototypeRepository;
import in.tech_camp.protospace_sample.repository.UserRepository;
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
            @Valid @ModelAttribute("prototypeForm") PrototypeForm prototypeForm, 
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
        model.addAttribute("prototype", prototype);
        return "prototypes/detail";
    }
    
}
