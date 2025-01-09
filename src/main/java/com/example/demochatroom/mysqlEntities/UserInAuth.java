package com.example.demochatroom.mysqlEntities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data

@Entity
@Table(name = "UserInAuth")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String password;

    private String email;

    @ManyToMany(fetch =FetchType.EAGER)
    private Set<Role> roles;

//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles=new HashSet<>();
}
