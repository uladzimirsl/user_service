package by.itacademy.user.dao.api;

import by.itacademy.user.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByAuthority(String roleName);
}
