package com.cooljs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 欢迎界面
 */
@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("text", "HELLO COOL-ADMIN FOR JAVA");
        return "welcome";
    }
}
