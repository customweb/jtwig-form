package com.customweb.jtwig.form.tag;

import org.springframework.beans.PropertyAccessor;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.lib.attribute.model.AbstractAttributeTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTag extends AbstractAttributeTag<FormTag> {

	private static final String DEFAULT_METHOD = "post";

	private static final String DEFAULT_MODEL_ATTRIBUTE_NAME = "formModel";

	private static final String DEFAULT_ERRORS_ATTRIBUTE_NAME = "formErrors";

	private static final String MODEL_ATTRIBUTE = "modelAttribute";

	private static final String ERRORS_ATTRIBUTE = "errorsAttribute";

	public static final String MODEL_ATTRIBUTE_VARIABLE_NAME = FormTag.class.getName() + "." + MODEL_ATTRIBUTE;

	public static final String ERRORS_ATTRIBUTE_VARIABLE_NAME = FormTag.class.getName() + "." + ERRORS_ATTRIBUTE;

	public static final String NESTED_PATH_VARIABLE_NAME = AbstractDataBoundFormElementTag.NESTED_PATH_VARIABLE_NAME;

	public static final String FORM_ID_ATTRIBUTE_NAME = FormTag.class.getName() + ".id";

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
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("form");
			return new Compiled(context.parse(resource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractAttributeModelCompiled {
		private Renderable block;
		
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
			this.block = block;
		}

		public String getId() {
			return this.getModelAttribute();
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
			context.with(FORM_ID_ATTRIBUTE_NAME, this.getId());
			
			context.with("form", new Data(this.getId(), this.renderContentAsString(context), context, this.getAttributeCollection()));
			
			block.render(context);
		}
	}
	
	public class Data extends AbstractAttributeModelData {
		private String id;
		private String content;
		
		protected Data(String id, String content, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.id = id;
			this.content = content;
		}
		
		public String getId() {
			return this.id;
		}

		public String getAction() throws ResourceException {
			if (this.getAttributeCollection().hasAttribute("action")) {
				return FormAddon.getPathHandler().resolve(this.getAttributeValue("action"));
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
		
		public String getContent() {
			return this.content;
		}
	}

}
