package com.customweb.jtwig.form.addon;

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
import com.customweb.jtwig.lib.AttributeAddon;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormAddon extends AttributeAddon<FormTag> {
	
	public static void addons(JtwigConfiguration config) {
		config.parse().addons().withAddon(FormAddon.class);
		config.parse().addons().withAddon(FormButtonAddon.class);
		config.parse().addons().withAddon(FormCheckboxAddon.class);
		config.parse().addons().withAddon(FormErrorsAddon.class);
		config.parse().addons().withAddon(FormHiddenAddon.class);
		config.parse().addons().withAddon(FormInputAddon.class);
		config.parse().addons().withAddon(FormLabelAddon.class);
		config.parse().addons().withAddon(FormMultiCheckboxAddon.class);
		config.parse().addons().withAddon(FormMultiOptionAddon.class);
		config.parse().addons().withAddon(FormMultiRadioAddon.class);
		config.parse().addons().withAddon(FormOptionAddon.class);
		config.parse().addons().withAddon(FormPasswordAddon.class);
		config.parse().addons().withAddon(FormRadioAddon.class);
		config.parse().addons().withAddon(FormSelectAddon.class);
		config.parse().addons().withAddon(FormTextareaAddon.class);
		config.parse().addons().withAddon(FormTokenAddon.class);
	}

	public FormAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
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
