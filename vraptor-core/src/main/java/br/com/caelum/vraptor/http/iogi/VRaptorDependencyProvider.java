/**
 *
 */
package br.com.caelum.vraptor.http.iogi;

import javax.inject.Inject;

import br.com.caelum.iogi.reflection.Target;
import br.com.caelum.iogi.spi.DependencyProvider;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;

/**
 * an adapter for IOGI's dependency provider based on VRaptor's container
 *
 * @author Lucas Cavalcanti
 * @since
 *
 */
@Component
@ApplicationScoped
public class VRaptorDependencyProvider implements DependencyProvider {

	private Container container;

	@Inject
	public VRaptorDependencyProvider(Container container) {
		this.container = container;
	}
	
	@Deprecated
	public VRaptorDependencyProvider() {
	}

	public boolean canProvide(Target<?> target) {
		return container.canProvide(target.getClassType());
	}

	public Object provide(Target<?> target) {
		return container.instanceFor(target.getClassType());
	}
}