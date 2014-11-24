package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.google.common.collect.Lists;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

public class FormErrors extends AbstractDataBoundFormElement<FormErrors> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("element", false));
		attributeDefinitions.add(new NamedAttributeDefinition("delimiter", false));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractDataBoundFormElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}
		
		@Override
		public String getId(RenderContext context) {
			return super.getId(context) + ".errors";
		}

		public String getElement() {
			if (this.getAttributeCollection().hasAttribute("element")) {
				return this.getAttributeValue("element");
			}
			return "span";
		}

		public String getDelimiter() {
			if (this.getAttributeCollection().hasAttribute("delimiter")) {
				return this.getAttributeValue("delimiter");
			}
			return "<br>";
		}

		public boolean hasErrors(RenderContext context) {
			if (this.hasErrorModel(context)) {
				return this.getErrorMap(context).containsKey(this.getPath());
			} else {
				return false;
			}
		}

		public List<String> getErrors(RenderContext context) {
			if (this.hasErrorModel(context)) {
				return this.getErrorMap(context).get(this.getPath());
			} else {
				return Lists.newArrayList();
			}
		}

		public boolean hasErrorModel(RenderContext context) {
			Object errors = context.map("formErrorModel");
			return errors != null && !errors.equals(Undefined.UNDEFINED);
		}

		@SuppressWarnings("unchecked")
		public Map<String, List<String>> getErrorMap(RenderContext context) {
			if (!this.hasErrorModel(context)) {
				throw new NoSuchElementException("The form error model has not been set.");
			}
			Object errorModel = context.map("formErrorModel");
			Map<String, List<String>> errorMap;
			if (errorModel instanceof Errors) {
				errorMap = new HashMap<String, List<String>>();
				for (FieldError fieldError : ((List<FieldError>) ((Errors) errorModel).getFieldErrors())) {
					if (!errorMap.containsKey(fieldError.getField())) {
						errorMap.put(fieldError.getField(), new ArrayList<String>());
					}
					errorMap.get(fieldError.getField()).add(fieldError.getDefaultMessage());
				}
			} else if (errorModel instanceof Map) {
				errorMap = (Map<String, List<String>>) errorModel;
			} else {
				throw new RuntimeException("The type of the error model is not valid.");
			}
			return errorMap;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				if (this.hasErrors(context)) {
					context.write(("<" + this.getElement() + Utils.concatAttributes(this.getDynamicAttributes()) + " id=\"" + this.getId(context) + "\">").getBytes());
					Iterator<String> iterator = this.getErrors(context).iterator();
					while (iterator.hasNext()) {
						context.write((this.escapeHtml(iterator.next())).getBytes());
						if (iterator.hasNext()) {
							context.write(this.getDelimiter().getBytes());
						}
					}
					context.write(("</" + this.getElement() + ">").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
