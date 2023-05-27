package com.vendingmachine.vendingmachine.domains.user.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.List;

@Entity
@Table(
        name = "user",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="user_username_unique",
                        columnNames = "username"
                )
        }
)
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name="user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "user_id_seq")
    private Integer id;

    @Column(
            nullable = false
    )
    private String username;

    @Column(
            nullable = false
    )
    private String password;

    @Column(
            nullable = false
    )
    private Integer deposit;

    public User(Integer id, String username, String password, Integer deposit) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deposit = deposit;
    }

    public User(String username, String password, Integer deposit) {
        this.username = username;
        this.password = password;
        this.deposit = deposit;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(deposit, user.deposit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, deposit);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
