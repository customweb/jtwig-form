package com.customweb.jtwig.form.model;

import java.io.IOException;

import org.springframework.beans.PropertyAccessor;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.AttributeModel;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class Form extends AttributeModel<Form> {

	private static final String DEFAULT_METHOD = "post";

	private static final String DEFAULT_MODEL_ATTRIBUTE_NAME = "formModel";
	
	private static final String DEFAULT_ERRORS_ATTRIBUTE_NAME = "formErrors";

	private static final String MODEL_ATTRIBUTE = "modelAttribute";
	
	private static final String ERRORS_ATTRIBUTE = "errorsAttribute";

	public static final String MODEL_ATTRIBUTE_VARIABLE_NAME = Form.class.getName() + "." + MODEL_ATTRIBUTE;
	
	public static final String ERRORS_ATTRIBUTE_VARIABLE_NAME = Form.class.getName() + "." + ERRORS_ATTRIBUTE;

	public static final String NESTED_PATH_VARIABLE_NAME = AbstractDataBoundFormElement.NESTED_PATH_VARIABLE_NAME;

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("action", false));
		attributeDefinitions.add(new NamedAttributeDefinition("method", false));
		attributeDefinitions.add(new VariableAttributeDefinition("model", false));
		attributeDefinitions.add(new VariableAttributeDefinition("errors", false));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends AbstractAttributeModelCompiled {
		protected Compiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public String getId() {
			return this.getModelAttribute();
		}

		public String getAction() {
			if (this.getAttributeCollection().hasAttribute("action")) {
				return this.getAttributeValue("action");
			} else {
				return "";
			}
		}

		public String getMethod() {
			if (this.getAttributeCollection().hasAttribute("method")) {
				return this.getAttributeValue("method");
			} else {
				return DEFAULT_METHOD;
			}
		}

		public String getModelAttribute() {
			if (this.getAttributeCollection().hasAttribute("model")) {
				return this.getAttributeValue("model");
			} else {
				return DEFAULT_MODEL_ATTRIBUTE_NAME;
			}
		}
		
		public String getErrorsAttribute() {
			if (this.getAttributeCollection().hasAttribute("errors")) {
				return this.getAttributeValue("errors");
			} else {
				return DEFAULT_ERRORS_ATTRIBUTE_NAME;
			}
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			context = context.isolatedModel();
			context.with(MODEL_ATTRIBUTE_VARIABLE_NAME, this.getModelAttribute());
			context.with(ERRORS_ATTRIBUTE_VARIABLE_NAME, this.getErrorsAttribute());
			context.with(NESTED_PATH_VARIABLE_NAME, this.getModelAttribute()
					+ PropertyAccessor.NESTED_PROPERTY_SEPARATOR);

			try {
				context.write(("<form id=\"" + this.getId() + "\" action=\"" + this.getAction() + "\" method=\""
						+ this.getMethod() + "\"" + this.concatDynamicAttributes() + ">")
						.getBytes());
				this.getContent().render(context);
				context.write("</form>".getBytes());
			} catch (IOException e) {
			}
		}
	}

}
