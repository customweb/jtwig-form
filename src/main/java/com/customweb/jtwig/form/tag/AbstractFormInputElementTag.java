package com.customweb.jtwig.form.tag;

import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormInputElementTag<T extends AbstractFormInputElementTag<T>> extends AbstractDataBoundFormElementTag<T> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("name", false));
		attributeDefinitions.add(new NamedAttributeDefinition("disabled", false));
		return attributeDefinitions;
	}

	abstract protected class Compiled extends AbstractDataBoundFormElementTag<T>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}
	}
	
	abstract public class Data extends AbstractDataBoundFormElementTag<T>.Data {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		@Override
		public String getName() {
			return this.getAttributeValue("name", super.getName());
		}
		
		public boolean isDisabled() {
			return Boolean.parseBoolean(this.getAttributeValue("disabled", "false"));
		}
	}

}
