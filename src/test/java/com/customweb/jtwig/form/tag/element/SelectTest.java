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

public class SelectTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:select path=\"stringValue\" %}{% endform:select %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><select id=\"stringValue\" name=\"stringValue\"     ></select></form>", output);
	}
	
	@Test
	public void nameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" name=\"myName\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"myName\" name=\"myName\"     ></select>", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" disabled=\"true\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\" disabled=\"disabled\"    ></select>", output);
	}
	
	@Test
	public void multipleAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" multiple %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"  multiple=\"multiple\"   ></select>", output);
	}
	
	@Test
	public void idAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" id=\"myId\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"myId\" name=\"stringValue\"     ></select>", output);
	}
	
	@Test
	public void itemsFromArrayToString() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" items=\"itemArray\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"     ><option  value=\"value1\"    >value1</option><option  value=\"stringValue\" selected=\"selected\"   >stringValue</option></select>", output);
	}
	
	@Test
	public void itemsFromArray() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" items=\"itemArray\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"     ><option  value=\"value1\"    >Label1</option><option  value=\"stringValue\" selected=\"selected\"   >Label2</option></select>", output);
	}
	
	@Test
	public void itemsFromList() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" items=\"itemList\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"     ><option  value=\"value1\"    >Label1</option><option  value=\"stringValue\" selected=\"selected\"   >Label2</option></select>", output);
	}
	
	@Test
	public void itemsFromMap() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" items=\"itemMap\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"     ><option  value=\"value1\"    >Label1</option><option  value=\"stringValue\" selected=\"selected\"   >Label2</option></select>", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:select %}{% endform:select %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" key=\"value\" %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"     key=\"value\"></select>", output);
	}
	
	@Test
	public void enumValue() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.enumValue\" %}{% form:option value=\"TEST\" %}TEST{% endform:option %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"enumValue\" name=\"enumValue\"     ><option  value=\"TEST\" selected=\"selected\"   >TEST</option></select>", output);
	}
	
	private JtwigModelMap getDefaultMap() {
		JtwigModelMap map = new JtwigModelMap();
		map.add("myModel", new Model());
		
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
		
		public Enum getEnumValue() {
			return Enum.TEST;
		}
	}
	
	public static enum Enum {
		TEST
	}
	
}
