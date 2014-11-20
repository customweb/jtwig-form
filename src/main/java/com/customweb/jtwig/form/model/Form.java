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
import com.customweb.jtwig.lib.model.VariableAttribute;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class Form extends AttributeModel<Form> {

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		List<AttributeDefinition> definitions = new ArrayList<AttributeDefinition>();
		definitions.add(new VariableAttributeDefinition("dataobject", true));
		definitions.add(new DynamicAttributeDefinition());
		return definitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled implements Renderable {
		private final Renderable content;
		private final AttributeCollection<Form> attributeCollection;

		private Compiled(Renderable content, AttributeCollection<Form> attributeCollection) {
			this.content = content;
			this.attributeCollection = attributeCollection;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			// Prevent access on grid variables outside
			context = context.isolatedModel();
			context.with("formDataObject", this.attributeCollection.getAttribute("dataobject", VariableAttribute.class)
					.getVariable(context));

			try {
				context.write(("<form" + Util.concatAttributes(this.attributeCollection.getAttributes(DynamicAttribute.class)) + ">").getBytes());
				this.content.render(context);
				context.write("</form>".getBytes());
			} catch (IOException e) {
			}
		}
	}

}
