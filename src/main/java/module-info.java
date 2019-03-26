/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

open module spring.rbac {
    requires java.persistence;
    requires java.validation;
    requires java.annotation;
    requires java.sql;
    requires spring.context;
    requires spring.beans;
    exports org.ss.spring.rbac.entity;
    exports org.ss.spring.rbac.api;
    exports org.ss.spring.rbac.interceptor;
}
