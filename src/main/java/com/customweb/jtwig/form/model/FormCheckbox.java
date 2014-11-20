package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.List;

import com.customweb.jtwig.form.Util;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinition;
import com.customweb.jtwig.lib.model.DynamicAttribute;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormCheckbox extends FormElement<FormCheckbox> {

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		List<AttributeDefinition> definitions = super.getAttributeDefinitions();
		definitions.add(0, new NamedAttributeDefinition("value", true));
		return definitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends CompiledFormElement {
		protected Compiled(Renderable content, AttributeCollection<FormCheckbox> attributeCollection) {
			super(content, attributeCollection);
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			String path = this.getAttributeCollection().getAttribute("path").getValue();
			String value = this.getAttributeCollection().getAttribute("value").getValue();
			boolean checked = value.equals(this.getFormDataValue(context, path));

			try {
				this.renderLabel(context);
				context.write(("<input type=\"checkbox\" name=\"" + path + "\" id=\"" + path + "\" " + (checked ? "checked=\"checked\" " : "")
						+ Util.concatAttributes(this.getAttributeCollection().getAttributes(DynamicAttribute.class)) + "/>").getBytes());
			} catch (IOException e) {}
		}
	}
}
