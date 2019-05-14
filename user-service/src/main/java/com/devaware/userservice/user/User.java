package com.devaware.userservice.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();
    
    @Builder
    public User(Long id, boolean isEnabled, LocalDateTime createdAt, LocalDateTime updatedAt,
    		String name, String username, String password, List<UserRole> userRoles) {
    	super(id, isEnabled, createdAt, updatedAt);
    	this.name = name;
    	this.username = username;
    	this.password = password;
    	this.userRoles.addAll(userRoles);
    }
    
    public void addRole(UserRole userRole) {
    	userRoles.add(userRole);
    	userRole.setUser(this);
    }
    
    public void removeRole(UserRole userRole) {
    	userRole.setUser(null);
    	userRoles.remove(userRole);
    }
}
