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

import java.lang.System.Logger.Level;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import org.junit.After;
import org.junit.BeforeClass;
import org.ss.rbac.api.Configuration;
import org.ss.rbac.api.PermissionService;
import org.ss.rbac.api.RbacApplication;
import org.ss.rbac.api.WithSecurityContext;
import org.ss.rbac.api.WithoutSecurityContext;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.entity.DataPermission;
import org.ss.rbac.entity.User;
import org.ss.rbac.test.api.impl.RbacConfigurationProvider;
import org.ss.rbac.test.api.impl.TestServiceProvider;
import org.ss.rbac.test.entity.Product;

/**
 * Abstract test with database access.
 * @author ss
 */
@WithSecurityContext
public abstract class DatabaseTest {
    /** Logger. */
    private static final System.Logger LOG = System.getLogger(DatabaseTest.class.getName());
    /** Factory. */
    private static EntityManagerFactory emf = null;
    /** Configuration. */
    protected static Configuration config = new RbacConfigurationProvider();
    /** Permission service. */
    protected final PermissionService permissionService =
            TestServiceProvider.load(PermissionService.class);
    @BeforeClass
    public static void before() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("rbac_test");
            emf = RbacApplication.bootstrap(emf, config);
        }
        User user = new User();
        user.setActive(true);
        user.setFirstname("Gareth");
        user.setLastname("Richardson");
        user.setPassword("paSSword");
        user.setUsername("gareth.richardson@test.com");
        user.setRoles(new HashSet<>());
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        RbacConfigurationProvider.auth(user);
    }
    @After
    @WithoutSecurityContext
    public void clearDatabase() {
        LOG.log(Level.DEBUG, ">> clear database after test");
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Class[] classes = new Class[] { Product.class, DataPermission.class };
        for (Class cl : classes) {
            LOG.log(Level.DEBUG, "> handle class [{0}]", cl.getName());
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaDelete query = cb.createCriteriaDelete(cl);
            Root root = query.from(cl);
            query.where(cb.isNotNull(root.get("id")));
            em.createQuery(query).executeUpdate();
        }
        tx.commit();
        LOG.log(Level.DEBUG, ">> clear database completed");
    }
    protected void setAllPermissionsForCurrentUser(Class<? extends Audit> entityClass) {
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.READ);
        permissions.add(PermissionOperation.DELETE);
        permissions.add(PermissionOperation.UPDATE);
        permissions.add(PermissionOperation.CREATE);
        permissionService.setDataPermissions(permissions, PrincipalType.USER,
                config.getCurrentUser().getId(), entityClass);
    }
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    protected User currentUser() {
        return config.getCurrentUser();
    }
}
