package com.sujan.javersdemo.service;

import com.sujan.javersdemo.dto.UserAuditInfo;
import com.sujan.javersdemo.entities.Users;
import com.sujan.javersdemo.repository.UserRepository;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAuditService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserAuditService.class);
    private Javers javers;
    private UserRepository userRepository;

    public UserAuditService(Javers javers, UserRepository userRepository) {
        this.javers = javers;
        this.userRepository = userRepository;
    }

    public String getAllUsersLogs() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Users.class);
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
        LOGGER.info("Actual Snapshot of all Users: {}", javers.getJsonConverter().toJson(snapshots));
        List<UserAuditInfo> responseList = snapshots.stream().map(this::getResponse).collect(Collectors.toList());
        return javers.getJsonConverter().toJson(responseList);
    }
    private UserAuditInfo getResponse(CdoSnapshot snapshot) {
        UserAuditInfo response = new UserAuditInfo();
        response.setChangedBy(snapshot.getCommitMetadata().getAuthor());
        response.setChangedOn(snapshot.getCommitMetadata().getCommitDate());
        response.setVersion(snapshot.getVersion());
        response.setChangedType(snapshot.getType().toString());
        response.setChangedProperties(snapshot.getChanged());
        response.setAfterChange(snapshot.getState());
        return response;
    }

    public String getUserLog(String userId, String from, String to) {
        //Assumption that 'from' & 'to' are non-null
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,d");
        JqlQuery jqlQuery = QueryBuilder.byInstanceId(userId, Users.class)
                .from(LocalDate.parse(from, formatter)).to(LocalDate.parse(to, formatter)).withChildValueObjects().build();
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery);
        LOGGER.info("Actual User snapshots: {}", javers.getJsonConverter().toJson(snapshots));
        List<UserAuditInfo> responseList = snapshots.stream().map(this::getResponse).collect(Collectors.toList());
        return javers.getJsonConverter().toJson(responseList);
    }

    public String getUserChanges(String userId) {
        JqlQuery jqlQuery = QueryBuilder
                .byInstanceId(userId, Users.class)
                .withNewObjectChanges(true)
                .build();
        Changes changes = javers.findChanges(jqlQuery);
        LOGGER.info("Actual Changes: {}", javers.getJsonConverter().toJson(changes));
        return changes.prettyPrint();
    }

    public String withFilters(String userId, String from, String to, String changedProperty) {
        //Assumption that 'from' & 'to' are non-null
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,d");
        JqlQuery jqlQuery = QueryBuilder
                .anyDomainObject()
                /*.byInstanceId(userId, Users.class)
                .from(LocalDate.parse(from, formatter))
                .to(LocalDate.parse(to, formatter))
                .withChangedProperty(changedProperty)
                .limit(5)
                .skip(5)*/
                .build();
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery);
        LOGGER.info("Actual Snapshot of all Users: {}", javers.getJsonConverter().toJson(snapshots));
        return javers.getJsonConverter().toJson(snapshots);
    }
/*
    public String getAuthorLogs(String authorId) {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Users.class).byAuthor(authorId);
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
        LOGGER.info("Actual Snapshot of the author: {}", javers.getJsonConverter().toJson(snapshots));
        List<UserAuditInfo> responseList = snapshots.stream().map(this::getResponse).collect(Collectors.toList());
        return javers.getJsonConverter().toJson(responseList);
    }
    */
}
