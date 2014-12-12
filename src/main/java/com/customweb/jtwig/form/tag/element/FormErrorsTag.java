package com.customweb.jtwig.form.tag.element;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.customweb.jtwig.form.tag.AbstractDataBoundFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormErrorsTag extends AbstractDataBoundFormElementTag<FormErrorsTag> {

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		this.getAttributeCollection().compile(context);
		try {
			JtwigResource resource = this.retrieveResource(context, "form/errors");
			return new Compiled(context.parse(resource).compile(context), this.getAttributeCollection());
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
