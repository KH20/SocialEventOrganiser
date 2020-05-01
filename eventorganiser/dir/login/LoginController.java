package eventorganiser.dir.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(HttpSession session) {

        if (session.getAttribute("SESSION_USERNAME") != null)
            return new ModelAndView("redirect:/");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("loginTemplate");
        return mv;
    }

}
