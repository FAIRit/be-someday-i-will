package pl.fairit.somedayiwill.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }
}
