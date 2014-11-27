package com.customweb.jtwig.form.tag;

import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.EmptyAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormInputElementTag<T extends AbstractFormInputElementTag<T>> extends AbstractDataBoundFormElementTag<T> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	abstract protected class AbstractFormInputElementCompiled extends AbstractDataBoundFormElementCompiled {
		protected AbstractFormInputElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}
	}
	
	abstract public class AbstractFormInputElementData extends AbstractDataBoundFormElementData {
		protected AbstractFormInputElementData(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}
	}

}
