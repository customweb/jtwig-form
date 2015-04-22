package com.customweb.jtwig.form.tag.element;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;

import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;

public class FormButtonTag extends AbstractFormElementTag<FormButtonTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("id", false));
		attributeDefinitions.add(new NamedAttributeDefinition("name", false));
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new NamedAttributeDefinition("disabled", false));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("type");
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			Loader.Resource formResource = this.retrieveResource(context, "form/button");
			return new Compiled(context.environment().parse(formResource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}
	
	private class Compiled extends AbstractFormElementTag<FormButtonTag>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}

		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			context.with("button", new Data(this.renderContentAsString(context), context, this.getAttributeCollection()));
		}
	}
	
	public class Data extends AbstractFormElementTag<FormButtonTag>.Data {
		private String label;
		
		protected Data(String label, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.label = label;
		}
		
		public String getId() {
			if (!this.getAttributeCollection().hasAttribute("id") && !this.getAttributeCollection().hasAttribute("name")) {
				return null;
			}
			String id = StringUtils.remove(StringUtils.remove(this.getName(), '['), ']');
			if (this.getAttributeCollection().hasAttribute("id")) {
				String value = this.getAttributeValue("id");
				if (value != null && !value.isEmpty()) {
					id = value;
				}
			}
			return id;
		}

		public String getName() {
			return this.getAttributeValue("name", null);
		}

		public String getValue() {
			return this.getAttributeValue("value", "Submit");
		}

		public boolean isDisabled() {
			return Boolean.parseBoolean(this.getAttributeValue("disabled", "false"));
		}
		
		public String getLabel() {
			return this.label;
		}
	}

}
