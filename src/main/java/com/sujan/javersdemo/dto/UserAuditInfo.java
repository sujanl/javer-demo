package com.sujan.javersdemo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserAuditInfo {
    private String changedBy;
    private LocalDateTime changedOn;
    private Long version;
    private String changedType;
    private List<String> changedProperties;
    private Object afterChange;
}
