package com.customweb.jtwig.form.model;

import java.io.IOException;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormSelect extends AbstractFormMultiElement<FormSelect> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new EmptyAttributeDefinition("multiple"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormMultiElementCompiled {
		protected Compiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public boolean isMultiple() {
			return this.getAttributeCollection().hasAttribute("multiple");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<select id=\"" + this.getId(context) + "\" name=\"" + this.getName() + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + (this.isMultiple() ? " multiple=\"multiple\"" : "") + ">").getBytes());
				if (this.hasItems()) {
					for (Object item : this.getItems(context)) {
						context.write(("<option value=\"" + this.escapeHtml(this.getItemValue(item)) + "\""
								+ (SelectedValueComparator.isSelected(context, this.getItemValue(item)) ? " selected=\"selected\"" : "") + ">"
								+ this.escapeHtml(this.getItemLabel(item)) + "</option>").getBytes());
					}
				} else {
					RenderContext innerContext = context.isolatedModel();
					innerContext.with("isSelectStarted", true);
					innerContext.with("selectActualValue", this.getDataValue(context, this.getPath()));
					this.getContent().render(innerContext);
				}
				context.write(("</select>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
