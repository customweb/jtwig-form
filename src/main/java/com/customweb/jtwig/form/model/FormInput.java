package com.customweb.jtwig.form.model;

import java.io.IOException;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormInput extends AbstractFormInputElement<FormInput> {

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormInputElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<input id=\"" + this.getId(context) + "\" name=\"" + this.getName() + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + " type=\"text\" value=\""
						+ this.escapeHtml(this.getDataValue(context, this.getPath())) + "\""
						+ Utils.concatAttributes(this.getDynamicAttributes()) + " />").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
