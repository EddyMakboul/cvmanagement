package cvmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class VueAppController {

	@RequestMapping(value = "/home")
	private ModelAndView home() {
		return new ModelAndView("home");
	}

}
