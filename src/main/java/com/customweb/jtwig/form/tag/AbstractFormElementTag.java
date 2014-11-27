package com.customweb.jtwig.form.tag;

import com.customweb.jtwig.lib.attribute.model.AbstractAttributeTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormElementTag<T extends AbstractFormElementTag<T>> extends AbstractAttributeTag<T> {
	
	abstract protected class AbstractFormElementCompiled extends AbstractAttributeModelCompiled {
		protected AbstractFormElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}
	}
	
	abstract public class AbstractFormElementData extends AbstractAttributeModelData {
		protected AbstractFormElementData(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
	}

}
