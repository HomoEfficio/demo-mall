package skplanet.recopick.demo.mall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "last_modified_at", updatable = true)
    @JsonIgnore
    private LocalDateTime lastModifiedDateTime;

//    @CreatedBy
//    private Member creator;
//
//    @LastModifiedBy
//    private Member modifier;
}