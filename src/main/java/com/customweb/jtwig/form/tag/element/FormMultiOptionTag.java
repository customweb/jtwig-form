package com.customweb.jtwig.form.tag.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.jtwig.compile.CompileContext;
import org.jtwig.content.api.Renderable;
import org.jtwig.exception.CompileException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.render.RenderContext;
import org.springframework.util.ObjectUtils;

import com.customweb.jtwig.form.model.BindStatus;
import com.customweb.jtwig.form.model.SelectedValueComparator;
import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag.MapItem;
import com.customweb.jtwig.lib.attribute.model.AttributeCollection;
import com.customweb.jtwig.lib.attribute.model.VariableAttribute;
import com.customweb.jtwig.lib.attribute.model.definition.AttributeDefinitionCollection;
import com.customweb.jtwig.lib.attribute.model.definition.NamedAttributeDefinition;
import com.customweb.jtwig.lib.attribute.model.definition.VariableAttributeDefinition;

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
		this.getAttributeCollection().compile(context);
		try {
			Loader.Resource resource = this.retrieveResource(context, "form/option");
			return new Compiled(context.environment().parse(resource).compile(context), this.getAttributeCollection());
		} catch (ParseException | ResourceException e) {
			throw new CompileException(e);
		}
	}

	private class Compiled extends AbstractFormElementTag<FormMultiOptionTag>.Compiled {
		protected Compiled(Renderable block, AttributeCollection attributeCollection) {
			super(block, null, attributeCollection);
		}
		
		public boolean isInSelectContext(RenderContext context) {
			return context.map(FormSelectTag.SELECT_CONTEXT_VARIABLE_NAME).equals(Boolean.TRUE);
		}
		
		public Collection<?> getItems(RenderContext context) {
			Object items = this.getAttributeCollection().getAttribute("items", VariableAttribute.class).getVariable();
			if (items instanceof Collection) {
				return (Collection<?>) items;
			} else if (items.getClass().isArray()) {
				return Arrays.asList((Object[]) items);
			} else if (items instanceof Map) {
				List<MapItem> itemList = new ArrayList<MapItem>();
				for (Entry<?, ?> item : ((Map<?, ?>) items).entrySet()) {
					itemList.add(new MapItem(item.getKey(), item.getValue()));
				}
				return itemList;
			}
			throw new RuntimeException("The 'items' attribute value has to be a collection.");
		}

		@Override
		public void render(RenderContext context) throws RenderException {
			if (!this.isInSelectContext(context)) {
				throw new RuntimeException("The 'multioption' tag can only be used inside a valid 'select' tag.");
			}
			this.getAttributeCollection().render(context);
			
			for (Object item : this.getItems(context)) {
				context = this.isolatedModel(context);
				context.with("option", new OptionData(item, context, this.getAttributeCollection()));
				this.getBlock().render(context);
			}
		}
	}
	
	protected class OptionData extends AbstractFormElementTag<FormMultiOptionTag>.Data {
		private Object item;
		
		protected OptionData(Object item, RenderContext context, AttributeCollection attributeCollection) {
			super(context, attributeCollection);
			this.item = item;
		}
		
		public BindStatus getBindStatus() {
			return (BindStatus) getContext().map(FormSelectTag.SELECT_BIND_STATUS_VARIABLE_NAME);
		}
		
		public String getLabel() {
			if (item instanceof MapItem) {
				return ObjectUtils.nullSafeToString(((MapItem) item).getLabel());
			} else if (this.getAttributeCollection().hasAttribute("itemLabel")) {
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
			if (item instanceof MapItem) {
				return ObjectUtils.nullSafeToString(((MapItem) item).getValue());
			} else if (this.getAttributeCollection().hasAttribute("itemValue")) {
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
			return SelectedValueComparator.isSelected(this.getBindStatus(), this.item);
		}
	}
}
