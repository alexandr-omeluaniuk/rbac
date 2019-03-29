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
package org.ss.rbac.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.ss.rbac.constant.PrincipalType;

/**
 * Data permission.
 * @author ss
 */
@Entity
@Table(name = "rbac_permission_data")
public class DataPermission implements Serializable {
    /** Default UID. */
    private static final long serialVersionUID = 1L;
// ==================================== FIELDS ====================================================
    /** ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Security principal. */
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sid_principal", nullable = false)
    private PrincipalType sidPrincipal;
    /** SID. */
    @NotNull
    @Column(name = "sid_id", nullable = false)
    private Long sidIdentity;
    /** Entity class name. */
    @NotNull
    @Size(max = 255)
    @Column(name = "entity", nullable = false, length = 255)
    private String entity;
    /** Permissions. */
    @NotNull
    @Column(name = "permissions", nullable = false)
    private byte permissions;
// ==================================== SET & GET =================================================
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
     * @return the sidPrincipal
     */
    public PrincipalType getSidPrincipal() {
        return sidPrincipal;
    }
    /**
     * @param sidPrincipal the sidPrincipal to set
     */
    public void setSidPrincipal(PrincipalType sidPrincipal) {
        this.sidPrincipal = sidPrincipal;
    }
    /**
     * @return the sidIdentity
     */
    public Long getSidIdentity() {
        return sidIdentity;
    }
    /**
     * @param sidIdentity the sidIdentity to set
     */
    public void setSidIdentity(Long sidIdentity) {
        this.sidIdentity = sidIdentity;
    }
    /**
     * @return the entity
     */
    public String getEntity() {
        return entity;
    }
    /**
     * @param entity the entity to set
     */
    public void setEntity(String entity) {
        this.entity = entity;
    }
    /**
     * @return the permissions
     */
    public byte getPermissions() {
        return permissions;
    }
    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(byte permissions) {
        this.permissions = permissions;
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
        if (!(object instanceof DataPermission)) {
            return false;
        }
        DataPermission other = (DataPermission) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }
    @Override
    public String toString() {
        return "org.ss.rbac.entity.DataPermission[ id=" + getId() + " ]";
    }
}
