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
package org.ss.rbac.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import org.ss.rbac.internal.api.ServiceProvider;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.exception.OperationDeniedException;
import org.ss.rbac.internal.api.PermissionResolver;

/**
 * Performs data access checks.
 * @author ss
 */
public final class CrudOperationsListener {
    /** Permission resolver. */
    private final PermissionResolver permissionResolver =
            ServiceProvider.load(PermissionResolver.class);
// ================================== PUBLIC ======================================================
    @PrePersist
    public void prePersist(Audit auditable) throws OperationDeniedException {
        permissionResolver.resolveAccessToOperation(auditable.getClass(),
                PermissionOperation.CREATE);
    }
    @PreUpdate
    public void preUpdate(Audit auditable) throws OperationDeniedException {
        permissionResolver.resolveAccessToOperation(auditable.getClass(),
                PermissionOperation.UPDATE);
    }
    @PreRemove
    public void preRemove(Audit auditable) throws OperationDeniedException {
        permissionResolver.resolveAccessToOperation(auditable.getClass(),
                PermissionOperation.DELETE);
    }
}
