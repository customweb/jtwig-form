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

public class FormOption extends AbstractFormElement<FormOption> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("value", true));
		attributeDefinitions.add(new NamedAttributeDefinition("label", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}

		public String getValue() {
			return this.getAttributeValue("value");
		}

		public String getLabel() {
			if (this.getAttributeCollection().hasAttribute("label")) {
				return this.getAttributeValue("label");
			}
			return this.getValue();
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<option value=\"" + this.escapeHtml(this.getValue()) + "\"" + (this.isDisabled() ? " disabled=\"disabled\"" : "")
						+ Utils.concatAttributes(this.getDynamicAttributes()) + ">" + this.escapeHtml(this.getLabel()) + "</option>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
