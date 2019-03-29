/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.rbac.test;

import org.junit.Test;
import org.ss.rbac.api.EntityManagerProvider;
import org.ss.rbac.api.ServiceProvider;

/**
 *
 * @author ss
 */
public class AuditingEntityListenerTest extends AbstractTest {
    private final EntityManagerProvider em = ServiceProvider.load(EntityManagerProvider.class);
    @Test
    public void testInit() {
        
    }
}
