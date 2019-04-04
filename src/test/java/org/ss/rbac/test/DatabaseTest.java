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
package org.ss.rbac.test;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.BeforeClass;
import org.ss.rbac.api.PermissionService;
import org.ss.rbac.api.ServiceProvider;
import org.ss.rbac.configuration.EntityManagerProvider;
import org.ss.rbac.configuration.UserProvider;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.entity.User;
import org.ss.rbac.test.api.impl.EntityManagerProviderImpl;
import org.ss.rbac.test.api.impl.UserProviderImpl;

/**
 * Abstract test with database access.
 * @author ss
 */
public abstract class DatabaseTest {
    /** User provider. */
    protected UserProvider userProvider = ServiceProvider.load(UserProvider.class);
    /** Permission service. */
    protected final PermissionService permissionService =
            ServiceProvider.load(PermissionService.class);
    @BeforeClass
    public static void before() {
        User user = new User();
        user.setActive(true);
        user.setFirstname("Gareth");
        user.setLastname("Richardson");
        user.setPassword("paSSword");
        user.setUsername("gareth.richardson@test.com");
        user.setRoles(new HashSet<>());
        EntityManagerProvider emProvider = new EntityManagerProviderImpl();
        EntityManager em = emProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        UserProviderImpl.auth(user);
    }
    protected void setAllPermissionsForCurrentUser(Class<? extends Audit> entityClass) {
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.READ);
        permissions.add(PermissionOperation.DELETE);
        permissions.add(PermissionOperation.UPDATE);
        permissions.add(PermissionOperation.CREATE);
        permissionService.setDataPermissions(permissions, PrincipalType.USER,
                userProvider.getCurrentUser().getId(), entityClass);
    }
}
