package com.customweb.jtwig.form;


import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.google.common.collect.Lists;
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
		config.parse().addons().withAddon(FormInputAddon.class);
		config.parse().addons().withAddon(FormHiddenAddon.class);
		config.parse().addons().withAddon(FormCheckboxAddon.class);
		config.parse().addons().withAddon(FormMultiCheckboxAddon.class);
		config.parse().addons().withAddon(FormOptionAddon.class);
		config.parse().addons().withAddon(FormMultiOptionAddon.class);
		config.parse().addons().withAddon(FormPasswordAddon.class);
		config.parse().addons().withAddon(FormRadioAddon.class);
		config.parse().addons().withAddon(FormMultiRadioAddon.class);
		config.parse().addons().withAddon(FormSelectAddon.class);
		config.parse().addons().withAddon(FormTextareaAddon.class);
		config.parse().addons().withAddon(FormLabelAddon.class);
		config.parse().addons().withAddon(FormErrorsAddon.class);
		config.parse().addons().withAddon(FormButtonAddon.class);
		JtwigTemplate template = new JtwigTemplate(new ClasspathJtwigResource("classpath:/views/default.twig"), config);
		
		JtwigModelMap map = new JtwigModelMap();
		
		
		map.add("dataobject", new DataObject());
		map.add("errors", new ErrorModel());
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
	
	public class ErrorModel implements Errors {

		@Override
		public String getObjectName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setNestedPath(String nestedPath) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getNestedPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void pushNestedPath(String subPath) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void popNestedPath() throws IllegalStateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reject(String errorCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reject(String errorCode, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rejectValue(String field, String errorCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rejectValue(String field, String errorCode, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addAllErrors(Errors errors) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean hasErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List getAllErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasGlobalErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getGlobalErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List getGlobalErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ObjectError getGlobalError() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasFieldErrors() {
			return true;
		}

		@Override
		public int getFieldErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List<FieldError> getFieldErrors() {
			List<FieldError> errors = Lists.newArrayList();
			errors.add(new FieldError("dataobject", "active", "testValue", false, new String[]{}, new String[]{}, "An error message."));
			errors.add(new FieldError("dataobject", "active", "testValue", false, new String[]{}, new String[]{}, "A second error message."));
			errors.add(new FieldError("dataobject", "test", "testValue", false, new String[]{}, new String[]{}, "A different error message."));
			return errors;
		}

		@Override
		public FieldError getFieldError() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasFieldErrors(String field) {
			return true;
		}

		@Override
		public int getFieldErrorCount(String field) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List<FieldError> getFieldErrors(String field) {
			return null;
		}

		@Override
		public FieldError getFieldError(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getFieldValue(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Class getFieldType(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}