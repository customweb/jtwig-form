package com.customweb.jtwig.form.tag;

import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;
import org.springframework.beans.PropertyAccessor;

import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;

public class FormTag extends AbstractFormTag<FormTag> {

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
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKeys("id");
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			Loader.Resource resource = this.retrieveResource(context, "form/form");
			return new Compiled(context.environment().parse(resource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormTag<FormTag>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}

		public String getId() {
			return this.getModelAttribute();
		}

		public String getModelAttribute() {
			return this.getAttributeValue("model", DEFAULT_MODEL_ATTRIBUTE_NAME);
		}

		public String getErrorsAttribute() {
			return this.getAttributeValue("errors", DEFAULT_ERRORS_ATTRIBUTE_NAME);
		}

		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			context.with(MODEL_ATTRIBUTE_VARIABLE_NAME, this.getModelAttribute());
			context.with(ERRORS_ATTRIBUTE_VARIABLE_NAME, this.getErrorsAttribute());
			context.with(NESTED_PATH_VARIABLE_NAME, this.getModelAttribute() + PropertyAccessor.NESTED_PROPERTY_SEPARATOR);
			context.with(FORM_ID_ATTRIBUTE_NAME, this.getId());

			context.with("form", new Data(this.getId(), this.renderContentAsString(context), context, this.getAttributeCollection()));
		}
	}

	public class Data extends AbstractFormTag<FormTag>.Data {
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
			return this.getAttributeValue("action", "");
		}

		public String getMethod() {
			return this.getAttributeValue("method", DEFAULT_METHOD);
		}

		public String getContent() {
			return this.content;
		}
	}

}
