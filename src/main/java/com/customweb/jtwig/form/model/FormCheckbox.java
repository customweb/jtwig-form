package com.customweb.jtwig.form.model;

import java.io.IOException;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormCheckbox extends AbstractFormCheckedElement<FormCheckbox> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new NamedAttributeDefinition("label", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormCheckedElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}

		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("value")) {
				return this.getAttributeValue("value");
			} else {
				return null;
			}
		}

		public String getLabel() {
			return this.getAttributeValue("label");
		}

		public boolean hasLabel() {
			return this.getAttributeCollection().hasAttribute("label");
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				if (this.hasLabel()) {
					context.write(("<label>").getBytes());
				}
				context.write(("<input id=\"" + this.getId(context) + "\" name=\"" + this.getName() + "\" type=\"checkbox\" "
						+ (this.isDisabled() ? "disabled=\"disabled\" " : "") + this.renderDetails(context, this.getValue())
						+ Utils.concatAttributes(this.getDynamicAttributes()) + " />").getBytes());
				if (this.hasLabel()) {
					context.write((" " + this.escapeHtml(this.getLabel()) + "</label>").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
