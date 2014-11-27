package com.customweb.jtwig.form.tag.element;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.SelectedValueComparator;
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
			JtwigResource resource = FormAddon.getResourceHandler().resolve("element/multiradio");
			JtwigResource itemResource = FormAddon.getResourceHandler().resolve("element/radio");
			return new Compiled(context.parse(resource).compile(context), context.parse(itemResource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormMultiElementCompiled {
		private Renderable block;
		private Renderable item;
		
		protected Compiled(Renderable block, Renderable item, Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
			this.block = block;
			this.item = item;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			List<String> itemsContent = new ArrayList<String>();
			for (Object item : this.getItems(context)) {
				ByteArrayOutputStream itemsRenderStream = new ByteArrayOutputStream();
				context = context.isolatedModel();
				context.with("el", new ItemData(item, context, this.getAttributeCollection()));
				this.item.render(context.newRenderContext(itemsRenderStream));
				itemsContent.add(itemsRenderStream.toString());
			}
			
			Data data = new Data(itemsContent, context, this.getAttributeCollection());
			context = context.isolatedModel();
			context.with("el", data);
			block.render(context);
		}
	}
	
	protected class Data extends AbstractFormMultiElementData {		
		protected Data(List<String> items, RenderContext context, AttributeCollection attributeCollection) {
			super(items, context, attributeCollection);
		}
		
		public List<String> getRadioButtons() {
			return this.getItems();
		}
	}
	
	protected class ItemData extends AbstractFormMultiElementItemData {
		protected ItemData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(item, context, attributeCollection);
		}
		
		public boolean isChecked() {
			return SelectedValueComparator.isSelected(this.getBindStatus(), this.getValue());
		}
	}
}
