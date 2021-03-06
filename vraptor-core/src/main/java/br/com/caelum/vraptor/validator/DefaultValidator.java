/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.caelum.vraptor.validator;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.view.ValidationViewsFactory;

import com.google.common.base.Supplier;

/**
 * The default validator implementation.
 *
 * @author Guilherme Silveira
 */
@RequestScoped
public class DefaultValidator extends AbstractValidator {

    private final Result result;
	private final List<Message> errors = new ArrayList<Message>();
	private final ValidationViewsFactory viewsFactory;
	private final BeanValidator beanValidator;
	private final Outjector outjector;
	private final Proxifier proxifier;
	private final Localization localization;

	@Inject
    public DefaultValidator(Result result, ValidationViewsFactory factory, Outjector outjector, Proxifier proxifier, BeanValidator beanValidator, Localization localization) {
        this.result = result;
		this.viewsFactory = factory;
		this.outjector = outjector;
		this.proxifier = proxifier;
		this.beanValidator = beanValidator;
		this.localization = localization;
    }
	
    public void checking(Validations validations) {
        addAll(validations.getErrors(new LocalizationSupplier(localization)));
    }

    public void validate(Object object, Class<?>... groups) {
        addAll(beanValidator.validate(object, groups));
    }
    
    public void validateProperties(Object object, String... properties) {
    	addAll(beanValidator.validateProperties(object, properties));
	}

    public <T extends View> T onErrorUse(Class<T> view) {
    	if (!hasErrors()) {
    		return new MockResult(proxifier).use(view); //ignore anything, no errors occurred
    	}
    	result.include("errors", errors);
    	outjector.outjectRequestMap();
    	return viewsFactory.instanceFor(view, errors);
    }

    public void addAll(Collection<? extends Message> messages) {
		for (Message message : messages) {
			add(message);
		}
	}

    public void add(Message message) {
    	if (message instanceof I18nMessage && !((I18nMessage) message).hasBundle()) {
    		((I18nMessage) message).setLazyBundle(new LocalizationSupplier(localization));
    	}
    	errors.add(message);
    }

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public List<Message> getErrors() {
		return unmodifiableList(errors);
	}

}

class LocalizationSupplier implements Supplier<ResourceBundle> {
	
	private final Localization localization;

	public LocalizationSupplier(Localization localization) {
		this.localization = localization;
	}

	public ResourceBundle get() {
		return localization.getBundle();
	}
}

