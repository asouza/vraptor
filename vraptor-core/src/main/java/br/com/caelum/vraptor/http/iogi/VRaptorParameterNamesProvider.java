/**
 *
 */
package br.com.caelum.vraptor.http.iogi;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.ioc.Component;

/**
 * An adapter for iogi's parameterNamesProvider
 *
 * @author Lucas Cavalcanti
 * @since
 *
 */
@Component
public class VRaptorParameterNamesProvider implements br.com.caelum.iogi.spi.ParameterNamesProvider {
	private ParameterNameProvider parameterNameProvider;

	@Inject
	public VRaptorParameterNamesProvider(ParameterNameProvider parameterNameProvider) {
		this.parameterNameProvider = parameterNameProvider;
	}

	@Deprecated
	public VRaptorParameterNamesProvider() {
	}

	public List<String> lookupParameterNames(AccessibleObject methodOrConstructor) {
		return Arrays.asList(parameterNameProvider.parameterNamesFor(methodOrConstructor));
	}
}