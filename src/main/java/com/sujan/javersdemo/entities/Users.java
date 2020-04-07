package com.sujan.javersdemo.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private ContactData contactData;

}
