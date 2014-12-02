package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class ButtonTest extends AbstractFormTest {

	@Test
	public void empty() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"Submit\"  ></button>", output);
	}
	
	@Test
	public void emptyIdAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button id=\"\" %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"Submit\"  ></button>", output);
	}
	
	@Test
	public void idAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button id=\"myId\" %}{% endform:button %}");
        assertEquals("<button id=\"myId\"  type=\"submit\" value=\"Submit\"  ></button>", output);
	}
	
	@Test
	public void emptyNameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button name=\"\" %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"Submit\"  ></button>", output);
	}
	
	@Test
	public void nameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button name=\"myName\" %}{% endform:button %}");
        assertEquals("<button id=\"myName\" name=\"myName\" type=\"submit\" value=\"Submit\"  ></button>", output);
	}
	
	@Test
	public void emptyValueAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button value=\"\" %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"Submit\"  ></button>", output);
	}
	
	@Test
	public void valueAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button value=\"myValue\" %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"myValue\"  ></button>", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button disabled %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"Submit\" disabled=\"disabled\" ></button>", output);
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:button key=\"value\" %}{% endform:button %}");
        assertEquals("<button   type=\"submit\" value=\"Submit\"  key=\"value\"></button>", output);
	}
	
}
