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

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.ss.rbac.configuration.UserProvider;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.internal.api.ServiceProvider;

/**
 * Audit entity listener.
 * @author ss
 */
public class AuditingEntityListener {
    /** User service. */
    private final UserProvider userProvider = ServiceProvider.load(UserProvider.class);
    /**
     * Handle pre persist operations.
     * @param auditable auditable entity.
     */
    @PrePersist
    public void prePersist(Audit auditable) {
        auditable.setCreatedBy(userProvider.getCurrentUser());
        auditable.setCreatedDate(new Date());
    }
    /**
     * Handle pre update operations.
     * @param auditable auditable entity.
     */
    @PreUpdate
    public void preUpdate(Audit auditable) {
        auditable.setLastModifiedBy(userProvider.getCurrentUser());
        auditable.setLastModifiedDate(new Date());
    }
}
