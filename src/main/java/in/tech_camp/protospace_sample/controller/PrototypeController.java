package in.tech_camp.protospace_sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrototypeController {
  @GetMapping("/")
  public String showTop() {
      return "prototypes/index";
  }
  
}
