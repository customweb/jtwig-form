package com.customweb.jtwig.form;


import org.junit.Test;

import com.customweb.jtwig.form.FormAddon;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.resource.ClasspathJtwigResource;

public class FormTest {

	@Test
	public void test() throws ParseException, CompileException, RenderException {
		
		JtwigConfiguration config = new JtwigConfiguration();
		config.parse().addons().withAddon(FormAddon.class);
		JtwigTemplate template = new JtwigTemplate(new ClasspathJtwigResource("classpath:/views/default.twig.html"), config);
		
		JtwigModelMap map = new JtwigModelMap();
		
		
		map.add("dataObject", new DataObject());
		
        String result = template.output(map);
        
        System.out.println(result);
        
	}
	
	public class DataObject {
		private String value1  = "test";
	}

}