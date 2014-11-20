package com.customweb.jtwig.form.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
		
		protected Object getFormDataValue(RenderContext context, String fieldName) {
			Object formDataObject = context.map("formDataObject");
			Object fieldValue = null;
			Field field;
			try {
				field = formDataObject.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				fieldValue = field.get(formDataObject);
			} catch (NoSuchFieldException e1) {
				throw new RuntimeException("The form data object does not have a field named '" + fieldName + "'.");
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				throw new RuntimeException("The value of the field '" + fieldName + "' cannot be read.");
			}
			return fieldValue;
		}
		
	}

}
