package com.devaware.userservice.role;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.devaware.userservice.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "name")
    private String name;
    
    @Column(name = "voter_name")
    private String voterName;
    
    @Builder
    public Role(Long id, boolean isEnabled, LocalDateTime createdAt, LocalDateTime updatedAt,
    		String name, String voterName) {
    	super(id, isEnabled, createdAt, updatedAt);
    	this.name = name;
    	this.voterName = voterName;
    }

}
