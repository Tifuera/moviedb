package com.global.dbtest;

import javax.persistence.EntityManagerFactory;

import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.global.dbtest.service.MovieStorageService;
import com.global.dbtest.service.impl.MovieStorageServiceImpl;
import com.global.dbtest.service.impl.MoviesCommandProvider;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private ServiceRegistration<?> commandServiceRegistration;
	private ServiceRegistration<?> movieStorageServiceRegistration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		EntityManagerFactory emf = lookupEMF(Constants.PERSISTENCE_UNIT_NAME);
		if (emf != null) {
			MovieStorageService movieStorageService = new MovieStorageServiceImpl(
					emf.createEntityManager());

			MoviesCommandProvider commandProvider = new MoviesCommandProvider(
					movieStorageService);

			this.commandServiceRegistration = context.registerService(
					CommandProvider.class.getName(), commandProvider, null);

			this.movieStorageServiceRegistration = context.registerService(
					MovieStorageService.class.getName(), movieStorageService,
					null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		if (this.commandServiceRegistration != null) {
			this.commandServiceRegistration.unregister();
			this.commandServiceRegistration = null;
		}

		if (this.movieStorageServiceRegistration != null) {

			this.movieStorageServiceRegistration.unregister();
		}

	}

	public EntityManagerFactory lookupEMF(String unitName) {
		ServiceReference<?>[] refs = null;
		try {
			refs = context.getServiceReferences(
					EntityManagerFactory.class.getName(), "(osgi.unit.name="
							+ unitName + ")");
		} catch (InvalidSyntaxException isEx) {
			throw new RuntimeException("Filter error", isEx);
		}
		return (refs == null) ? null : (EntityManagerFactory) context
				.getService(refs[0]);
	}

}
