package com.customweb.jtwig.form.tag.element;

import com.customweb.jtwig.form.tag.AbstractFormElementTag;
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

public class FormLabelTag extends AbstractFormElementTag<FormLabelTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("path", true));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("for");
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource resource = this.retrieveResource(context, "form/label");
			return new Compiled(context.parse(resource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormElementTag<FormLabelTag>.Compiled {
		protected Compiled(Renderable block, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
		}

		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			context.with("label", new Data(this.renderContentAsString(context), context, this.getAttributeCollection()));
		}
	}
	
	protected class Data extends AbstractFormElementTag<FormLabelTag>.Data {
		private String label;
		
		protected Data(String label, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.label = label;
		}
		
		public String getPath() {
			return this.getAttributeValue("path");
		}
		
		public String getLabel() {
			return this.label;
		}
	}

}
