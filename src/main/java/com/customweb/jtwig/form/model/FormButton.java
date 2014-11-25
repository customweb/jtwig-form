package com.customweb.jtwig.form.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormButton extends AbstractFormElement<FormButton> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("name", false));
		attributeDefinitions.add(new NamedAttributeDefinition("value", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
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

		public String getId(RenderContext context) {
			String id = StringUtils.remove(StringUtils.remove(this.getName(), '['), ']');
			if (this.getAttributeCollection().hasAttribute("id")) {
				String value = this.getAttributeValue("id");
				if (value != null && !value.isEmpty()) {
					id = value;
				}
			}
			return id;
		}

		public boolean hasId() {
			return this.getAttributeCollection().hasAttribute("id") || this.hasName();
		}

		public String getName() {
			return this.getAttributeValue("name");
		}

		public boolean hasName() {
			return this.getAttributeCollection().hasAttribute("name");
		}

		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("value")) {
				return this.getAttributeValue("value");
			} else {
				return "Submit";
			}
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<button" + (this.hasId() ? " id=\"" + this.getId(context) + "\"" : "")
						+ (this.hasName() ? " name=\"" + this.getName() + "\"" : "") + " type=\"submit\" value=\"" + this.getValue() + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + this.concatDynamicAttributes() + ">").getBytes());
				this.getContent().render(context);
				context.write("</button>".getBytes());
			} catch (IOException e) {
			}
		}
	}

}
