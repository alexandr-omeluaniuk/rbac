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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.ss.rbac.configuration.EntityManagerProvider;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.entity.DataPermission;
import org.ss.rbac.internal.api.DataPermissionDAO;
import org.ss.rbac.api.ServiceProvider;
import org.ss.rbac.entity.User;

/**
 * Data permission DAO implementation.
 * @author ss
 */
public class DataPermissionDAOImpl implements DataPermissionDAO {
    /** Entity manager provider. */
    private final EntityManagerProvider emProvider = ServiceProvider.load(
            EntityManagerProvider.class);
    @Override
    public DataPermission getDataPermission(PrincipalType principalType, Long principalId,
            Class<? extends Audit> entityClass) {
        EntityManager em = emProvider.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DataPermission> criteria = cb.createQuery(DataPermission.class);
        Root<DataPermission> c = criteria.from(DataPermission.class);
        criteria.select(c).where(
                cb.equal(c.get("principalType"), principalType),
                cb.equal(c.get("principalId"), principalId),
                cb.equal(c.get("entity"), entityClass.getName())
        );
        List<DataPermission> result = em.createQuery(criteria).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public List<DataPermission> getUserPermission(User user, Class<? extends Audit> entityClass) {
        EntityManager em = emProvider.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DataPermission> criteria = cb.createQuery(DataPermission.class);
        Root<DataPermission> c = criteria.from(DataPermission.class);
        List<Predicate> criterias = new ArrayList<>();
        criterias.add(cb.equal(c.get("entity"), entityClass.getName()));
        List<Predicate> orCriterias = new ArrayList<>();
        user.getRoles().forEach((role) -> {
            orCriterias.add(cb.and(
                    cb.equal(c.get("principalId"), role.getId()),
                    cb.equal(c.get("principalType"), PrincipalType.ROLE)
            ));
        });
        orCriterias.add(cb.and(
                cb.equal(c.get("principalId"), user.getId()),
                cb.equal(c.get("principalType"), PrincipalType.USER)
        ));
        criterias.add(cb.or(
                orCriterias.toArray(new Predicate[0])
        ));
        criteria.select(c).where(criterias.toArray(new Predicate[0]));
        return em.createQuery(criteria).getResultList();
    }
}
