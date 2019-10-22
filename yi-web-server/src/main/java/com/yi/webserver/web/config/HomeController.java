package com.yi.webserver.web.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void index(HttpServletResponse response) throws IOException {
		response.getWriter().println("Yi web Server works!");
	}
}