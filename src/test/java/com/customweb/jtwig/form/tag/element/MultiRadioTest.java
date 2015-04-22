package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jtwig.JtwigModelMap;
import org.jtwig.exception.JtwigException;
import org.jtwig.exception.ParseException;
import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;

public class MultiRadioTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws JtwigException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:multiradio path=\"stringValue\" items=\"singleItem\" %}{% endform:multiradio %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span></form>", output);
	}
	
	@Test
	public void nameAttribute() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"singleItem\" name=\"myName\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"myName1\" name=\"myName\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void disabledAttribute() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"singleItem\" disabled=\"true\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\" disabled=\"disabled\" type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void idAttribute() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\"  items=\"singleItem\" id=\"myId\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"myId\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void itemsFromArrayToString() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"itemArray\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"value1\"     /> value1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> stringValue</label></span>", output);
	}
	
	@Test
	public void itemsFromArray() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"itemArray\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"value1\"     /> Label1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> Label2</label></span>", output);
	}
	
	@Test
	public void itemsFromList() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"itemList\" itemLabel=\"label\" itemValue=\"value\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"value1\"     /> Label1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> Label2</label></span>", output);
	}
	
	@Test
	public void itemsFromMap() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"itemMap\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"value1\"     /> Label1</label></span><span><label><input id=\"stringValue2\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    /> Label2</label></span>", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws JtwigException {
		renderTemplate("{% form:multiradio items=\"singleItem\" %}{% endform:multiradio %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void missingItemsAttribute() throws JtwigException {
		renderTemplate("{% form:multiradio path=\"myModel.stringValue\" %}{% endform:multiradio %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws JtwigException {
		String output = renderTemplate("{% form:multiradio path=\"myModel.stringValue\" items=\"singleItem\" key=\"value\" %}{% endform:multiradio %}", this.getDefaultMap());
        assertEquals("<span><label><input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"   key=\"value\" /> stringValue</label></span>", output);
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
