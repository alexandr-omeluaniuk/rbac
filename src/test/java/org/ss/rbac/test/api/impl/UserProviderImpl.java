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
package org.ss.rbac.test.api.impl;

import org.ss.rbac.api.EntityManagerProvider;
import org.ss.rbac.api.ServiceProvider;
import org.ss.rbac.api.UserProvider;
import org.ss.rbac.entity.User;

/**
 *
 * @author ss
 */
public class UserProviderImpl implements UserProvider {
    private final EntityManagerProvider em = ServiceProvider.load(EntityManagerProvider.class);
    private static User user;
    @Override
    public User getCurrentUser() {
        if (user == null) {
            user = new User();
            user.setActive(true);
            user.setFirstname("Gareth");
            user.setLastname("Richardson");
            user.setPassword("paSSword");
            user.setUsername("gareth.richardson@test.com");
            em.getEntityManager().persist(user);
        }
        return user;
    }
}
