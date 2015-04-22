package com.customweb.jtwig.form.tag.element;

import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;

import com.customweb.jtwig.form.model.IdGenerator;
import com.customweb.jtwig.form.tag.AbstractFormInputElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;

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
			Loader.Resource resource = this.retrieveResource(context, "form/checkbox");
			return new Compiled(context.environment().parse(resource).compile(context), this.getAttributeCollection());
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
