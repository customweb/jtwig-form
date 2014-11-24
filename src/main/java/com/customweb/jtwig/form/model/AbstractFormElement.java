package com.customweb.jtwig.form.model;

import org.apache.commons.lang.StringEscapeUtils;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.AttributeModel;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;

abstract public class AbstractFormElement<T extends AbstractFormElement<T>> extends AttributeModel<T> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new EmptyAttributeDefinition("htmlescape"));
		return attributeDefinitions;
	}

	abstract protected class AbstractFormElementCompiled extends AbstractAttributeModelCompiled {
		protected AbstractFormElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public boolean isHtmlEscape() {
			return this.getAttributeCollection().hasAttribute("htmlescape");
		}

		public String escapeHtml(Object input) {
			String inputString = Utils.nullSafeToString(input);
			if (this.isHtmlEscape()) {
				return StringEscapeUtils.escapeHtml(inputString);
			} else {
				return inputString;
			}
		}
	}

}
