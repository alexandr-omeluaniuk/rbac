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

import java.lang.System.Logger.Level;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import org.ss.rbac.api.RbacApplication;
import org.ss.rbac.api.WithSecurityContext;
import org.ss.rbac.api.WithoutSecurityContext;
import org.ss.rbac.constant.PermissionOperation;
import org.ss.rbac.entity.Audit;
import org.ss.rbac.entity.DataPermission;
import org.ss.rbac.entity.User;
import org.ss.rbac.exception.OperationDeniedException;
import org.ss.rbac.internal.api.DataPermissionDAO;
import org.ss.rbac.internal.api.PermissionResolver;
import org.ss.rbac.internal.api.ServiceProvider;

/**
 * Permission resolver implementation.
 * @author ss
 */
public class PermissionResolverImpl implements PermissionResolver {
    /** Logger. */
    private static final System.Logger LOG =
            System.getLogger(PermissionResolverImpl.class.getName());
    /** Data permission DAO. */
    private final DataPermissionDAO dataPermissionDAO =
            ServiceProvider.load(DataPermissionDAO.class);
    @Override
    public void resolveAccessToOperation(Class<? extends Audit> entityClass,
            PermissionOperation operation) throws OperationDeniedException {
        if (isSecurityContext()) {
            User currentUser = RbacApplication.getConfiguration().getCurrentUser();
            if (LOG.isLoggable(System.Logger.Level.TRACE)) {
                LOG.log(Level.TRACE, "[resolveAccessToOperation] entity class: "
                        + entityClass.getName());
                LOG.log(Level.TRACE, "[resolveAccessToOperation] user: " + currentUser);
                LOG.log(Level.TRACE, "[resolveAccessToOperation] operation: " + operation.name());
            }
            List<DataPermission> permissions = dataPermissionDAO.getUserPermission(
                    currentUser, entityClass);
            for (DataPermission permission : permissions) {
                Set<PermissionOperation> operations = PermissionOperation
                        .readPermissions(permission.getPermissions());
                if (operations.contains(PermissionOperation.CREATE)) {
                    if (LOG.isLoggable(Level.TRACE)) {
                        LOG.log(Level.TRACE, "[resolveAccessToOperation] passed");
                    }
                    return;
                }
            }
            LOG.log(Level.INFO, "[resolveAccessToOperation] no permissions found");
            throw new OperationDeniedException(operation, entityClass);
        }
    }
// ==================================== PRIVATE ===================================================
    /**
     * Check if code is running in security context.
     * @return true if is running in security context.
     */
    private boolean isSecurityContext() {
        String[] scanPackages = RbacApplication.getConfiguration().scanPackages();
        if (LOG.isLoggable(Level.TRACE)) {
            LOG.log(Level.TRACE, "[isSecurityContext] packages for scan: "
                    + String.join(",", scanPackages));
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            for (StackTraceElement element : stackTraceElements) {
                String className = element.getClassName();
                if (isClassUnderLibraryControl(className, scanPackages)) {
                    Class clazz = Class.forName(className);
                    boolean isClassSecured = isClassSecured(clazz);
                    Boolean isMethodSecured = isClassMethodSecured(clazz, element.getMethodName());
                    if (isClassSecured) {
                        if (isMethodSecured == null || Boolean.TRUE.equals(isMethodSecured)) {
                            LOG.log(Level.TRACE, "[isSecurityContext] security context found "
                                    + "for class [{0}] and method [{1}]",
                                    element.getClassName(), element.getMethodName());
                            return true;
                        } else if (Boolean.FALSE.equals(isMethodSecured)) {
                            LOG.log(Level.TRACE, "[isSecurityContext] explicit exception from "
                                    + "security context for class [{0}] and method [{1}]",
                                    element.getClassName(), element.getMethodName());
                            return false;
                        }
                    } else {
                        if (Boolean.TRUE.equals(isMethodSecured)) {
                            LOG.log(Level.TRACE, "[isSecurityContext] security context found "
                                    + "for method [{0}] in class [{1}]",
                                    element.getMethodName(), element.getClassName());
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.log(Level.ERROR, "Security context can not be defined!", e);
            throw new RuntimeException("Security context can not be defined!", e);
        }
        LOG.log(Level.TRACE, "[isSecurityContext] security context not found");
        return false;
    }
    /**
     * Check if class under library control.
     * @param className class name.
     * @param scanPackages scan packages.
     * @return true if under control.
     */
    private boolean isClassUnderLibraryControl(String className, String[] scanPackages) {
        for (String pack : scanPackages) {
            if (className.startsWith(pack)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if class secured.
     * @param clazz class.
     * @return true if class in security context.
     */
    private boolean isClassSecured(Class clazz) {
        if (clazz == null) {
            return false;
        }
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation anno : annotations) {
            if (WithSecurityContext.class.equals(anno.annotationType())) {
                LOG.log(Level.TRACE, "[isSecurityContext] class [{0}] in security context",
                        clazz.getName());
                return true;
            }
        }
        return isClassSecured(clazz.getSuperclass());
    }
    /**
     * Check if method in security context.
     * @param clazz class.
     * @param methodName method name.
     * @return true if method in security context.
     */
    private Boolean isClassMethodSecured(Class clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            if (methodName.equals(method.getName())) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation anno : annotations) {
                    if (WithSecurityContext.class.equals(anno.annotationType())) {
                        LOG.log(Level.TRACE, "[isSecurityContext] method [{0}] in security context",
                                method.getName());
                        return Boolean.TRUE;
                    } else if (WithoutSecurityContext.class.equals(anno.annotationType())) {
                        LOG.log(Level.TRACE,
                                "[isSecurityContext] method [{0}] excluded from security context",
                                method.getName());
                        return Boolean.FALSE;
                    }
                }
            }
        }
        return null;
    }
}
