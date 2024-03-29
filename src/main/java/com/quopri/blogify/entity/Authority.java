package com.quopri.blogify.entity;

import com.quopri.blogify.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority implements GrantedAuthority {

    public Authority() {
    }

    public Authority(RoleEnum roleEnum) {
        this.setId(roleEnum.getKey());
        this.setAuthority(roleEnum.getValue());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "authority")
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
