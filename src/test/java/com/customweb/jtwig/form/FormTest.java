package com.customweb.jtwig.form;


import java.util.Arrays;

import org.junit.Test;

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
		config.parse().addons().withAddon(FormCheckboxAddon.class);
		config.parse().addons().withAddon(FormMultiCheckboxAddon.class);
		config.parse().addons().withAddon(FormLabelAddon.class);
		JtwigTemplate template = new JtwigTemplate(new ClasspathJtwigResource("classpath:/views/default.twig"), config);
		
		JtwigModelMap map = new JtwigModelMap();
		
		
		map.add("dataobject", new DataObject());
		map.add("itemlist", Arrays.asList(new String[]{ "testValue", "hallo", "welt" }));
		
        String result = template.output(map);
        
        System.out.println(result);
        
	}
	
	public class DataObject {
		private String active  = "test";
		
		public String getActive() {
			return this.active + "Value";
		}
	}

}