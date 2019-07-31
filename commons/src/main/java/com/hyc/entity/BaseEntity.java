package com.hyc.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 8381257712614878361L;

    @Id
    @GeneratedValue(generator = "IDWorker")
    @GenericGenerator(name = "IDWorker", strategy = "com.hyc.utils.SnowflakeIdWorker")
    @Column(unique = true, length = 18)
    private ID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "last_modified_at", nullable = false)
    @LastModifiedDate
    private Date lastModifiedAt;

    @Column(name = "last_modified_by", nullable = false)
    @LastModifiedBy
    private String lastModifiedBy;

}
