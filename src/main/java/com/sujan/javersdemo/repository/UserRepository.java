package com.sujan.javersdemo.repository;

import com.sujan.javersdemo.entities.Users;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface UserRepository extends MongoRepository<Users, String>{
    Optional<Users> findByUsernameAndIdNot(String username, String id);

    Optional<Object> findByEmailAndIdNot(String email, String id);
}
