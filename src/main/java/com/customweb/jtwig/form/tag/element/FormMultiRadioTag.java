package com.customweb.jtwig.form.tag.element;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiRadioTag extends AbstractFormMultiElementTag<FormMultiRadioTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKeys("type", "checked");
		return attributeDefinitions;
	}
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource resource = this.retrieveResource(context, "form/multiradio");
			JtwigResource itemResource = this.retrieveResource(context, "form/radio");
			return new Compiled(context.parse(resource).compile(context), context.parse(itemResource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormMultiElementTag<FormMultiRadioTag>.Compiled {
		private Renderable item;
		
		protected Compiled(Renderable block, Renderable item, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
			this.item = item;
		}
		
		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			List<String> itemsContent = new ArrayList<String>();
			for (Object item : this.getItems(context)) {
				ByteArrayOutputStream itemsRenderStream = new ByteArrayOutputStream();
				RenderContext itemContext = this.isolatedModel(context);
				itemContext.with("radio", new ItemData(item, itemContext, this.getAttributeCollection()));
				this.item.render(itemContext.newRenderContext(itemsRenderStream));
				itemsContent.add(itemsRenderStream.toString());
			}
			
			Data data = new Data(itemsContent, context, this.getAttributeCollection());
			context.with("multiradio", data);
		}
	}
	
	protected class Data extends AbstractFormMultiElementTag<FormMultiRadioTag>.Data {		
		protected Data(List<String> items, RenderContext context, AttributeCollection attributeCollection) {
			super(items, context, attributeCollection);
		}
		
		public List<String> getRadioButtons() {
			return this.getItems();
		}
	}
	
	protected class ItemData extends AbstractFormMultiElementTag<FormMultiRadioTag>.ItemData {
		protected ItemData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(item, context, attributeCollection);
		}
		
		public boolean isChecked() {
			return this.isSelected();
		}
	}
}
