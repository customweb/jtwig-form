package com.customweb.jtwig.form.model;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormCheckedElement<T extends AbstractFormCheckedElement<T>> extends AbstractDataBoundFormElement<T> {

	protected abstract class AbstractFormCheckedElementCompiled extends AbstractDataBoundFormElementCompiled {
		protected AbstractFormCheckedElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}
		
		@Override
		public String getId(RenderContext context) {
			return Utils.nextId(super.getId(context), context);
		}
		
		public String renderDetails(RenderContext context, String value) {
			Object actualValue = this.getDataValue(context, this.getPath());
			boolean checked;
			if (Boolean.class.equals(actualValue.getClass()) || boolean.class.equals(actualValue.getClass())) {
				if (actualValue instanceof String) {
					actualValue = Boolean.valueOf((String) actualValue);
				}
				checked = (actualValue != null ? (Boolean) actualValue : Boolean.FALSE);
				value = "true";
			} else {
				if (value == null) {
					throw new RuntimeException("Attribute 'value' is required when binding to non-boolean values");
				}
				checked = this.isOptionSelected(context, value);
			}
			return "value=\"" + this.escapeHtml(value) + "\"" + (checked ? " checked=\"checked\"" : "");
		}

		public boolean isOptionSelected(RenderContext context, String candidateValue) {
			return SelectedValueComparator.isSelected(this.getDataValue(context, this.getPath()), candidateValue);
		}
	}

}
