package com.customweb.jtwig.form.tag.element;

import java.io.ByteArrayOutputStream;

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
import com.customweb.jtwig.lib.attribute.model.definition.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;
import com.google.common.collect.Lists;

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
			Loader.Resource resource = this.retrieveResource(context, "form/select");
			Loader.Resource optionResource = this.retrieveResource(context, "form/option");
			return new Compiled(context.environment().parse(resource).compile(context), context.environment().parse(optionResource).compile(context), super.compile(context),
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
