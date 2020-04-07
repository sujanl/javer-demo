package com.sujan.javersdemo.service;

import com.sujan.javersdemo.dto.UserInfo;
import com.sujan.javersdemo.entities.Users;
import com.sujan.javersdemo.exception.AlreadyExistException;
import com.sujan.javersdemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(UserInfo userInfo) {
        Users usersToInsert = new Users();
        //val
        checkIfUsernameAlreadyExist(userInfo);
        checkIfEmailAlreadyExist(userInfo);

        BeanUtils.copyProperties(userInfo, usersToInsert);
        LOGGER.info("User to insert: {}",usersToInsert);
        return userRepository.save(usersToInsert).getId();
    }

    public String update(UserInfo userInfo) {
        Users usersToUpdate = new Users();
        //val
        checkIfUsernameAlreadyExist(userInfo);
        checkIfEmailAlreadyExist(userInfo);
        BeanUtils.copyProperties(userInfo, usersToUpdate);
        LOGGER.info("User to insert: {}",usersToUpdate);
        return userRepository.save(usersToUpdate).getId();
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    private void checkIfUsernameAlreadyExist(UserInfo userInfo) {
        if (!userRepository.findByUsernameAndIdNot(userInfo.getUsername(), userInfo.getId()).isEmpty())
            throw new AlreadyExistException("UserName already exist");
    }

    private void checkIfEmailAlreadyExist(UserInfo userInfo) {
        if (!userRepository.findByEmailAndIdNot(userInfo.getEmail(), userInfo.getId()).isEmpty())
            throw new AlreadyExistException("Email already exist");
    }

}
