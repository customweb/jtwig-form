package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.model.AbstractTokenGenerator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.form.tag.FormTag;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormTokenTag extends AbstractFormElementTag<FormTokenTag> {
	
	private AbstractTokenGenerator tokenGenerator;
	
	public FormTokenTag(AbstractTokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}
	
	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new EmptyAttributeDefinition("htmlescape"));
		attributeDefinitions.setAllowDynamicAttributes(false);
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection(), tokenGenerator);
	}

	private class Compiled extends AbstractFormElementCompiled {
		private AbstractTokenGenerator tokenGenerator;
		
		protected Compiled(AttributeCollection attributeCollection, AbstractTokenGenerator tokenGenerator) {
			super(null, attributeCollection);
			this.tokenGenerator = tokenGenerator;
		}
		
		public String getFormId(RenderContext context) {
			return (String) context.map(FormTag.FORM_ID_ATTRIBUTE_NAME);
		}
		
		public AbstractTokenGenerator getTokenGenerator() {
			return this.tokenGenerator;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<input type=\"hidden\" name=\"_token\" value=\"" + this.getTokenGenerator().generate(this.getFormId(context)) + "\" />").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
