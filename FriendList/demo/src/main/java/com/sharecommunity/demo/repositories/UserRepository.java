package com.sharecommunity.demo.repositories;

import java.util.List;

import com.sharecommunity.demo.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByIdNot(Long id);
}