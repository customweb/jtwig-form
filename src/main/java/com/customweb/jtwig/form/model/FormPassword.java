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

public class FormPassword extends AbstractFormInputElement<FormPassword> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new EmptyAttributeDefinition("showpassword"));
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

		public boolean isShowPassword() {
			return this.getAttributeCollection().hasAttribute("showpassword");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<input id=\"" + this.getId(context) + "\" name=\"" + this.getName(context) + "\" type=\"password\" value=\""
						+ (this.isShowPassword() ? this.getBoundValue(context) : "") + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + this.concatDynamicAttributes() + " />")
						.getBytes());
			} catch (IOException e) {
			}
		}
	}
}
