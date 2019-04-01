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

import java.lang.System.Logger.Level;
import java.util.Collections;
import java.util.Set;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.entity.DataPermission;
import org.ss.rbac.internal.api.CoreDAO;
import org.ss.rbac.internal.api.DataPermissionDAO;
import org.ss.rbac.api.ServiceProvider;
import org.ss.rbac.api.PermissionService;

/**
 * RBAC permission service implementation.
 * @author ss
 */
public class RbacPermissionServiceImpl implements PermissionService {
    /** Logger. */
    private static final System.Logger LOG =
            System.getLogger(RbacPermissionServiceImpl.class.getName());
    /** No permissions. */
    private static final byte NO_PERMISSIONS = 0x00;
    /** Data permission DAO. */
    private final DataPermissionDAO dataPermissionDAO =
            ServiceProvider.load(DataPermissionDAO.class);
    /** Core DAO. */
    private final CoreDAO coreDAO = ServiceProvider.load(CoreDAO.class);
    @Override
    public void setDataPermissions(Set<PermissionOperation> permissions,
            PrincipalType principalType, Long principalId, Class<? extends Audit> entityClass) {
        LOG.log(Level.INFO, "set data permissions for class [{0}], principal type [{1}], "
                + "principal ID [{2}]",
                new Object[] { entityClass.getName(), principalType.name(), principalId });
        DataPermission permission = dataPermissionDAO.getDataPermission(
                principalType, principalId, entityClass);
        if (permission == null) {
            LOG.log(Level.INFO, "create new data permission");
            permission = new DataPermission();
            permission.setEntity(entityClass.getName());
            permission.setPermissions(NO_PERMISSIONS);
            permission.setPrincipalId(principalId);
            permission.setPrincipalType(principalType);
            permission = coreDAO.create(permission);
        }
        byte mask = NO_PERMISSIONS;
        for (PermissionOperation operation : permissions) {
            mask = (byte) (mask | operation.getCode());
            LOG.log(Level.INFO, "permission [{0}]", new Object[]{ operation.name() });
        }
        permission.setPermissions(mask);
        coreDAO.update(permission);
    }
    @Override
    public Set<PermissionOperation> getDataPermissions(PrincipalType principalType,
            Long principalId, Class<? extends Audit> entityClass) {
        LOG.log(Level.TRACE, "get data permissions for class [{0}], principal type [{1}], "
                + "principal ID [{2}]",
                new Object[] { entityClass.getName(), principalType.name(), principalId });
        DataPermission permission = dataPermissionDAO.getDataPermission(
                principalType, principalId, entityClass);
        LOG.log(Level.TRACE, permission);
        if (permission == null) {
            return Collections.emptySet();
        } else {
            return PermissionOperation.readPermissions(permission.getPermissions());
        }
    }
}
