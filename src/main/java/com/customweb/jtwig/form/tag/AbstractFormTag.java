package com.customweb.jtwig.form.tag;

import com.customweb.jtwig.lib.attribute.model.AbstractAttributeTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormTag<T extends AbstractFormTag<T>> extends AbstractAttributeTag<T> {
	
	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("class", false));
		attributeDefinitions.add(new NamedAttributeDefinition("style", false));
		return attributeDefinitions;
	}
	
	abstract protected class Compiled extends AbstractAttributeTag<T>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}
	}
	
	abstract public class Data extends AbstractAttributeTag<T>.Data {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		public String getCssClass() {
			return this.getAttributeValue("class", null);
		}
		
		public String getCssStyle() {
			return this.getAttributeValue("style", null);
		}
	}

}
