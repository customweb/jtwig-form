package com.customweb.jtwig.form.tag;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.PropertyAccessor;

import com.customweb.jtwig.form.model.BindStatus;
import com.customweb.jtwig.form.model.SelectedValueComparator;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

public class AbstractDataBoundFormElementTag<T extends AbstractDataBoundFormElementTag<T>> extends AbstractFormElementTag<T> {
	
	public static final String NESTED_PATH_VARIABLE_NAME = "nestedPath";

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
	}
	
	abstract public class AbstractDataBoundFormElementData extends AbstractFormElementData {
		private BindStatus bindStatus;
		
		protected AbstractDataBoundFormElementData(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		public String getPath() {
			return this.getAttributeValue("path");
		}

		public String getName() {
			return this.getPropertyPath();
		}

		public String getId() {
			String id = StringUtils.remove(StringUtils.remove(this.getName(), '['), ']');
			if (this.getAttributeCollection().hasAttribute("id")) {
				String value = this.getAttributeValue("id");
				if (value != null && !value.isEmpty()) {
					id = value;
				}
			}
			return id;
		}
		
		public BindStatus getBindStatus() {
			if (this.bindStatus == null) {
				// HTML escaping in tags is performed by the ValueFormatter class.
				String nestedPath = getNestedPath();
				String pathToUse = (nestedPath != null ? nestedPath + getPath() : getPath());
				if (pathToUse.endsWith(PropertyAccessor.NESTED_PROPERTY_SEPARATOR)) {
					pathToUse = pathToUse.substring(0, pathToUse.length() - 1);
				}
				this.bindStatus = new BindStatus(this.getContext(), pathToUse, this.isHtmlEscape());
			}
			return this.bindStatus;
		}
		
		private String getNestedPath() {
			Object nestedPath = this.getContext().map(NESTED_PATH_VARIABLE_NAME);
			if (nestedPath == null || nestedPath.equals(Undefined.UNDEFINED)) {
				return "";
			}
			return (String) nestedPath;
		}
		
		private String getPropertyPath() {
			String expression = this.getBindStatus().getExpression();
			return (expression != null ? expression : "");
		}

		public Object getBoundValue() {
			return this.getBindStatus().getValue();
		}
		
		public String getBoundDisplayValue() {
			return this.getBindStatus().getDisplayValue();
		}
		
		public String getValue() {
			return this.getBoundDisplayValue();
		}
		
		protected boolean isOptionSelected(Object value) {
			return SelectedValueComparator.isSelected(getBindStatus(), value);
		}
	}

}
