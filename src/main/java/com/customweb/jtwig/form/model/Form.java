package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.Collection;

import com.customweb.jtwig.lib.model.AttributeModel;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class Form extends AttributeModel<Form> {
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this);
	}
	
	private class Compiled implements Renderable {
		private final Renderable content;
		private final Form form;

		private Compiled(Renderable content, Form form) {
			this.content = content;
			this.form = form;
		}

		@Override
		public void render(RenderContext context) throws RenderException {

			// Prevent access on grid variables outside
			context = context.isolatedModel();
			context.with("other", "sample");
			
			StringBuilder builder = new StringBuilder();
			for (Attribute attribute : this.form.getDynamicAttributes()) {
				builder.append(" ").append(attribute.toString());
			}
			
			VariableAttribute dataObjectAttribute = (VariableAttribute) this.form.getAttribute(Attributes.DATAOBJECT.name());
			if (dataObjectAttribute == null) {
				throw new IllegalArgumentException("The data object attribute is mandatory.");
			}
						
			try {
				context.write(("<form" + builder.toString() + ">").getBytes());
				this.content.render(context);
				context.write("</form>".getBytes());
			} catch (IOException e) {
			}

		}
	}
	
	public enum Attributes {
		DATAOBJECT
	}

}
