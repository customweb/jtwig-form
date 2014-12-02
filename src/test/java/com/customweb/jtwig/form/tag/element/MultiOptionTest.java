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

public class MultiOptionTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:select path=\"stringValue\" %}{% form:multioption items=\"singleItem\" %}{% endform:multioption %}{% endform:select %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\" ><select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"stringValue\" selected=\"selected\" >stringValue</option></select></form>", output);
	}

	@Test
	public void itemsFromArrayToString() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:multioption items=\"itemArray\" %}{% endform:multioption %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"value1\"  >value1</option><option  value=\"stringValue\" selected=\"selected\" >stringValue</option></select>", output);
	}
	
	@Test
	public void itemsFromArray() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:multioption items=\"itemArray\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:multioption %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"value1\"  >Label1</option><option  value=\"stringValue\" selected=\"selected\" >Label2</option></select>", output);
	}
	
	@Test
	public void itemsFromList() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:multioption items=\"itemList\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:multioption %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"value1\"  >Label1</option><option  value=\"stringValue\" selected=\"selected\" >Label2</option></select>", output);
	}
	
	@Test
	public void itemsFromMap() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:multioption items=\"itemMap\" %}{% endform:multioption %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"value1\"  >Label1</option><option  value=\"stringValue\" selected=\"selected\" >Label2</option></select>", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingItemsAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:multioption %}{% endform:multioption %}{% endform:select %}", this.getDefaultMap());
	}
	
	@Test(expected = RuntimeException.class)
	public void notInSelectContext() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:multioption items=\"singleItem\" %}{% endform:multioption %}", this.getDefaultMap());
	}

	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:multioption items=\"singleItem\" key=\"value\" %}{% endform:multioption %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"stringValue\" selected=\"selected\" key=\"value\">stringValue</option></select>", output);
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
