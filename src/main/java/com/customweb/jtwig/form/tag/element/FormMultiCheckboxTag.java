package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.model.IdGenerator;
import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormMultiCheckboxTag extends AbstractFormMultiElementTag<FormMultiCheckboxTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("element", false));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKeys("type", "checked");
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormMultiElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}

		@Override
		public String getId(RenderContext context) {
			return IdGenerator.nextId(super.getId(context), context);
		}

		public String getElement() {
			if (this.getAttributeCollection().hasAttribute("element")) {
				return this.getAttributeValue("element");
			}
			return "span";
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				for (Object item : this.getItems(context)) {
					String itemId = this.getId(context);
					context.write(("<" + this.getElement() + ">").getBytes());
					context.write(("<label for=\"" + itemId + "\">").getBytes());
					context.write(("<input id=\"" + itemId + "\" name=\"" + this.getName(context) + "\" "
							+ (this.isDisabled() ? "disabled=\"disabled\" " : "") + "type=\"checkbox\" value=\""
							+ this.escapeHtml(this.getItemValue(item)) + "\" "
							+ (this.isOptionSelected(context, this.getItemValue(item)) ? "checked=\"checked\" " : "")
							+ this.concatDynamicAttributes() + "/>").getBytes());
					context.write((" " + this.escapeHtml(this.getItemLabel(item)) + "</label>").getBytes());
					context.write(("</" + this.getElement() + ">").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
