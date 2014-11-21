package com.customweb.jtwig.form.model;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

abstract public class AbstractFormCheckedElement<T extends AbstractFormCheckedElement<T>> extends
		AbstractDataBoundFormElement<T> {

	protected abstract class AbstractFormCheckedElementCompiled extends AbstractDataBoundFormElementCompiled {
		protected AbstractFormCheckedElementCompiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public boolean isOptionSelected(RenderContext context, String value) {
			return SelectedValueComparator.isSelected(this.geDataValue(context, this.getPath()), value);
		}
	}

}
