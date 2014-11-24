package com.customweb.jtwig.form.model;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;

abstract public class AbstractFormInputElement<T extends AbstractFormInputElement<T>> extends AbstractDataBoundFormElement<T> {

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

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}
	}

}
