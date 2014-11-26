package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.tag.AbstractDataBoundFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormErrorsTag extends AbstractDataBoundFormElementTag<FormErrorsTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("element", false));
		attributeDefinitions.add(new NamedAttributeDefinition("delimiter", false));
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
		public String getId(RenderContext context) {
			return super.getId(context) + ".errors";
		}

		public String getElement() {
			if (this.getAttributeCollection().hasAttribute("element")) {
				return this.getAttributeValue("element");
			}
			return "span";
		}

		public String getDelimiter() {
			if (this.getAttributeCollection().hasAttribute("delimiter")) {
				return this.getAttributeValue("delimiter");
			}
			return "<br>";
		} 

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				if (this.getBindStatus(context).isError()) {
					context.write(("<" + this.getElement() + " id=\"" + this.getId(context) + "\"" + this.concatDynamicAttributes() + ">").getBytes());
					context.write(this.getBindStatus(context).getErrorMessagesAsString(this.getDelimiter()).getBytes());
					context.write(("</" + this.getElement() + ">").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
