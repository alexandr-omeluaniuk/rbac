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
import javax.persistence.criteria.CriteriaQuery;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.internal.api.PermissionResolver;
import org.ss.rbac.internal.api.ServiceProvider;

/**
 * Proxy for CriteriaQuery.
 * @see javax.persistence.criteria.CriteriaQuery
 * @author ss
 */
public class CriteriaQueryProxy extends AbstractProxy<CriteriaQuery> {
    /** Permission resolver. */
    private final PermissionResolver permissionResolver =
            ServiceProvider.load(PermissionResolver.class);
    @Override
    protected Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "from":
                Class entityClass = (Class) args[0];
                if (Audit.class.isAssignableFrom(entityClass)) {
                    permissionResolver.resolveAccessToOperation(entityClass,
                            PermissionOperation.READ);
                }
                return method.invoke(this.origin, args);
            default:
                return method.invoke(this.origin, args);
        }
    }
}
