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

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.ss.rbac.internal.api.EntityManagerProvider;
import org.ss.rbac.internal.api.CoreDAO;
import org.ss.rbac.internal.api.ServiceProvider;

/**
 * Core DAO implementation.
 * @author ss
 */
public class CoreDAOImpl implements CoreDAO {
    /** Entity manager provider. */
    private final EntityManagerProvider emProvider = ServiceProvider.load(
            EntityManagerProvider.class);
    @Override
    public <T> T create(T entity) {
        EntityManager em = emProvider.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        return entity;
    }
    @Override
    public <T> T update(T entity) {
        EntityManager em = emProvider.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(entity);
        tx.commit();
        return entity;
    }
}
