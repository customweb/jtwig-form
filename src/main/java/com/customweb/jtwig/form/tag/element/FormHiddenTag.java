package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.tag.AbstractDataBoundFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormHiddenTag extends AbstractDataBoundFormElementTag<FormHiddenTag> {
	
	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("type");
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

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<input id=\"" + this.getId(context) + "\" name=\"" + this.getName(context) + "\" type=\"hidden\" value=\""
						+ this.getBoundDisplayValue(context) + "\""
						+ this.concatDynamicAttributes() + " />").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
