package com.customweb.jtwig.form.tag.element;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.customweb.jtwig.form.addon.FormAddon;
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
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("element/errors");
			return new Compiled(context.parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractDataBoundFormElementCompiled {
		private Renderable block;
		
		protected Compiled(Renderable block, AttributeCollection attributeCollection) {
			super(null, attributeCollection);
			this.block = block;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			context = context.isolatedModel();
			context.with("el", new Data(context, this.getAttributeCollection()));
			block.render(context);
		}
	}
	
	public class Data extends AbstractDataBoundFormElementData {
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
