package com.customweb.jtwig.form.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.customweb.jtwig.lib.model.AttributeCollection;
import com.customweb.jtwig.lib.model.AttributeDefinition;
import com.customweb.jtwig.lib.model.AttributeModel;
import com.customweb.jtwig.lib.model.DynamicAttributeDefinition;
import com.customweb.jtwig.lib.model.NamedAttributeDefinition;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.render.RenderContext;

public abstract class FormElement<T extends FormElement<T>> extends AttributeModel<T> {

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		List<AttributeDefinition> definitions = new ArrayList<AttributeDefinition>();
		definitions.add(new NamedAttributeDefinition("path", true));
		definitions.add(new NamedAttributeDefinition("label", false));
		definitions.add(new DynamicAttributeDefinition());
		return definitions;
	}

	protected abstract class CompiledFormElement implements Renderable {
		private final AttributeCollection<FormCheckbox> attributeCollection;

		protected CompiledFormElement(Renderable content, AttributeCollection<FormCheckbox> attributeCollection) {
			this.attributeCollection = attributeCollection;
		}

		protected AttributeCollection<FormCheckbox> getAttributeCollection() {
			return this.attributeCollection;
		}

		protected void renderLabel(RenderContext context) {
			if (this.attributeCollection.hasAttribute("label")) {
				String path = this.getAttributeCollection().getAttribute("path").getValue();
				String label = this.attributeCollection.getAttribute("label").getValue();

				try {
					context.write(("<label for=\"" + path + "\">" + label + "</label>").getBytes());
				} catch (IOException e) {
				}
			}
		}

		protected Object getFormDataValue(RenderContext context, String fieldName) {
			Object formDataObject = context.map("formDataObject");
			Object fieldValue = null;
			try {
				fieldValue = PropertyUtils.getProperty(formDataObject, fieldName);
			} catch (NoSuchMethodException e1) {
				throw new RuntimeException("The form data object does not have a field named '" + fieldName + "'.");
			} catch (InvocationTargetException | IllegalAccessException e1) {
				throw new RuntimeException("The value of the field '" + fieldName + "' cannot be read.");
			}
			return fieldValue;
		}

	}

}
