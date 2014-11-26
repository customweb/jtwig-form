package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.model.IdGenerator;
import com.customweb.jtwig.form.tag.AbstractFormInputElementTag;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormRadioTag extends AbstractFormInputElementTag<FormRadioTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new NamedAttributeDefinition("label", false));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormInputElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}
		
		@Override
		public String getId(RenderContext context) {
			return IdGenerator.nextId(super.getId(context), context);
		}

		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("value")) {
				return this.getAttributeValue("value");
			} else {
				return "";
			}
		}

		public String getLabel() {
			return this.getAttributeValue("label");
		}

		public boolean hasLabel() {
			return this.getAttributeCollection().hasAttribute("label");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				if (this.hasLabel()) {
					context.write(("<label>").getBytes());
				}
				context.write(("<input id=\"" + this.getId(context) + "\" name=\"" + this.getName(context) + "\" type=\"radio\" value=\""
						+ this.escapeHtml(this.getValue()) + "\"" + (this.isOptionSelected(context, this.getValue()) ? " checked=\"checked\"" : "")
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + this.concatDynamicAttributes() + " />")
						.getBytes());
				if (this.hasLabel()) {
					context.write((" " + this.escapeHtml(this.getLabel()) + "</label>").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
