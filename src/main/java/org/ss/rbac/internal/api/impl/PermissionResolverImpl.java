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
package org.ss.rbac.internal.api.impl;

import java.util.List;
import java.util.Set;
import org.ss.rbac.api.RbacApplication;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.entity.DataPermission;
import org.ss.rbac.entity.User;
import org.ss.rbac.exception.OperationDeniedException;
import org.ss.rbac.internal.api.DataPermissionDAO;
import org.ss.rbac.internal.api.PermissionResolver;
import org.ss.rbac.internal.api.ServiceProvider;

/**
 * Permission resolver implementation.
 * @author ss
 */
public class PermissionResolverImpl implements PermissionResolver {
    /** Logger. */
    private static final System.Logger LOG =
            System.getLogger(PermissionResolverImpl.class.getName());
    /** Data permission DAO. */
    private final DataPermissionDAO dataPermissionDAO =
            ServiceProvider.load(DataPermissionDAO.class);
    @Override
    public void resolveAccessToOperation(Class<? extends Audit> entityClass,
            PermissionOperation operation) throws OperationDeniedException {
        User currentUser = RbacApplication.getConfiguration().getCurrentUser();
        if (LOG.isLoggable(System.Logger.Level.TRACE)) {
            LOG.log(System.Logger.Level.TRACE, "[resolveAccessToOperation] entity class: "
                    + entityClass.getName());
            LOG.log(System.Logger.Level.TRACE, "[resolveAccessToOperation] user: " + currentUser);
            LOG.log(System.Logger.Level.TRACE, "[resolveAccessToOperation] operation: "
                    + operation.name());
        }
        List<DataPermission> permissions = dataPermissionDAO.getUserPermission(
                currentUser, entityClass);
        for (DataPermission permission : permissions) {
            Set<PermissionOperation> operations = PermissionOperation
                    .readPermissions(permission.getPermissions());
            if (operations.contains(PermissionOperation.CREATE)) {
                if (LOG.isLoggable(System.Logger.Level.TRACE)) {
                    LOG.log(System.Logger.Level.TRACE, "check permission, passed");
                }
                return;
            }
        }
        LOG.log(System.Logger.Level.INFO, "check permission, no permissions found");
        throw new OperationDeniedException(operation, entityClass);
    }
}
