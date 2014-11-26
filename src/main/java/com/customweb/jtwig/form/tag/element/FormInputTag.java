package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.tag.AbstractFormInputElementTag;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormInputTag extends AbstractFormInputElementTag<FormInputTag> {

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
				context.write(("<input id=\"" + this.getId(context) + "\" name=\"" + this.getName(context) + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + " type=\"text\" value=\""
						+ this.getBoundValue(context) + "\""
						+ this.concatDynamicAttributes() + " />").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
