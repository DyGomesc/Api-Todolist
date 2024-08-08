package br.com.dygomesc.api_todolist.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity( name = "tb_users")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column( unique = true )
    private String userName;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
