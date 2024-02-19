package org.example.pp_3_1_4_bootstrap.repositories;

import org.example.pp_3_1_4_bootstrap.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
