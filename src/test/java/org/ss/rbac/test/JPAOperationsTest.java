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
import javax.persistence.EntityTransaction;
import org.junit.Assert;
import org.junit.Test;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.exception.OperationDeniedException;
import org.ss.rbac.test.entity.Product;

/**
 * Test for intercepted JPA methods.
 * @author ss
 */
public class JPAOperationsTest extends DatabaseTest {
    /** Logger. */
    private static final System.Logger LOG =
            System.getLogger(JPAOperationsTest.class.getName());
    @Test
    public void testSave() {
        LOG.log(System.Logger.Level.INFO, "----------------- testSave ---------------------------");
        // without permissions
        Product product = new Product();
        product.setName("Soap");
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(product);
            tx.commit();
            Assert.fail("Not permitted!");
        } catch (Exception ex) {
            tx.rollback();
            Assert.assertTrue("Incorrect exception!",
                    isCorrectException(ex, PermissionOperation.CREATE));
        }
        // with permissions
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.CREATE);
        permissionService.setDataPermissions(permissions, PrincipalType.USER, currentUser().getId(),
                Product.class);
        tx = em.getTransaction();
        tx.begin();
        em.persist(product);
        tx.commit();
        Assert.assertNotNull(product.getId());
    }
    @Test
    public void testMerge() {
        LOG.log(System.Logger.Level.INFO, "----------------- testMerge --------------------------");
        EntityManager em = getEntityManager();
        Product product = prepareProduct(em, true);
        EntityTransaction tx = em.getTransaction();
        // without permissions
        final String name = "Apple";
        product.setName(name);
        try {
            tx.begin();
            em.merge(product);
            tx.commit();
            Assert.fail("Not permitted!");
        } catch (Exception ex) {
            tx.rollback();
            Assert.assertTrue("Incorrect exception!",
                    isCorrectException(ex, PermissionOperation.UPDATE));
        }
        // with permissions
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.UPDATE);
        permissionService.setDataPermissions(permissions, PrincipalType.USER, currentUser().getId(),
                Product.class);
        tx = em.getTransaction();
        tx.begin();
        em.merge(product);
        tx.commit();
        Assert.assertEquals(name, product.getName());
    }
    @Test
    public void testRemove() {
        LOG.log(System.Logger.Level.INFO, "----------------- testRemove -------------------------");
        EntityManager em = getEntityManager();
        Product product = prepareProduct(em, true);
        EntityTransaction tx = em.getTransaction();
        // without permissions
        try {
            tx.begin();
            em.remove(product);
            tx.commit();
            Assert.fail("Not permitted!");
        } catch (Exception ex) {
            tx.rollback();
            Assert.assertTrue("Incorrect exception!",
                    isCorrectException(ex, PermissionOperation.DELETE));
        }
        // with permissions
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.DELETE);
        permissions.add(PermissionOperation.READ);
        permissionService.setDataPermissions(permissions, PrincipalType.USER, currentUser().getId(),
                Product.class);
        tx = em.getTransaction();
        tx.begin();
        product = em.find(Product.class, product.getId());
        em.remove(product);
        tx.commit();
    }
    @Test
    public void testFind() {
        LOG.log(System.Logger.Level.INFO, "----------------- testFind ---------------------------");
        EntityManager em = getEntityManager();
        Product product = prepareProduct(em, false);
        // without permissions
        try {
            em.find(Product.class, product.getId());
            Assert.fail("Not permitted!");
        } catch (Exception ex) {
            Assert.assertTrue("Incorrect exception!",
                    isCorrectException(ex, PermissionOperation.READ));
        }
        // with permissions
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.READ);
        permissionService.setDataPermissions(permissions, PrincipalType.USER, currentUser().getId(),
                Product.class);
        product = em.find(Product.class, product.getId());
        Assert.assertNotNull(product);
    }
// ========================================= PRIVATE ==============================================
    private boolean isCorrectException(Throwable e, PermissionOperation operation) {
        if (e == null) {
            return false;
        }
        if (e instanceof OperationDeniedException) {
            return operation == ((OperationDeniedException) e).getOperation();
        } else {
            return isCorrectException(e.getCause(), operation);
        }
    }
    private Product prepareProduct(EntityManager em, boolean isRead) {
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.CREATE);
        if (isRead) {
            permissions.add(PermissionOperation.READ);
        }
        permissionService.setDataPermissions(permissions, PrincipalType.USER, currentUser().getId(),
                Product.class);
        Product product = new Product();
        product.setName("Soap");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(product);
        tx.commit();
        return product;
    }
}
