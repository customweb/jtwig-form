package com.customweb.jtwig.form.model;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeModel;
import com.lyncode.jtwig.content.api.Renderable;

abstract public class AbstractFormElement<T extends AbstractFormElement<T>> extends AttributeModel<T> {

	abstract protected class AbstractFormElementCompiled extends AbstractAttributeModelCompiled {
		protected AbstractFormElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}
	}

}
