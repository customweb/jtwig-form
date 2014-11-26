package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormSelectTag extends AbstractFormMultiElementTag<FormSelectTag> {
	
	public static final String SELECT_ACTIVE_VARIABLE_NAME = FormSelectTag.class.getName() + ".active";
	
	public static final String SELECT_BIND_STATUS_VARIABLE_NAME = FormSelectTag.class.getName() + ".bindStatus";

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("items", false));
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
				context.write(("<select id=\"" + this.getId(context) + "\" name=\"" + this.getName(context) + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + (this.isMultiple() ? " multiple=\"multiple\"" : "") + this.concatDynamicAttributes() + ">").getBytes());
				if (this.hasItems()) {
					for (Object item : this.getItems(context)) {
						context.write(("<option value=\"" + this.escapeHtml(this.getItemValue(item)) + "\""
								+ (this.isOptionSelected(context, this.getItemValue(item)) ? " selected=\"selected\"" : "") + ">"
								+ this.escapeHtml(this.getItemLabel(item)) + "</option>").getBytes());
					}
				} else {
					RenderContext innerContext = context.isolatedModel();
					innerContext.with(SELECT_ACTIVE_VARIABLE_NAME, true);
					innerContext.with(SELECT_BIND_STATUS_VARIABLE_NAME, this.getBindStatus(context));
					this.getContent().render(innerContext);
				}
				context.write(("</select>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
