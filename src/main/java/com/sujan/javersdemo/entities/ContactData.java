package com.sujan.javersdemo.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.javers.spring.annotation.JaversAuditable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactData {
    private String fullName;
    private String address;
    private String phone;

}
