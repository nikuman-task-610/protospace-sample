package in.tech_camp.protospace_sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PrototypeController {
  @GetMapping("/")
  @ResponseBody
  public String showTop() {
      return "<h1>トップページ/h1>";
  }
  
}
