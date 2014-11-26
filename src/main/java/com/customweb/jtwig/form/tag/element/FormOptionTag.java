package com.customweb.jtwig.form.tag.element;

import java.io.IOException;

import com.customweb.jtwig.form.model.BindStatus;
import com.customweb.jtwig.form.model.SelectedValueComparator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

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
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormElementCompiled {
		protected Compiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public boolean isSelectActive(RenderContext context) {
			return context.map(FormSelectTag.SELECT_ACTIVE_VARIABLE_NAME).equals(Boolean.TRUE);
		}

		public String getValue() {
			return this.getAttributeValue("value");
		}
		
		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}
		
		public BindStatus getBindStatus(RenderContext context) {
			return (BindStatus) context.map(FormSelectTag.SELECT_BIND_STATUS_VARIABLE_NAME);
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			if (!this.isSelectActive(context)) {
				throw new RuntimeException("The 'multioption' tag can only be used inside a valid 'select' tag.");
			}

			try {
				context.write(("<option value=\"" + this.escapeHtml(this.getValue()) + "\"" + (this.isDisabled() ? " disabled=\"disabled\"" : "")
						+ (SelectedValueComparator.isSelected(this.getBindStatus(context), this.getValue()) ? " selected=\"selected\"" : "")
						+ this.concatDynamicAttributes() + ">").getBytes());
				this.getContent().render(context);
				context.write(("</option>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
