package edu.hsalbsig.swarch.justintime.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class TraceableEntity {
    @Column(name = "createdOn", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    
    @PrePersist
    private void prePersist() {
        createdOn = new Date();
    }
    
    @PreUpdate
    private void preUpdate() {
        updatedOn = new Date();
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }
}
