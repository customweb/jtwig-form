package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.model.AbstractTokenGenerator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.form.tag.FormTag;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.VariableAttribute;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormTokenTag extends AbstractFormElementTag<FormTokenTag> {
	
	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("generator", true));
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
		
		public String getFormId(RenderContext context) {
			return (String) context.map(FormTag.FORM_ID_ATTRIBUTE_NAME);
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			AbstractTokenGenerator generator = this.getAttributeCollection().getAttribute("generator", VariableAttribute.class).getVariable(context, AbstractTokenGenerator.class);
			try {
				context.write(("<input type=\"hidden\" name=\"_token\" value=\"" + generator.generate(this.getFormId(context)) + "\" />").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
