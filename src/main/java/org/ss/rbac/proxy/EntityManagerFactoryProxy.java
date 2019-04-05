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
package org.ss.rbac.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Proxy for entity manager factory.
 * @author ss
 */
public class EntityManagerFactoryProxy implements InvocationHandler {
    /** Factory. */
    private final EntityManagerFactory emf;
    /**
     * Private constructor.
     * @param emf factory.
     */
    private EntityManagerFactoryProxy(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("createEntityManager".equals(method.getName())) {
            EntityManager em = (EntityManager) method.invoke(emf, args);
            return EntityManagerProxy.proxying(em);
        } else {
            return method.invoke(emf, args);
        }
    }
    /**
     * Create proxy.
     * @param emf origin factory.
     * @return proxy object.
     */
    public static synchronized EntityManagerFactory proxying(EntityManagerFactory emf) {
        return (EntityManagerFactory) Proxy.newProxyInstance(
                EntityManagerFactoryProxy.class.getClassLoader(),
                new Class[] { EntityManagerFactory.class }, new EntityManagerFactoryProxy(emf));
    }
}
