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

import java.util.Set;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.entity.Audit;

/**
 * RBAC permission service.
 * @author ss
 */
public interface PermissionService {
    /**
     * Set permissions for data.
     * @param permissions permitted operations.
     * @param principalType principal type.
     * @param principalId principal identity.
     * @param entityClass applied for data.
     */
    void setDataPermissions(Set<PermissionOperation> permissions, PrincipalType principalType,
            Long principalId, Class<? extends Audit> entityClass);
    /**
     * Get data permissions.
     * @param principalType principal type.
     * @param principalId principal identity.
     * @param entityClass data entity class.
     * @return permitted operations.
     */
    Set<PermissionOperation> getDataPermissions(PrincipalType principalType,
            Long principalId, Class<? extends Audit> entityClass);
}
