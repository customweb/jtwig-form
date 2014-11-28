package com.customweb.jtwig.form.tag;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ObjectUtils;

import com.customweb.jtwig.form.model.IdGenerator;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.VariableAttribute;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormMultiElementTag<T extends AbstractFormMultiElementTag<T>> extends AbstractFormInputElementTag<T> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("items", true));
		attributeDefinitions.add(new NamedAttributeDefinition("itemLabel", false));
		attributeDefinitions.add(new NamedAttributeDefinition("itemValue", false));
		return attributeDefinitions;
	}

	abstract protected class Compiled extends AbstractFormInputElementTag<T>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}
		
		public boolean hasItems() {
			return this.getAttributeCollection().hasAttribute("items");
		}

		public Collection<?> getItems(RenderContext context) {
			Object items = this.getAttributeCollection().getAttribute("items", VariableAttribute.class).getVariable(context);
			if (items instanceof Collection) {
				return (Collection<?>) items;
			} else if (items.getClass().isArray()) {
				return Arrays.asList((Object[]) items);
			}
			throw new RuntimeException("The 'items' attribute value has to be a collection.");
		}
	}
	
	abstract public class Data extends AbstractFormInputElementTag<T>.Data {
		private List<String> items;
		
		protected Data(List<String> items, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.items = items;
		}
		
		public List<String> getItems() {
			return this.items;
		}
	}
	
	abstract public class ItemData extends AbstractFormInputElementTag<T>.Data {
		private Object item;
		
		protected ItemData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.item = item;
		}
		
		@Override
		public String getId() {
			return IdGenerator.nextId(super.getId(), this.getContext());
		}
		
		public String getLabel() {
			if (this.getAttributeCollection().hasAttribute("itemLabel")) {
				String fieldName = this.getAttributeValue("itemLabel");
				try {
					return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(this.item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			return this.item.toString();
		}

		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("itemValue")) {
				String fieldName = this.getAttributeValue("itemValue");
				try {
					return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(this.item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			try {
				return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(this.item, "value"));
			} catch (Exception e) {
			}
			return this.item.toString();
		}
	}

}
