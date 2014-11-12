package com.customweb.jtwig.form;

import org.parboiled.Rule;

import com.customweb.jtwig.form.model.Form;
import com.lyncode.jtwig.addons.Addon;
import com.lyncode.jtwig.addons.AddonModel;
import com.lyncode.jtwig.content.model.compilable.Text;
import com.lyncode.jtwig.expressions.model.Constant;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.parser.model.JtwigSymbol;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormAddon extends Addon {

	public FormAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	public Rule startRule() {
		return Optional(
			Sequence(
				push(new Form()),
				ZeroOrMore(
					attribute(),
					action(((Form)expressionParser().peek(2)).addAttribute(expressionParser().pop(1), expressionParser().pop(0)))
				)
			)
		);
	}
	
	protected Rule attribute() {
		return Sequence(
			basicParser().identifier(),
			expressionParser().push(new Constant<>(match())),
			basicParser().symbol(JtwigSymbol.ATTR),
			FirstOf(
				string(basicParser().symbol(JtwigSymbol.QUOTE)),
				string(basicParser().symbol(JtwigSymbol.DOUBLE_QUOTE))
			),
			expressionParser().push(new Constant<>(basicParser().pop())),
			basicParser().spacing()
        );
	}
	
	protected Rule string(Rule delimiter) {
		return Sequence(
			delimiter,
			basicParser().push(""),
			OneOrMore(
				Sequence(
					TestNot(
							delimiter
					),
					ANY,
					basicParser().push(basicParser().peek() + match())
				)
            ),
            delimiter
		);
	}

	protected String keyword() {
		return "form";
	}

	@Override
	public java.lang.String beginKeyword() {
		return keyword();
	}

	@Override
	public java.lang.String endKeyword() {
		return "end" + keyword();
	}

	@Override
	public AddonModel<Form> instance() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("rawtypes")
	String popAsString() {
		return (String) ((Constant) expressionParser().pop(0)).getValue();
	}

}
