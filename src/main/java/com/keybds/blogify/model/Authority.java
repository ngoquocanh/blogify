package com.keybds.blogify.model;

import com.keybds.blogify.enums.RoleEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
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
