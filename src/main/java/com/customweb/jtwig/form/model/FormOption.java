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
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormElementCompiled {
		protected Compiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public void checkSelect(RenderContext context) {
			if (!context.map("isSelectStarted").equals(Boolean.TRUE)) {
				throw new RuntimeException("The 'option' tag can only be used inside a valid 'select' tag.");
			}
		}

		public String getValue() {
			return this.getAttributeValue("value");
		}
		
		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			this.checkSelect(context);

			try {
				context.write(("<option value=\"" + this.escapeHtml(this.getValue()) + "\"" + (this.isDisabled() ? " disabled=\"disabled\"" : "")
						+ (SelectedValueComparator.isSelected(context.map("selectActualValue"), this.getValue()) ? " selected=\"selected\"" : "")
						+ Utils.concatAttributes(this.getDynamicAttributes()) + ">").getBytes());
				this.getContent().render(context);
				context.write(("</option>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
