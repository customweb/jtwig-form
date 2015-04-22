package com.customweb.jtwig.form.tag.element;

import java.util.List;

import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;
import org.springframework.validation.ObjectError;

import com.customweb.jtwig.form.tag.AbstractDataBoundFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;

public class FormErrorsTag extends AbstractDataBoundFormElementTag<FormErrorsTag> {

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		this.getAttributeCollection().compile(context);
		try {
			Loader.Resource resource = this.retrieveResource(context, "form/errors");
			return new Compiled(context.environment().parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractDataBoundFormElementTag<FormErrorsTag>.Compiled {
		protected Compiled(Renderable block, AttributeCollection attributeCollection) {
			super(block, null, attributeCollection);
		}

		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			context.with("errors", new Data(context, this.getAttributeCollection()));
		}
	}
	
	public class Data extends AbstractDataBoundFormElementTag<FormErrorsTag>.Data {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		public boolean hasErrors() {
			return this.getBindStatus().isError();
		}
		
		public List<String> getErrorMessages() {
			return this.getBindStatus().getErrorMessages();
		}
		
		public List<String> getErrorCodes() {
			return this.getBindStatus().getErrorCodes();
		}
		
		public  List<? extends ObjectError> getErrors() {
			return this.getBindStatus().getErrors();
		}
	}
}
