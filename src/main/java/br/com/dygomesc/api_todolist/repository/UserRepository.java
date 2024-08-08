package br.com.dygomesc.api_todolist.repository;

import br.com.dygomesc.api_todolist.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
        UserModel findByUserName(String username);

}
