package com.customweb.jtwig.form.model;

import java.io.IOException;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.AttributeModel;
import com.customweb.jtwig.lib.model.VariableAttribute;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class Form extends AttributeModel<Form> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("dataobject", false));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends AbstractAttributeModelCompiled {
		protected Compiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			context = context.isolatedModel();
			if (this.getAttributeCollection().hasAttribute("dataobject")) {
				context.with(
						"formDataObject",
						this.getAttributeCollection().getAttribute("dataobject", VariableAttribute.class).getVariable(
								context));
				context.with("formDataObjectVariable",
						this.getAttributeValue("dataobject"));
			}

			try {
				context.write(("<form"
						+ Utils.concatAttributes(this.getDynamicAttributes()) + ">")
						.getBytes());
				this.getContent().render(context);
				context.write("</form>".getBytes());
			} catch (IOException e) {
			}
		}
	}

}
