package com.greenfox.tribes1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HerokuController {

  @GetMapping("/test")
  public String intro(){
    return "This is heroku test";
  }

  @GetMapping("/heroku")
  public String heroku(){
    return "another heroku stuff ...";
  }
}
