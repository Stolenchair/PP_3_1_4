package org.example.pp_3_1_4_bootstrap.models;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "t_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "usermame")
    @NotEmpty(message = "Поле не должно быть пустым")
    @Email

    @Size(min = 2, max = 100, message = "Не меньше 2 знаков")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Поле не должно быть пустым")
    @UniqueElements
    @Size(min = 2, max = 100, message = "Не меньше 2 знаков")
    private String password;

    @Column(name = "firsname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    @NotEmpty(message = "Поле не должно быть пустым")
//    @Positive
    @Min(value = 15, message = "Здесь нет места детям")
    @Max(value = 100, message = "Старикам тут не место")
    private int age;

    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getRole() {
        return getRoles().toString().replace("[", "")
                .replace("]", "").replace("ROLE_", "");
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
