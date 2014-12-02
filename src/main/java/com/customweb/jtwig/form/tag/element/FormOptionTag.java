package com.customweb.jtwig.form.tag.element;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.BindStatus;
import com.customweb.jtwig.form.model.SelectedValueComparator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormOptionTag extends AbstractFormElementTag<FormOptionTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("value", true));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("selected");
		return attributeDefinitions;
	}
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("element/option");
			return new Compiled(context.parse(resource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormElementTag<FormOptionTag>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}
		
		public boolean isInSelectContext(RenderContext context) {
			return context.map(FormSelectTag.SELECT_CONTEXT_VARIABLE_NAME).equals(Boolean.TRUE);
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			if (!this.isInSelectContext(context)) {
				throw new RuntimeException("The 'option' tag can only be used inside a valid 'select' tag.");
			}
			
			context = this.isolatedModel(context);
			context.with("option", new Data(this.renderContentAsString(context), context, this.getAttributeCollection()));
			this.getBlock().render(context);
		}
	}
	
	protected class Data extends AbstractFormElementTag<FormOptionTag>.Data {
		private String label;
		
		protected Data(String label, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.label = label;
		}
		
		public String getValue() {
			return this.getAttributeValue("value");
		}
		
		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}
		
		public boolean isSelected() {
			return SelectedValueComparator.isSelected(this.getBindStatus(), this.getValue());
		}
		
		public BindStatus getBindStatus() {
			return (BindStatus) this.getContext().map(FormSelectTag.SELECT_BIND_STATUS_VARIABLE_NAME);
		}
		
		public String getLabel() {
			return this.label;
		}
	}
}
