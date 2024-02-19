package org.example.pp_3_1_4_bootstrap.services;

import org.example.pp_3_1_4_bootstrap.models.User;
import org.example.pp_3_1_4_bootstrap.repositories.RoleRepository;
import org.example.pp_3_1_4_bootstrap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    private final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }


    //
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }


        // Role roleUser = roleRepository.findOneByName("ROLE_USER"); // альтернативный вариант
        //Set<Role> roles = new HashSet<>(); // т.к. User.setRoles требует Set
        // roles.add(roleRepository.findOneByName("ROLE_USER")); // создадим Set с одним значением
        user.setRoles(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setAge(user.getAge());
//        user.setFirstName(user.getFirstName());
//        user.setLastName(user.getLastName());
        user.setRoles(user.getRoles());
        userRepository.save(user);

    }

}
