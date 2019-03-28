/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module ss.rbac {
    requires java.persistence;
    requires java.validation;
    exports org.ss.rbac.entity;
    exports org.ss.rbac.api;
    uses org.ss.rbac.api.UserProvider;
}
