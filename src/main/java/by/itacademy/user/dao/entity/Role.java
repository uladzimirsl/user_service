package by.itacademy.user.dao.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "security")
public class Role implements GrantedAuthority {

    private UUID uuid;
    private String roleName;

    public Role() {
    }

    public Role(UUID uuid, String roleName) {
        this.uuid = uuid;
        this.roleName = roleName;
    }

    @Id
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setAuthority(String roleName) {
        this.roleName = roleName;
    }

    @Override
    @Column(name = "role_name", nullable = false)
    public String getAuthority() {
        return roleName;
    }

}
