/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module ss.rbac {
    requires java.persistence;
    requires java.validation;
    requires java.logging;
    
    exports org.ss.rbac.entity;
    exports org.ss.rbac.api;
    exports org.ss.rbac.configuration;
    exports org.ss.rbac.constant;
    
    opens org.ss.rbac.entity;
    
    uses org.ss.rbac.configuration.UserProvider;
    uses org.ss.rbac.configuration.EntityManagerProvider;
    uses org.ss.rbac.internal.api.DataPermissionDAO;
    uses org.ss.rbac.api.PermissionService;
    uses org.ss.rbac.internal.api.CoreDAO;
    
    provides java.lang.System.LoggerFinder 
            with org.ss.rbac.internal.api.RbacLoggerFinder;
    provides org.ss.rbac.internal.api.DataPermissionDAO 
            with org.ss.rbac.internal.api.impl.DataPermissionDAOImpl;
    provides org.ss.rbac.api.PermissionService 
            with org.ss.rbac.internal.api.impl.RbacPermissionServiceImpl;
    provides org.ss.rbac.internal.api.CoreDAO 
            with org.ss.rbac.internal.api.impl.CoreDAOImpl;
}
