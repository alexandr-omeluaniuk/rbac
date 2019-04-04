/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.rbac.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User model.
 * @author ss
 */
@Entity
@Table(name = "rbac_user")
public class User implements Serializable {
    /** Default UID. */
    private static final long serialVersionUID = 1L;
// =================================== FIELDS =====================================================
    /** ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** First name. */
    @NotNull
    @Size(min = 100)
    @Column(name = "firstname", length = 100, nullable = false)
    private String firstname;
    /** Last name. */
    @NotNull
    @Size(min = 100)
    @Column(name = "lastname", length = 100, nullable = false)
    private String lastname;
    /** Username. */
    @NotNull
    @Size(min = 100)
    @Column(name = "username", length = 100, nullable = false)
    private String username;
    /** Password. */
    @NotNull
    @Size(min = 255)
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    /** Is active. */
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active;
    /** Roles. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rbac_users_roles")
    private Set<Role> roles;
// ================================================================================================
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }
    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
// ================================================================================================
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }
    @Override
    public String toString() {
        return "org.ss.spring.rbac.entity.User[ id=" + getId() + " ]";
    }
}
