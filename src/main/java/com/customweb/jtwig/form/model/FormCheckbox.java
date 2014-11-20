package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import com.customweb.jtwig.form.Util;
import com.customweb.jtwig.lib.model.Attribute;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinition;
import com.customweb.jtwig.lib.model.DynamicAttribute;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormCheckbox extends FormElement<FormCheckbox> {

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		List<AttributeDefinition> definitions = super.getAttributeDefinitions();
		return definitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends CompiledFormElement {
		private final AttributeCollection<FormCheckbox> attributeCollection;

		private Compiled(Renderable content, AttributeCollection<FormCheckbox> attributeCollection) {
			this.attributeCollection = attributeCollection;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			String path = this.attributeCollection.getAttribute("path").getValue();
			String label = this.attributeCollection.getAttribute("label").getValue();

			try {
				context.write(("<input type=\"checkbox\" name=\"" + path + "\" id=\"" + path + "\" value=\"" + this.getFormDataValue(context, path) + "\" "
						+ Util.concatAttributes(this.attributeCollection.getAttributes(DynamicAttribute.class)) + "/>").getBytes());
				// this.content.render(context);
			} catch (IOException e) {
			}
		}
	}
}
