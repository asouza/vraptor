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
package br.com.caelum.vraptor.ioc;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.VRaptorException;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.interceptor.InterceptorRegistry;

@ApplicationScoped
public class InterceptorStereotypeHandler implements StereotypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(InterceptorStereotypeHandler.class);
	private final InterceptorRegistry registry;

	@Inject
	public InterceptorStereotypeHandler(InterceptorRegistry registry) {
		this.registry = registry;
	}

	public Class<? extends Annotation> stereotype() {
		return Intercepts.class;
	}

	public void handle(Class<?> type) {
		if (Interceptor.class.isAssignableFrom(type)) {
            registerInterceptor(type);
        } else {
            throw new VRaptorException("Annotation " + Intercepts.class + " found in " + type
                    + ", but it is neither an Interceptor nor an InterceptorSequence.");
        }
	}

	@SuppressWarnings("unchecked")
	private void registerInterceptor(Class<?> type) {
		logger.debug("Found interceptor for {}", type);
		Class<? extends Interceptor> interceptorType = (Class<? extends Interceptor>) type;
		registry.register(interceptorType);
	}
}
