package com.yi.adminserver.web.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void index(HttpServletResponse response) throws IOException {
		response.getWriter().println("admin server works");
	}
}