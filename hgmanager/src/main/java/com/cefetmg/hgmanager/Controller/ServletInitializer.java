package com.cefetmg.hgmanager.Controller;

import com.cefetmg.hgmanager.Controller.HgManagerApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HgManagerApplication.class);
	}

}
