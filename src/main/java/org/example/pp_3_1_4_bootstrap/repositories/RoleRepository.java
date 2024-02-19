package org.example.pp_3_1_4_bootstrap.repositories;

import org.example.pp_3_1_4_bootstrap.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByName(String roleUser);
}
