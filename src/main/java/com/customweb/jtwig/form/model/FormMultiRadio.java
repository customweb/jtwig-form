package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.EmptyAttributeDefinition;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.customweb.jtwig.lib.model.VariableAttribute;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormMultiRadio extends AbstractFormCheckedElement<FormMultiRadio> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new NamedAttributeDefinition("element", false));
		attributeDefinitions.add(new VariableAttributeDefinition("items", true));
		attributeDefinitions.add(new NamedAttributeDefinition("itemLabel", false));
		attributeDefinitions.add(new NamedAttributeDefinition("itemValue", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormCheckedElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}

		public String getElement() {
			if (this.getAttributeCollection().hasAttribute("element")) {
				return this.getAttributeValue("element");
			}
			return "span";
		}

		public Collection<?> getItems(RenderContext context) {
			Object items = this.getAttributeCollection().getAttribute("items", VariableAttribute.class).getVariable(context);
			if (items instanceof Collection) {
				return (Collection<?>) items;
			} else if (items.getClass().isArray()) {
				return Arrays.asList((Object[]) items);
			}
			throw new RuntimeException("The 'items' attribute value has to be a collection.");
		}

		public String getItemLabel(Object item) {
			if (this.getAttributeCollection().hasAttribute("itemLabel")) {
				String fieldName = this.getAttributeValue("itemLabel");
				try {
					return Utils.nullSafeToString(PropertyUtils.getProperty(item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			return item.toString();
		}

		public String getItemValue(Object item) {
			if (this.getAttributeCollection().hasAttribute("itemValue")) {
				String fieldName = this.getAttributeValue("itemValue");
				try {
					return Utils.nullSafeToString(PropertyUtils.getProperty(item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			try {
				return Utils.nullSafeToString(PropertyUtils.getProperty(item, "value"));
			} catch (Exception e) {
			}
			return item.toString();
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				for (Object item : this.getItems(context)) {
					String itemId = this.getId(context);
					context.write(("<" + this.getElement() + ">").getBytes());
					context.write(("<label for=\"" + itemId + "\">").getBytes());
					context.write(("<input id=\"" + itemId + "\" name=\"" + this.getName() + "\" type=\"radio\"" + (this.isDisabled() ? " disabled=\"disabled\"" : "") + " value=\""
							+ this.escapeHtml(this.getItemValue(item)) + "\""
							+ (this.isOptionSelected(context, this.getItemValue(item)) ? " checked=\"checked\"" : "")
						 + " />").getBytes());
					context.write((" " + this.escapeHtml(this.getItemLabel(item)) + "</label>").getBytes());
					context.write(("</" + this.getElement() + ">").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
