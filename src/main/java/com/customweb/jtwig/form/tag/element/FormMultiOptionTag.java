package com.customweb.jtwig.form.tag.element;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ObjectUtils;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.BindStatus;
import com.customweb.jtwig.form.model.SelectedValueComparator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.VariableAttribute;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;
import com.lyncode.jtwig.compile.CompileContext;
import com.lyncode.jtwig.content.api.Renderable;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiOptionTag extends AbstractFormElementTag<FormMultiOptionTag> {
	
	@Override
	public AttributeDefinitionCollection getAttributeDefinitions() {
		AttributeDefinitionCollection attributeDefinitions = super.getAttributeDefinitions();
		attributeDefinitions.add(new VariableAttributeDefinition("items", true));
		attributeDefinitions.add(new NamedAttributeDefinition("itemLabel", false));
		attributeDefinitions.add(new NamedAttributeDefinition("itemValue", false));
		attributeDefinitions.getDynamicAttributeDefinition().addDisallowedKey("selected");
		return attributeDefinitions;
	}
	
	@Override
	public Renderable compile(CompileContext context) throws CompileException {
		try {
			JtwigResource resource = FormAddon.getResourceHandler().resolve("element/option");
			return new Compiled(context.parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormElementCompiled {
		private Renderable block;
		
		protected Compiled(Renderable block, AttributeCollection attributeCollection) {
			super(null, attributeCollection);
			this.block = block;
		}
		
		public boolean isSelectActive(RenderContext context) {
			return context.map(FormSelectTag.SELECT_ACTIVE_VARIABLE_NAME).equals(Boolean.TRUE);
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

		@Override
		public void render(RenderContext context) throws RenderException {
			if (!this.isSelectActive(context)) {
				throw new RuntimeException("The 'multioption' tag can only be used inside a valid 'select' tag.");
			}
			
			for (Object item : this.getItems(context)) {
				context = context.isolatedModel();
				context.with("option", new OptionData(item, context, this.getAttributeCollection()));
				block.render(context);
			}
		}
	}
	
	protected class OptionData extends AbstractFormElementData {
		private Object item;
		
		protected OptionData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.item = item;
		}
		
		public BindStatus getBindStatus() {
			return (BindStatus) getContext().map(FormSelectTag.SELECT_BIND_STATUS_VARIABLE_NAME);
		}
		
		public String getLabel() {
			if (this.getAttributeCollection().hasAttribute("itemLabel")) {
				String fieldName = this.getAttributeValue("itemLabel");
				try {
					return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(this.item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			return this.item.toString();
		}

		public String getValue() {
			if (this.getAttributeCollection().hasAttribute("itemValue")) {
				String fieldName = this.getAttributeValue("itemValue");
				try {
					return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(this.item, fieldName));
				} catch (Exception e) {
					throw new RuntimeException("The item does not have a field named '" + fieldName + "'.");
				}
			}
			try {
				return ObjectUtils.nullSafeToString(PropertyUtils.getProperty(this.item, "value"));
			} catch (Exception e) {
			}
			return this.item.toString();
		}
		
		public boolean isSelected() {
			return SelectedValueComparator.isSelected(this.getBindStatus(), this.getValue());
		}
	}
}
