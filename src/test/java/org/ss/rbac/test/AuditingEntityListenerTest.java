/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.rbac.test;

import javax.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;
import org.ss.rbac.api.EntityManagerProvider;
import org.ss.rbac.api.ServiceProvider;
import org.ss.rbac.test.entity.Product;

/**
 *
 * @author ss
 */
public class AuditingEntityListenerTest extends AbstractTest {
    private final EntityManagerProvider emProvider = ServiceProvider.load(EntityManagerProvider.class);
    @Test
    public void testAuditing() throws Exception {
        Product product = new Product();
        product.setName("Soap");
        EntityManager em = emProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
        Assert.assertNotNull(product.getId());
        Assert.assertNotNull(product.getCreatedBy());
        Assert.assertNotNull(product.getCreatedDate());
        Assert.assertNull(product.getLastModifiedBy());
        Assert.assertNull(product.getLastModifiedDate());
        product.setName("Apple");
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
        Assert.assertNotNull(product.getLastModifiedBy());
        Assert.assertNotNull(product.getLastModifiedDate());
    }
}
