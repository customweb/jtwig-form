package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class MultiCheckboxTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:multicheckbox path=\"stringValue\" items=\"singleItem\" %}{% endform:multicheckbox %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span></form>", output);
	}
	
	@Test
	public void nameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"singleItem\" name=\"myName\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"myName1\" name=\"myName\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"singleItem\" disabled %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\" disabled=\"disabled\" type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void idAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\"  items=\"singleItem\" id=\"myId\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"myId\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void itemsFromArrayToString() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"itemArray\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"value1\"     /> value1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void itemsFromArray() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"itemArray\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"value1\"     /> Label1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> Label2</label></span>", output);
	}
	
	@Test
	public void itemsFromList() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"itemList\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"value1\"     /> Label1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> Label2</label></span>", output);
	}
	
	@Test
	public void itemsFromMap() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"itemMap\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"value1\"     /> Label1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    /> Label2</label></span>", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:multicheckbox items=\"singleItem\" %}{% endform:multicheckbox %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void missingItemsAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" %}{% endform:multicheckbox %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:multicheckbox path=\"myModel.stringValue\" items=\"singleItem\" key=\"value\" %}{% endform:multicheckbox %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"   key=\"value\" /> stringValue</label></span>", output);
	}
	
	private JtwigModelMap getDefaultMap() {
		JtwigModelMap map = new JtwigModelMap();
		map.add("myModel", new Model());
		
		map.add("singleItem", new Item[]{new Item("label", "stringValue")});
		
		Item[] itemArray = new Item[]{
			new Item("Label1", "value1"),
			new Item("Label2", "stringValue")
		};
		Map<String, String> itemMap = new LinkedHashMap<String, String>();
		for (Item item : itemArray) {
			itemMap.put(item.getValue(), item.getLabel());
		}
		map.add("itemArray", itemArray);
		map.add("itemList", Arrays.asList(itemArray));
		map.add("itemMap", itemMap);
		return map;
	}
	
	public static class Item {
		private String label;
		private String value;
		
		public Item(String label, String value) {
			this.label = label;
			this.value = value;
		}
		
		public String getLabel() {
			return label;
		}
		
		public String getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return this.getValue();
		}
	}
	
	public static class Model {
		public String getStringValue() {
			return "stringValue";
		}
	}
	
}
