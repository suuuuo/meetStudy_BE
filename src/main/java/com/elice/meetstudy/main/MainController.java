package com.elice.meetstudy.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @GetMapping("/")
  public String testHello() {
    return "team09 H I &#x0021 &#x0021 &#x005E&#x3145&#x005E &#x2661;";
  }

}
