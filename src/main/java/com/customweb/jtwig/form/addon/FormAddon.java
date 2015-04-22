package com.customweb.jtwig.form.addon;

import org.jtwig.Environment;
import org.jtwig.configuration.JtwigConfiguration;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.addon.element.FormButtonAddon;
import com.customweb.jtwig.form.addon.element.FormCheckboxAddon;
import com.customweb.jtwig.form.addon.element.FormErrorsAddon;
import com.customweb.jtwig.form.addon.element.FormHiddenAddon;
import com.customweb.jtwig.form.addon.element.FormInputAddon;
import com.customweb.jtwig.form.addon.element.FormLabelAddon;
import com.customweb.jtwig.form.addon.element.FormMultiCheckboxAddon;
import com.customweb.jtwig.form.addon.element.FormMultiOptionAddon;
import com.customweb.jtwig.form.addon.element.FormMultiRadioAddon;
import com.customweb.jtwig.form.addon.element.FormOptionAddon;
import com.customweb.jtwig.form.addon.element.FormPasswordAddon;
import com.customweb.jtwig.form.addon.element.FormRadioAddon;
import com.customweb.jtwig.form.addon.element.FormSelectAddon;
import com.customweb.jtwig.form.addon.element.FormTextareaAddon;
import com.customweb.jtwig.form.addon.element.FormTokenAddon;
import com.customweb.jtwig.form.tag.FormTag;
import com.customweb.jtwig.lib.attribute.AttributeAddon;

public class FormAddon extends AttributeAddon<FormTag> {
	
	public static void addons(JtwigConfiguration configuration) {
		configuration.getAddonParserList().withAddon(FormAddon.class);
		configuration.getAddonParserList().withAddon(FormButtonAddon.class);
		configuration.getAddonParserList().withAddon(FormCheckboxAddon.class);
		configuration.getAddonParserList().withAddon(FormErrorsAddon.class);
		configuration.getAddonParserList().withAddon(FormHiddenAddon.class);
		configuration.getAddonParserList().withAddon(FormInputAddon.class);
		configuration.getAddonParserList().withAddon(FormLabelAddon.class);
		configuration.getAddonParserList().withAddon(FormMultiCheckboxAddon.class);
		configuration.getAddonParserList().withAddon(FormMultiOptionAddon.class);
		configuration.getAddonParserList().withAddon(FormMultiRadioAddon.class);
		configuration.getAddonParserList().withAddon(FormOptionAddon.class);
		configuration.getAddonParserList().withAddon(FormPasswordAddon.class);
		configuration.getAddonParserList().withAddon(FormRadioAddon.class);
		configuration.getAddonParserList().withAddon(FormSelectAddon.class);
		configuration.getAddonParserList().withAddon(FormTextareaAddon.class);
		configuration.getAddonParserList().withAddon(FormTokenAddon.class);
	}

	public FormAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:form";
	}

	@Override
	public FormTag instance() {
		return new FormTag();
	}

}
