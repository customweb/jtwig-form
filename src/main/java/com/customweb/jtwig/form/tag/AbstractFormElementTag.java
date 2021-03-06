package com.customweb.jtwig.form.tag;

import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormElementTag<T extends AbstractFormElementTag<T>> extends AbstractFormTag<T> {
	
	abstract protected class Compiled extends AbstractFormTag<T>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}
	}
	
	abstract public class Data extends AbstractFormTag<T>.Data {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
	}

}
