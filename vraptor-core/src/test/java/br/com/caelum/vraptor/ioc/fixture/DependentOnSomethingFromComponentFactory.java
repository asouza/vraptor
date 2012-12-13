/**
 *
 */
package br.com.caelum.vraptor.ioc.fixture;

import javax.inject.Inject;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.NeedsCustomInstantiation;

@Component
public class DependentOnSomethingFromComponentFactory {
	private NeedsCustomInstantiation dependency;

	@Inject
	public DependentOnSomethingFromComponentFactory(NeedsCustomInstantiation dependency) {
		this.dependency = dependency;
	}
	
	@Deprecated
	public DependentOnSomethingFromComponentFactory() {
	}

	public NeedsCustomInstantiation getDependency() {
		return dependency;
	}
}