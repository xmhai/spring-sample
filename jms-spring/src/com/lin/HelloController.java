package com.lin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping(path= "/")
    @ResponseBody
    public String greet(){
        return "hello world";
    }
}