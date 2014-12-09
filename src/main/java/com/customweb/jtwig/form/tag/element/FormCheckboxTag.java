package com.customweb.jtwig.form.tag.element;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.IdGenerator;
import com.customweb.jtwig.form.tag.AbstractFormInputElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormCheckboxTag extends AbstractFormInputElementTag<FormCheckboxTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new NamedAttributeDefinition("label", false));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKeys("type", "checked");
		return attributeDefinitions;
	}
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		this.getAttributeCollection().compile(context);
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("checkbox");
			return new Compiled(context.parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormInputElementTag<FormCheckboxTag>.Compiled {
		protected Compiled(Renderable block, AttributeCollection attributeCollection) {
			super(block, null, attributeCollection);
		}

		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			context.with("checkbox", new Data(context, this.getAttributeCollection()));
		}
	}
	
	protected class Data extends AbstractFormInputElementTag<FormCheckboxTag>.Data {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		@Override
		public String getId() {
			return this.getAttributeValue("id", IdGenerator.nextId(super.getId(), this.getContext()));
		}
		
		@Override
		public String getValue() {
			if (Boolean.class.equals(this.getBindStatus().getValueType()) || boolean.class.equals(this.getBindStatus().getValueType())) {
				return "true";
			}
			if (this.getAttributeCollection().hasAttribute("value")) {
				return this.getAttributeValue("value");
			} else {
				throw new RuntimeException("Attribute 'value' is required when binding to non-boolean values");
			}
		}
		
		public String getLabel() {
			return this.getAttributeValue("label", null);
		}

		public boolean isChecked() {
			Object actualValue = this.getBoundValue();
			if (Boolean.class.equals(this.getBindStatus().getValueType()) || boolean.class.equals(this.getBindStatus().getValueType())) {
				if (actualValue instanceof String) {
					actualValue = Boolean.valueOf((String) actualValue);
				}
				return (actualValue != null ? (Boolean) actualValue : Boolean.FALSE);
			} else {
				return this.isOptionSelected(this.getAttributeValue("value"));
			}
		}
	}
}
