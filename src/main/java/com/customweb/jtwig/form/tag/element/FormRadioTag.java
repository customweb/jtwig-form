package com.customweb.jtwig.form.tag.element;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.IdGenerator;
import com.customweb.jtwig.form.tag.AbstractFormInputElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormRadioTag extends AbstractFormInputElementTag<FormRadioTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new NamedAttributeDefinition("label", false));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKeys("type", "checked");
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("element/radio");
			return new Compiled(context.parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormInputElementCompiled {
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
	
	protected class Data extends AbstractFormInputElementData {
		protected Data(RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
		}
		
		@Override
		public String getId() {
			return IdGenerator.nextId(super.getId(), this.getContext());
		}
		
		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("value")) {
				return this.getAttributeValue("value");
			} else {
				return "";
			}
		}
		
		public String getLabel() {
			if (!this.getAttributeCollection().hasAttribute("label")) {
				return null;
			}
			return this.getAttributeValue("label");
		}
		
		public boolean isChecked() {
			return this.isOptionSelected(this.getValue());
		}
	}
}
