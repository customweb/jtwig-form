package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import com.customweb.jtwig.form.Utils;
import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.customweb.jtwig.lib.model.VariableAttribute;
import com.customweb.jtwig.lib.model.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.render.RenderContext;

public class FormMultiOption extends AbstractFormElement<FormMultiOption> {

	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("items", true));
		attributeDefinitions.add(new NamedAttributeDefinition("itemLabel", false));
		attributeDefinitions.add(new NamedAttributeDefinition("itemValue", false));
		return attributeDefinitions;
	}

	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		return new Compiled(this.getAttributeCollection());
	}

	private class Compiled extends AbstractFormElementCompiled {
		protected Compiled(AttributeCollection attributeCollection) {
			super(null, attributeCollection);
		}
		
		public void checkSelect(RenderContext context) {
			if (!context.map("isSelectStarted").equals(Boolean.TRUE)) {
				throw new RuntimeException("The 'multioption' tag can only be used inside a valid 'select' tag.");
			}
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

		@Override
		public void render(RenderContext context) throws RenderException {
			this.checkSelect(context);
			
			try {
				for (Object item : this.getItems(context)) {
					context.write(("<option value=\"" + this.escapeHtml(this.getItemValue(item)) + "\""
							+ (SelectedValueComparator.isSelected(context.map("selectActualValue"), this.getItemValue(item)) ? " selected=\"selected\"" : "")
							+ Utils.concatAttributes(this.getDynamicAttributes()) + ">" + this.escapeHtml(this.getItemLabel(item)) + "</option>").getBytes());
				}
			} catch (IOException e) {
			}
		}
	}
}
