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

public class FormSelect extends AbstractDataBoundFormElement<FormSelect> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("items", false));
		attributeDefinitions.add(new NamedAttributeDefinition("itemLabel", false));
		attributeDefinitions.add(new NamedAttributeDefinition("itemValue", false));
		attributeDefinitions.add(new EmptyAttributeDefinition("multiple"));
		attributeDefinitions.add(new EmptyAttributeDefinition("disabled"));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(super.compile(context), this.getAttributeCollection());
	}

	private class Compiled extends AbstractDataBoundFormElementCompiled {
		protected Compiled(Renderable content, AttributeCollection attributeCollection) {
			super(content, attributeCollection);
		}

		public boolean hasItems() {
			return this.getAttributeCollection().hasAttribute("items");
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

		public boolean isMultiple() {
			return this.getAttributeCollection().hasAttribute("multiple");
		}

		public boolean isDisabled() {
			return this.getAttributeCollection().hasAttribute("disabled");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			try {
				context.write(("<select id=\"" + this.getId(context) + "\" name=\"" + this.getName() + "\""
						+ (this.isDisabled() ? " disabled=\"disabled\"" : "") + (this.isMultiple() ? " multiple=\"multiple\"" : "") + ">").getBytes());
				if (this.hasItems()) {
					for (Object item : this.getItems(context)) {
						context.write(("<option value=\"" + this.escapeHtml(this.getItemValue(item)) + "\""
								+ (SelectedValueComparator.isSelected(context, this.getItemValue(item)) ? " selected=\"selected\"" : "") + ">"
								+ this.escapeHtml(this.getItemLabel(item)) + "</option>").getBytes());
					}
				} else {
					RenderContext innerContext = context.isolatedModel();
					innerContext.with("isSelectStarted", true);
					innerContext.with("selectActualValue", this.getDataValue(context, this.getPath()));
					this.getContent().render(innerContext);
				}
				context.write(("</select>").getBytes());
			} catch (IOException e) {
			}
		}
	}
}
