jtwig-form
==========
This library provides addons for the [Jtwig template engine](http://jtwig.org/) to build forms. It provides an easy integration with the Spring framework.

## Installation ##
The project contains a Maven .pom file to include all depencendies.

## Usage ##
To use the form tags, they have to be registered first.

```java
JtwigConfiguration config = new JtwigConfiguration();
FormAddon.addons(config);
JtwigTemplate template = new JtwigTemplate(..., config);
```

### Tags ###
All form tags must have an opening (prefix `form:`) and closig tag (prefix `endform:`).

#### Form ####
Renders an HTML 'form' tag and exposes a binding path to inner tags for binding.

```twig
{% form:form action="..." method="post" %}{% endform:form %}
```

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| action      | false     |             | Specifies an address (url) where to submit the form. |
| method      | false     | post        | Specifies the HTTP method used when submitting the form. |
| model       | false     | formModel   | Name of the JtwigModelMap entry under which the form object is exposed. |
| errors      | false     | formErrors  | Name of the JtwigModelMap entry unter which the form errors are exposed. |

Dynamic attributes are allowed.

#### Button ####
Renders an HTML 'button' tag.

```twig
{% form:button %}{% endform:button %}
```

The tag's content is used as label for the HTML 'button' element.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| name        | false     |             | The name attribute for the HTML button tag. |
| value       | false     |             | The value attribute for the HTML button tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Checkbox ####
Renders an HTML 'input' tag with type 'checkbox'.

```twig
{% form:checkbox path="..." %}{% endform:checkbox %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| value       | false     |             | The value attribute for the HTML input tag. Required when binding to non-boolean values. |
| label       | false     |             | Value to be displayed as part of the tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Errors ####
Renders field errors.

```twig
{% form:errors %}{% endform:errors %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML tag. |

#### Hidden Input ####
Renders an HTML 'input' tag with type 'hidden' using the bound value.

```twig
{% form:hidden path="..." %}{% endform:hidden %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Text Input ####
Renders an HTML 'input' tag with type 'text' using the bound value.

```twig
{% form:input path="..." %}{% endform:input %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Label ####
Renders a form field label in an HTML 'label' tag.

```twig
{% form:label path="..." %}{% endform:label %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |

#### Multi Checkboxes ####
Renders multiple HTML 'input' tags with type 'checkbox'.

```twig
{% form:multicheckbox path="..." items="..." %}{% endform:multicheckbox %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| items       | true      |             | The Collection, Map or array of objects used to generate the HTML 'input' tags with type 'checkbox'. |
| itemLabel   | false     |             | Name of the property mapped to the label to be displayed as part of the HTML 'input' tags with type 'checkbox'. |
| itemValue   | false     |             | Name of the property mapped to 'value' attribute of the HTML 'input' tags with type 'checkbox'. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Multi Options ####
Renders a list of HTML 'option' tags. Sets 'selected' as appropriate based on bound value.

```twig
{% form:select path="..." %}
  {% form:multioption items="..." %}{% endform:multioption %}
{% endform:select %}
```

Must be defined inside of a `form:select` tag.

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| items       | true      |             | The Collection, Map or array of objects used to generate the HTML 'option' tags. |
| itemLabel   | false     |             | Name of the property mapped to the inner text of the HTML 'option' tags. |
| itemValue   | false     |             | Name of the property mapped to 'value' attribute of the HTML 'option' tags. |

#### Multi Radio Buttons ####
Renders multiple HTML 'input' tags with type 'radio'.

```twig
{% form:multiradio path="..." items="..." %}{% endform:multiradio %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| items       | true      |             | The Collection, Map or array of objects used to generate the HTML 'input' tags with type 'radio'. |
| itemLabel   | false     |             | Name of the property mapped to the label to be displayed as part of the HTML 'input' tags with type 'radio'. |
| itemValue   | false     |             | Name of the property mapped to 'value' attribute of the HTML 'input' tags with type 'radio'. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Option ####
Renders a single HTML 'option'. Sets 'selected' as appropriate based on bound value.

```twig
{% form:select path="..." %}
  {% form:option items="..." %}
    Label
  {% endform:option %}
{% endform:select %}
```

Must be defined inside of a `form:select` tag.

The tag's content is used as label of the HTML 'option' element.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| value       | true      |             | The 'value' attribute of the HTML 'option' tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Password Input ####
Renders an HTML 'input' tag with type 'password' using the bound value.

```twig
{% form:password path="..." %}{% endform:password %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute     | Required  | Default     | Description |
| ------------- | --------- | ----------- | ----------- |
| path          | true      |             | Path to property for data binding. |
| id            | false     | = path      | The id attribute for the HTML input tag. |
| showpassword  | false     | false       | Setting this attribute (without a value) will show the password. |
| disabled      | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Radio Button ####
Renders an HTML 'input' tag with type 'radio'.

```twig
{% form:radio path="..." %}{% endform:radio %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| value       | false     |             | The value attribute for the HTML input tag. Required when binding to non-boolean values. |
| label       | false     |             | Value to be displayed as part of the tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Select ####
Renders an HTML 'select' element. Supports databinding to the selected option.

```twig
{% form:select path="..." items="..." %}{% endform:select %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML input tag. |
| items       | false     |             | The Collection, Map or array of objects used to generate the HTML 'input' tags with type 'radio'. |
| itemLabel   | false     |             | Name of the property mapped to the label to be displayed as part of the HTML 'input' tags with type 'radio'. |
| itemValue   | false     |             | Name of the property mapped to 'value' attribute of the HTML 'input' tags with type 'radio'. |
| multiple    | false     | false       | Setting this attribute (without a value) will allow multiple values to be selected. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

#### Textarea ####
Renders an HTML 'textarea'.

```twig
{% form:textarea path="..." %}{% endform:textarea %}
```

The tag's content is ignored.

Dynamic attributes are allowed.

| Attribute   | Required  | Default     | Description |
| ----------- | --------- | ----------- | ----------- |
| path        | true      |             | Path to property for data binding. |
| id          | false     | = path      | The id attribute for the HTML textarea tag. |
| disabled    | false     | false       | Setting this attribute (without a value) will disable the HTML element. |

### CSRF Protection ###
The Jtwig form library comes with a CSRF protection mechanism.
When the form is rendered, a token is generated and inserted through the token tag.
The token is submitted to the server along with the rest of the form data.
There it is validated and the form is processed only if the token is valid.

To use CSRF protection, a token generator needs to be defined by extending the `com.customweb.jtwig.form.model.AbstractTokenGenerator`. This generator than needs to be registered with the FormTokenAddon.

```java
com.customweb.jtwig.form.addon.element.FormTokenAddon.setTokenGeneratorClass(MyTokenGenerator.class);
```

To activate the CSRF protection for a form, insert the `form:token` tag inside of the  `form:form`.

```twig
{% form:token %}{% endform:token %}
```

The validation of the token is done using the token generator again by calling the validate method with the token received as argument.

```java
FormTokenAddon.getTokenGenerator().validate(receivedToken);
```

### Spring Integration ###
To use the form tags, they have to be registered first.

```xml
<bean id="viewResolver" class="com.lyncode.jtwig.mvc.JtwigViewResolver">
	<property name="prefix" value="/WEB-INF/views/" />
	<property name="suffix" value=".twig" />
</bean>
<bean class="com.customweb.jtwig.form.spring.ViewResolverAddon" />
```

#### CSRF Protection ####
The usage of the CSRF protection in spring is easy. To activate it, enable aspectj autoproxy and add these definitions to the spring configuration:

```xml
<bean class="com.customweb.jtwig.form.spring.TokenMethodInterceptor" />

<bean class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
  <property name="staticMethod" value="com.customweb.jtwig.form.addon.element.FormTokenAddon.setTokenGeneratorClass"/>
  <property name="arguments">
    <list>
      <value>my.package.MyTokenGenerator</value>
    </list>
  </property>
</bean>
```

After it is configured, the CSRF protection can be used by annotating the controller action method that receives the form data with `@TokenProtection`.
The method must have an argument of type `javax.servlet.ServletRequest`.
If the validation fails, an exception of type `org.springframework.security.access.AccessDeniedException.AccessDeniedException` is thrown.

```java
@Controller
public class MyController {
	@TokenProtection
	@RequestMapping(value = { "/processForm"}, method = RequestMethod.POST)
	public void processForm(HttpServletRequest request, HttpServletResponse response) {
	  ...
	}
}
```
