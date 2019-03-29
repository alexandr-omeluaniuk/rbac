/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.rbac.test;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.junit.Test;
import org.ss.rbac.api.UserProvider;

/**
 *
 * @author ss
 */
public class AuditingEntityListenerTest extends AbstractTest {
    @Test
    public void testInit() {
        ServiceLoader<UserProvider> serviceLoader = ServiceLoader.load(UserProvider.class);
        System.out.println("=====================================================================");
        Iterator<UserProvider> itr = serviceLoader.iterator();
        System.out.println(itr.hasNext());
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("=====================================================================");
    }
}
