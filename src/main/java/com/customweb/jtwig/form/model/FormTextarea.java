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

public class FormTextarea extends AbstractDataBoundFormElement<FormTextarea> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("cols", false));
		attributeDefinitions.add(new NamedAttributeDefinition("rows", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractDataBoundFormElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}

		public String getCols() {
			if (this.getAttributeCollection().hasAttribute("cols")) {
				return this.getAttributeValue("cols");
			}
			return "1";
		}

		public String getRows() {
			if (this.getAttributeCollection().hasAttribute("rows")) {
				return this.getAttributeValue("rows");
			}
			return "1";
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<textarea name=\"" + this.getName(context) + "\" id=\"" + this.getId(context) + "\" cols=\"" + this.getCols() + "\""
						+ "rows=\"" + this.getRows() + "\"" + (this.isDisabled() ? " disabled=\"disabled\"" : "")
						+ Utils.concatAttributes(this.getDynamicAttributes()) + ">"
						+ this.escapeHtml(this.getDataValue(context, this.getPath()).toString()) + "</textarea>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
