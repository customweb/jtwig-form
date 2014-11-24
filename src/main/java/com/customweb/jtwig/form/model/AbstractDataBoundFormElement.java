package com.customweb.jtwig.form.model;

import java.util.NoSuchElementException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

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

		public final String getPath() {
			return this.getAttributeValue("path");
		}

		public final String getName(RenderContext context) {
			if (this.hasDataModelProperty(context, this.getPath())) {
				return context.map("formDataModelVariable") + "." + this.getPath();
			}
			return this.getPath();
		}

		public final String getId(RenderContext context) {
			String id = StringUtils.remove(StringUtils.remove(this.getName(context), '['), ']');
			if (this.getAttributeCollection().hasAttribute("id")) {
				String value = this.getAttributeValue("id");
				if (value != null && !value.isEmpty()) {
					id = value;
				}
			}
			return id;
		}

		public boolean hasDataModel(RenderContext context) {
			Object dataObject = context.map("formDataModel");
			return dataObject != null && !dataObject.equals(Undefined.UNDEFINED);
		}

		public boolean hasDataModelProperty(RenderContext context, String fieldName) {
			if (!this.hasDataModel(context)) {
				return false;
			}

			try {
				PropertyUtils.getProperty(this.getDataModel(context), fieldName);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		public Object getDataModel(RenderContext context) throws NoSuchElementException {
			if (!this.hasDataModel(context)) {
				throw new NoSuchElementException("The form data model has not been set.");
			}
			return context.map("formDataModel");
		}

		public Object getDataValue(RenderContext context, String fieldName) {
			if (this.hasDataModelProperty(context, fieldName)) {
				try {
					return PropertyUtils.getProperty(this.getDataModel(context), fieldName);
				} catch (Exception e) {
				}
			}
			Object fieldValue = context.map("fieldName");
			if (fieldValue.equals(Undefined.UNDEFINED)) {
				throw new RuntimeException("The form data model does not have a field named '" + fieldName + "'.");
			}
			return fieldValue;
		}
	}

}
