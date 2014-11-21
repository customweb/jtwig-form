package com.customweb.jtwig.form.model;

import java.io.IOException;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormLabel extends AbstractFormElement<FormLabel> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("path", true));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("for");
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
		
		public String getPath() {
			return this.getAttributeValue("path");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<label for=\"" + this.getPath() + "\""
						+ Utils.concatAttributes(this.getDynamicAttributes()) + ">")
						.getBytes());
				this.getContent().render(context);
				context.write("</label>".getBytes());
			} catch (IOException e) {
			}
		}
	}

}
