package com.customweb.jtwig.form.tag.element;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;

import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;

public class FormMultiCheckboxTag extends AbstractFormMultiElementTag<FormMultiCheckboxTag> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKeys("type", "checked");
		return attributeDefinitions;
	}
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			Loader.Resource resource = this.retrieveResource(context, "form/multicheckbox");
			Loader.Resource itemResource = this.retrieveResource(context, "form/checkbox");
			return new Compiled(context.environment().parse(resource).compile(context), context.environment().parse(itemResource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormMultiElementTag<FormMultiCheckboxTag>.Compiled {
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
				itemContext.with("checkbox", new ItemData(item, itemContext, this.getAttributeCollection()));
				this.item.render(itemContext.newRenderContext(itemsRenderStream));
				itemsContent.add(itemsRenderStream.toString());
			}
			
			context.with("multicheckbox", new Data(itemsContent, context, this.getAttributeCollection()));
		}
	}
	
	protected class Data extends AbstractFormMultiElementTag<FormMultiCheckboxTag>.Data {		
		protected Data(List<String> items, RenderContext context, AttributeCollection attributeCollection) {
			super(items, context, attributeCollection);
		}
		
		public List<String> getCheckboxes() {
			return this.getItems();
		}
	}
	
	protected class ItemData extends AbstractFormMultiElementTag<FormMultiCheckboxTag>.ItemData {
		protected ItemData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(item, context, attributeCollection);
		}
		
		public boolean isChecked() {
			return this.isSelected();
		}
	}
}
