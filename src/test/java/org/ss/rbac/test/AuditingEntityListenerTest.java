/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.rbac.test;

import javax.persistence.Persistence;
import org.junit.Test;

/**
 *
 * @author ss
 */
public class AuditingEntityListenerTest {
    @Test
    public void testInit() {
        System.out.println("TEST WORKS!!!");
        Persistence.createEntityManagerFactory("rbac_test");
    }
}
