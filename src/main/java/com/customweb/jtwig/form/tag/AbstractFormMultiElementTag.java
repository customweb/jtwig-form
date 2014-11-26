package com.customweb.jtwig.form.tag;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ObjectUtils;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.customweb.jtwig.lib.model.VariableAttribute;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
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

	abstract protected class AbstractFormMultiElementCompiled extends AbstractFormInputElementCompiled {
		protected AbstractFormMultiElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
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

		public String getItemLabel(Object item) {
			if (this.getAttributeCollection().hasAttribute("itemLabel")) {
				String fieldName = this.getAttributeValue("itemLabel");
				try {
					return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			return item.toString();
		}

		public String getItemValue(Object item) {
			if (this.getAttributeCollection().hasAttribute("itemValue")) {
				String fieldName = this.getAttributeValue("itemValue");
				try {
					return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			try {
				return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(item, "value"));
			} catch (Exception e) {
			}
			return item.toString();
		}
	}

}
