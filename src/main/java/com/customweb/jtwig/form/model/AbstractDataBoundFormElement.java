package com.customweb.jtwig.form.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.PropertyAccessor;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

public class AbstractDataBoundFormElement<T extends AbstractDataBoundFormElement<T>> extends AbstractFormElement<T> {
	
	public static final String NESTED_PATH_VARIABLE_NAME = "nestedPath";

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("path", true));
		attributeDefinitions.add(new NamedAttributeDefinition("id", false));
		return attributeDefinitions;
	}

	protected abstract class AbstractDataBoundFormElementCompiled extends AbstractFormElementCompiled {
		private BindStatus bindStatus;
		
		protected AbstractDataBoundFormElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public String getPath() {
			return this.getAttributeValue("path");
		}

		public String getName(RenderContext context) {
			return this.getPropertyPath(context);
		}

		public String getId(RenderContext context) {
			String id = StringUtils.remove(StringUtils.remove(this.getName(context), '['), ']');
			if (this.getAttributeCollection().hasAttribute("id")) {
				String value = this.getAttributeValue("id");
				if (value != null && !value.isEmpty()) {
					id = value;
				}
			}
			return id;
		}
		
		public BindStatus getBindStatus(RenderContext context) {
			if (this.bindStatus == null) {
				// HTML escaping in tags is performed by the ValueFormatter class.
				String nestedPath = getNestedPath(context);
				String pathToUse = (nestedPath != null ? nestedPath + getPath() : getPath());
				if (pathToUse.endsWith(PropertyAccessor.NESTED_PROPERTY_SEPARATOR)) {
					pathToUse = pathToUse.substring(0, pathToUse.length() - 1);
				}
				this.bindStatus = new BindStatus(context, pathToUse, this.isHtmlEscape());
			}
			return this.bindStatus;
		}
		
		private String getNestedPath(RenderContext context) {
			Object nestedPath = context.map(NESTED_PATH_VARIABLE_NAME);
			if (nestedPath == null || nestedPath.equals(Undefined.UNDEFINED)) {
				return "";
			}
			return (String) nestedPath;
		}
		
		private String getPropertyPath(RenderContext context) {
			String expression = this.getBindStatus(context).getExpression();
			return (expression != null ? expression : "");
		}

		public Object getBoundValue(RenderContext context) {
			return this.getBindStatus(context).getValue();
		}
		
		public boolean isOptionSelected(RenderContext context, Object value) {
			return SelectedValueComparator.isSelected(getBindStatus(context), value);
		}
	}

}
