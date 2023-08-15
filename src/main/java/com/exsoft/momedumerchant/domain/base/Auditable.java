package com.exsoft.momedumerchant.domain.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * 
 * @author DELL
 * @param <U> 
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {

  private static final long serialVersionUID = 1;

  // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringUtils.DF_DD_MM_YYYY_HH_MM_SS)
  @Column(name = "created_date", nullable = false, updatable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreatedDate
//  @Temporal(TemporalType.TIMESTAMP)
  protected Instant createdDate;

  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringUtils.DF_DD_MM_YYYY_HH_MM_SS)
  @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @LastModifiedDate
//  @Temporal(TemporalType.TIMESTAMP)
  protected Instant modifiedDate;

  @Column(name = "created_by", nullable = false, updatable = false,
      columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
  @CreatedBy
  protected U createdBy;

  @Column(name = "modified_by", nullable = false,
      columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
  @LastModifiedBy
  protected U modifiedBy;
}
