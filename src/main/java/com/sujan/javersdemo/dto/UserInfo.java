package com.sujan.javersdemo.dto;

import com.sujan.javersdemo.entities.ContactData;
import lombok.Data;

@Data
public class UserInfo {
    private String id;
    private String username;
    private String password;
    private String email;
    private ContactData contactData;
}
