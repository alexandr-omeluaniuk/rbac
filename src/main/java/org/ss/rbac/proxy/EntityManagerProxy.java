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

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.internal.api.PermissionResolver;
import org.ss.rbac.internal.api.ServiceProvider;

/**
 * Proxy for entity manager.
 * @author ss
 */
public class EntityManagerProxy extends AbstractProxy<EntityManager> {
    /** Permission resolver. */
    private final PermissionResolver permissionResolver =
            ServiceProvider.load(PermissionResolver.class);
    @Override
    public Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "refresh":
                Class entityClazz = args[0].getClass();
                if (Audit.class.isAssignableFrom(entityClazz)) {
                    permissionResolver.resolveAccessToOperation(entityClazz,
                            PermissionOperation.READ);
                }
                return method.invoke(this.origin, args);
            case "find":
                Class entityClass = (Class) args[0];
                if (Audit.class.isAssignableFrom(entityClass)) {
                    permissionResolver.resolveAccessToOperation(entityClass,
                            PermissionOperation.READ);
                }
                return method.invoke(this.origin, args);
            case "getCriteriaBuilder":
                CriteriaBuilder cb = (CriteriaBuilder) method.invoke(origin, args);
                return new CriteriaBuilderProxy().proxying(cb, CriteriaBuilder.class);
            case "createQuery":
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof ProxyMethods) {
                        args[i] = ((ProxyMethods)Proxy.getInvocationHandler(args[i]))
                                .getOrigin();
                    }
                }
                return method.invoke(origin, args);
            default:
                return method.invoke(origin, args);
        }
    }
}
