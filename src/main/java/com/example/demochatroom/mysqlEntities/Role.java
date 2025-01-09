package com.example.demochatroom.mysqlEntities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Role")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    private String name;
}
