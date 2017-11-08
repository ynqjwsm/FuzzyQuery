package so.wsm.fq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(){
        return "mobile/main";
    }

    @GetMapping(value = {"/login","/l"})
    public String login(){
        return "mobile/login";
    }

}
