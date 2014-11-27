package com.customweb.jtwig.form.tag.element;

import java.io.ByteArrayOutputStream;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.SelectedValueComparator;
import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;
import com.google.common.collect.Lists;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormSelectTag extends AbstractFormMultiElementTag<FormSelectTag> {
	
	public static final String SELECT_ACTIVE_VARIABLE_NAME = FormSelectTag.class.getName() + ".active";
	
	public static final String SELECT_BIND_STATUS_VARIABLE_NAME = FormSelectTag.class.getName() + ".bindStatus";

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("items", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("multiple"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("element/select");
			JtwigResource optionResource = FormAddon.getResourceHandler().resolve("element/option");
			return new Compiled(context.parse(resource).compile(context), context.parse(optionResource).compile(context), super.compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormMultiElementCompiled {
		private Renderable block;
		private Renderable option;
		
		protected Compiled(Renderable block, Renderable option, Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
			this.block = block;
			this.option = option;
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			context = context.isolatedModel();
			
			String content;
			if (this.hasItems()) {
				ByteArrayOutputStream optionRenderStream = new ByteArrayOutputStream();
				for (Object item : this.getItems(context)) {
					context = context.isolatedModel();
					context.with("option", new OptionData(item, context, this.getAttributeCollection()));
					this.option.render(context.newRenderContext(optionRenderStream));
				}
				content = optionRenderStream.toString();
			} else {
				context.with(SELECT_ACTIVE_VARIABLE_NAME, true);
				context.with(SELECT_BIND_STATUS_VARIABLE_NAME, new Data("", context, this.getAttributeCollection()).getBindStatus());
				content = this.renderContentAsString(context);
			}
			
			context.with("el", new Data(content, context, this.getAttributeCollection()));
			block.render(context);
		}
	}
	
	protected class Data extends AbstractFormMultiElementData {
		protected Data(String options, RenderContext context, AttributeCollection attributeCollection) {
			super(Lists.newArrayList(options), context, attributeCollection);
		}
		
		public boolean isMultiple() {
			return this.getAttributeCollection().hasAttribute("multiple");
		}
		
		public String getOptions() {
			return this.getItems().get(0);
		}
	}
	
	protected class OptionData extends AbstractFormMultiElementItemData {
		protected OptionData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(item, context, attributeCollection);
		}
		
		public boolean isSelected() {
			return SelectedValueComparator.isSelected(this.getBindStatus(), this.getValue());
		}
	}
}
