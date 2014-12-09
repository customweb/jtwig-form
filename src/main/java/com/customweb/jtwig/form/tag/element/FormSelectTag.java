package com.customweb.jtwig.form.tag.element;

import java.io.ByteArrayOutputStream;

import com.customweb.jtwig.form.addon.FormAddon;
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

	public static final String SELECT_CONTEXT_VARIABLE_NAME = FormSelectTag.class.getName() + ".context";

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
			JtwigResource resource = FormAddon.getResourceHandler().resolve("select");
			JtwigResource optionResource = FormAddon.getResourceHandler().resolve("option");
			return new Compiled(context.parse(resource).compile(context), context.parse(optionResource).compile(context), super.compile(context),
					this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormMultiElementTag<FormSelectTag>.Compiled {
		private Renderable option;

		protected Compiled(Renderable block, Renderable option, Renderable content, AttributeCollection attributeCollection) {
			super(block, content, attributeCollection);
			this.option = option;
		}

		@Override
		public void prepareContext(RenderContext context) throws RenderException {
			String content;
			if (this.hasItems()) {
				ByteArrayOutputStream optionRenderStream = new ByteArrayOutputStream();
				for (Object item : this.getItems(context)) {
					RenderContext itemContext = this.isolatedModel(context);
					itemContext.with("option", new OptionData(item, itemContext, this.getAttributeCollection()));
					this.option.render(itemContext.newRenderContext(optionRenderStream));
				}
				content = optionRenderStream.toString();
			} else {
				RenderContext contentContext = this.isolatedModel(context);
				contentContext.with(SELECT_CONTEXT_VARIABLE_NAME, true);
				contentContext.with(SELECT_BIND_STATUS_VARIABLE_NAME, new Data("", contentContext, this.getAttributeCollection()).getBindStatus());
				content = this.renderContentAsString(contentContext);
			}

			context.with("select", new Data(content, context, this.getAttributeCollection()));
		}
	}

	protected class Data extends AbstractFormMultiElementTag<FormSelectTag>.Data {
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

	protected class OptionData extends AbstractFormMultiElementTag<FormSelectTag>.ItemData {
		protected OptionData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(item, context, attributeCollection);
		}
	}
}
