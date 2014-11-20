package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.customweb.jtwig.form.Util;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinition;
import com.customweb.jtwig.lib.model.AttributeModel;
import com.customweb.jtwig.lib.model.DynamicAttribute;
import com.customweb.jtwig.lib.model.DynamicAttributeDefinition;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormLabel extends AttributeModel<FormLabel> {

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		List<AttributeDefinition> definitions = new ArrayList<AttributeDefinition>();
		definitions.add(new VariableAttributeDefinition("path", true));
		definitions.add(new DynamicAttributeDefinition());
		return definitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled implements Renderable {
		private final Renderable content;
		private final AttributeCollection<FormLabel> attributeCollection;

		private Compiled(Renderable content, AttributeCollection<FormLabel> attributeCollection) {
			this.content = content;
			this.attributeCollection = attributeCollection;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			String path = this.attributeCollection.getAttribute("path").getValue();
			
			try {
				context.write(("<label for=\"" + path + "\"" + Util.concatAttributes(this.attributeCollection.getAttributes(DynamicAttribute.class)) + ">").getBytes());
				this.content.render(context);
				context.write("</label>".getBytes());
			} catch (IOException e) {}
		}
	}

}
