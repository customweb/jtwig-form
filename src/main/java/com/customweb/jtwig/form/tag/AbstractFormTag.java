package com.customweb.jtwig.form.tag;

import com.customweb.jtwig.lib.attribute.model.AbstractAttributeTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormTag<T extends AbstractFormTag<T>> extends AbstractAttributeTag<T> {
	
	abstract protected class Compiled extends AbstractAttributeTag<T>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}
	}
	
	abstract public class Data extends AbstractAttributeTag<T>.Data {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
	}

}
