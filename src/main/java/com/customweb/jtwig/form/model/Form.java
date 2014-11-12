package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lyncode.jtwig.addons.AddonModel;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.expressions.api.CompilableExpression;
import com.lyncode.jtwig.expressions.model.Constant;
import com.lyncode.jtwig.render.RenderContext;

public class Form extends AddonModel<Form> {
	
	Map<String, String> attributes = new HashMap<String, String>();

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.attributes);
	}
	
	public Form addAttribute(CompilableExpression key, CompilableExpression value) {
		attributes.put((String) ((Constant) key).getValue(), (String) ((Constant) value).getValue());
		return this;
	}

	private class Compiled implements Renderable {
		private final Renderable content;
		private Map<String, String> attributes;

		private Compiled(Renderable content, Map<String, String> attributes) {
			this.content = content;
			this.attributes = attributes;
		}

		@Override
		public void render(RenderContext context) throws RenderException {

			// Prevent access on grid variables outside
			context = context.isolatedModel();
			context.with("other", "sample");
			
			StringBuilder builder = new StringBuilder();
			for (Entry<String, String> attribute : attributes.entrySet()) {
				builder.append(attribute.getKey()).append("=").append(attribute.getValue()).append(" ");
			}
			
			try {
				context.write(("<form name=\"" + builder.toString() + "\">").getBytes());
				this.content.render(context);
				context.write("</form>".getBytes());
			} catch (IOException e) {
			}

		}
	}

}
