package com.customweb.jtwig.form.model;

import java.util.NoSuchElementException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

public class AbstractDataBoundFormElement<T extends AbstractDataBoundFormElement<T>> extends AbstractFormElement<T> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("path", true));
		attributeDefinitions.add(new NamedAttributeDefinition("id", false));
		return attributeDefinitions;
	}

	protected abstract class AbstractDataBoundFormElementCompiled extends AbstractFormElementCompiled {
		protected AbstractDataBoundFormElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public String getPath() {
			return this.getAttributeValue("path");
		}

		public String getName() {
			return this.getPath();
		}

		public String getId(RenderContext context) {
			String id = StringUtils.remove(StringUtils.remove(this.getName(), '['), ']');
			if (this.getAttributeCollection().hasAttribute("id")) {
				String value = this.getAttributeValue("id");
				if (value != null && !value.isEmpty()) {
					id = value;
				}
			}
			return id;
		}

		public Object getDataModel(RenderContext context) throws NoSuchElementException {
			return context.map("formDataModel");
		}

		public Object getDataValue(RenderContext context, String fieldName) {
			try {
				return PropertyUtils.getProperty(this.getDataModel(context), fieldName);
			} catch (Exception e) {
				throw new RuntimeException("The form data model does not have a field named '" + fieldName + "'.");
			}
		}
	}

}
