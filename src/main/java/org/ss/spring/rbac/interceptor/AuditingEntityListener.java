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
package org.ss.spring.rbac.interceptor;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ss.spring.rbac.entity.Audit;
import org.ss.spring.rbac.api.UserService;

/**
 * Audit entity listener.
 * @author ss
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AuditingEntityListener {
    /** User service. */
    private UserService userService;
    @PrePersist
    public void prePersist(Audit auditable) {
        auditable.setCreatedBy(userService.currentUser());
        auditable.setCreatedDate(new Date());
    }
    @PreUpdate
    public void preUpdate(Audit auditable) {
        auditable.setLastModifiedBy(userService.currentUser());
        auditable.setLastModifiedDate(new Date());
    }
}
