/*
 * The MIT License
 *
 * Copyright 2019 ss.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.ss.rbac.api;

import javax.persistence.EntityManagerFactory;
import org.ss.rbac.internal.api.EntityManagerProvider;
import org.ss.rbac.internal.api.ServiceProvider;
import org.ss.rbac.proxy.EntityManagerFactoryProxy;

/**
 * Bootstrap.
 * @author ss
 */
public final class RbacApplication {
    /** Entity manager provider. */
    private static final EntityManagerProvider EM_PROVIDER =
            ServiceProvider.load(EntityManagerProvider.class);
    /** Configuration. */
    private static Configuration configuration = null;
    /**
     * Private constructor.
     */
    private RbacApplication() {
    }
    /**
     * Bootstrap module.
     * Proxying entity manager factory for intercept requests inside entity manager. 
     * @param externalFactory external entity manager factory.
     * @param config external configuration. 
     * @return proxy for entity manager factory. 
     */
    public static synchronized EntityManagerFactory bootstrap(
            EntityManagerFactory externalFactory, Configuration config) {
        configuration = config;
        externalFactory = new EntityManagerFactoryProxy()
                .proxying(externalFactory, EntityManagerFactory.class);
        EM_PROVIDER.setEntityManagerFactory(externalFactory);
        return externalFactory;
    }
    /**
     * Get external configuration.
     * @return external configuration.
     */
    public static synchronized Configuration getConfiguration() {
        return configuration;
    }
}
