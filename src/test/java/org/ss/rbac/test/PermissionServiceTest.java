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
import org.junit.Assert;
import org.junit.Test;
import org.ss.rbac.api.PermissionService;
import org.ss.rbac.api.ServiceProvider;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.constant.PrincipalType;
import org.ss.rbac.entity.User;
import org.ss.rbac.test.entity.Product;

/**
 * @see org.ss.rbac.api.PermissionService
 * @author ss
 */
public class PermissionServiceTest extends DatabaseTest {
    /** Logger. */
    private static final System.Logger LOG =
            System.getLogger(PermissionServiceTest.class.getName());
    /** Service. */
    private final PermissionService service = ServiceProvider.load(PermissionService.class);
    @Test
    public void testSetDataPermissions() {
        LOG.log(System.Logger.Level.INFO, "----------------- setDataPermissions -----------------");
        User user = userProvider.getCurrentUser();
        PrincipalType principalType = PrincipalType.USER;
        Set<PermissionOperation> permissions = new HashSet<>();
        permissions.add(PermissionOperation.READ);
        permissions.add(PermissionOperation.DELETE);
        service.setDataPermissions(permissions, principalType, user.getId(), Product.class);
        LOG.log(System.Logger.Level.INFO, "----------------- getDataPermissions -----------------");
        Set<PermissionOperation> productPermissions = service.getDataPermissions(
                principalType, user.getId(), Product.class);
        Assert.assertEquals(2, productPermissions.size());
        boolean isRead = false, isDelete = false, isCreate = false, isUpdate = false;
        for (PermissionOperation permission : productPermissions) {
            if (permission == PermissionOperation.READ) {
                isRead = true;
            }
            if (permission == PermissionOperation.DELETE) {
                isDelete = true;
            }
            if (permission == PermissionOperation.CREATE) {
                isCreate = true;
            }
            if (permission == PermissionOperation.UPDATE) {
                isUpdate = true;
            }
        }
        Assert.assertTrue(isRead);
        Assert.assertTrue(isDelete);
        Assert.assertFalse(isUpdate);
        Assert.assertFalse(isCreate);
    }
}
