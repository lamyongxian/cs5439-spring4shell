package sg.edu.nus.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("home");
	}

    @RequestMapping(value = "/robots.txt", method = RequestMethod.GET,produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> robots(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder builder = new StringBuilder();
		builder.append("User-agent: *\nDisallow: \n\n");

		// Strip filename
		String hostPath = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"));

		builder.append("Sitemap: " + hostPath + "/sitemap.xml");
        return new ResponseEntity<String>(builder.toString(), HttpStatus.OK);
    }

	@RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET,produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> sitemap(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
