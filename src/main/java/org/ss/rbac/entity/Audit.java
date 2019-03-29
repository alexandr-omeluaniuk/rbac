/*
 * Copyright (C) 2018 Wisent Media
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ss.rbac.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ss.rbac.api.AuditingEntityListener;

/**
 * Auditing superclass.
 * @author Alexandr Omeluaniuk
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit {
// ================================== FIELDS ======================================================
    /** Created by. */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;
    /** Created date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;
    /** Last modified by. */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_modified_by")
    private User lastModifiedBy;
    /** Last modified date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
// ================================= SET & GET ====================================================
    /**
     * @return the createdBy
     */
    public User getCreatedBy() {
        return createdBy;
    }
    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * @return the lastModifiedBy
     */
    public User getLastModifiedBy() {
        return lastModifiedBy;
    }
    /**
     * @param lastModifiedBy the lastModifiedBy to set
     */
    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    /**
     * @return the lastModifiedDate
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    /**
     * @param lastModifiedDate the lastModifiedDate to set
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
