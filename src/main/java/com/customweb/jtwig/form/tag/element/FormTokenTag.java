package com.customweb.jtwig.form.tag.element;

import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;

import com.customweb.jtwig.form.model.AbstractTokenGenerator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.form.tag.FormTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;

public class FormTokenTag extends AbstractFormElementTag<FormTokenTag> {
	
	private AbstractTokenGenerator tokenGenerator;
	
	public FormTokenTag(AbstractTokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		this.getAttributeCollection().compile(context);
		try {
			Loader.Resource resource = this.retrieveResource(context, "form/token");
			return new Compiled(tokenGenerator, context.environment().parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormElementTag<FormTokenTag>.Compiled {
		private AbstractTokenGenerator tokenGenerator;
		
		protected Compiled(AbstractTokenGenerator tokenGenerator, Renderable block, AttributeCollection attributeCollection) {
			super(block, null, attributeCollection);
			if (tokenGenerator == null) {
				throw new RuntimeException("The token generator class has not been defined.");
			}
			this.tokenGenerator = tokenGenerator;
		}
		
		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			context.with("token", new Data(tokenGenerator, context, this.getAttributeCollection()));
		}
	}
	
	protected class Data extends AbstractFormElementTag<FormTokenTag>.Data {
		private AbstractTokenGenerator tokenGenerator;
		
		protected Data(AbstractTokenGenerator tokenGenerator, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.tokenGenerator = tokenGenerator;
		}
		
		protected String getFormId() {
			return (String) getContext().map(FormTag.FORM_ID_ATTRIBUTE_NAME);
		}
		
		protected AbstractTokenGenerator getTokenGenerator() {
			return this.tokenGenerator;
		}
		
		public String getToken() {
			return this.getTokenGenerator().generate(this.getFormId());
		}
	}
}
