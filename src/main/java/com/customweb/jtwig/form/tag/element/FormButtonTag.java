package com.customweb.jtwig.form.tag.element;

import org.apache.commons.lang3.StringUtils;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormButtonTag extends AbstractFormElementTag<FormButtonTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("name", false));
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("type");
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource formResource = FormAddon.getResourceHandler().resolve("element/button");
			return new Compiled(context.parse(formResource).compile(context), super.compile(context), this.getAttributeCollection());
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
			if (!this.getAttributeCollection().hasAttribute("name")) {
				return null;
			}
			return this.getAttributeValue("name");
		}

		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("value")) {
				return this.getAttributeValue("value");
			} else {
				return "Submit";
			}
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}
		
		public String getLabel() {
			return this.label;
		}
	}

}
