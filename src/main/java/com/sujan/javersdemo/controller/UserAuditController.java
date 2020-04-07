package com.sujan.javersdemo.controller;

import com.sujan.javersdemo.service.UserAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/javers/users")
public class UserAuditController {

    private UserAuditService userAuditService;

    public UserAuditController(UserAuditService userAuditService) {
        this.userAuditService = userAuditService;
    }

    /**
     * Historic state of the object 'com.sastra.p2s.fleet.data.impl.entity.UserEntity'(Change log of all users)
     *
     * @return
     */
    @GetMapping("/all/logs")
    public String getAllUsersSnapshots() {
        return this.userAuditService.getAllUsersLogs();
    }

    /**
     * Historic state of object 'com.sastra.p2s.fleet.data.impl.entity.UserEntity'
     * of user with id 'userId'(Log of user with id 'userId' of all versions)
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/logs")
    public String getUserSnapshots(@PathVariable String userId, @RequestParam String from, @RequestParam String to) {
        return this.userAuditService.getUserLog(userId, from, to);
    }
/*


    //No Security configured in this project so author is set as 'unknown'

    */
/**
     * Historic state of the object 'com.sastra.p2s.fleet.data.impl.entity.UserEntity' where author is
     * 'userId' (Activity log of the user with id 'userId')
     * @param authorId
     * @return
     *//*

    @GetMapping("/author/{authorId}/logs")
    public String getAuthorsSnapshots(@PathVariable String authorId) {
        return this.userAuditService.getAuthorLogs(authorId);
    }

*/

    /**
     * Get updates of a user
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/changes")
    public String getChangesInUser(@PathVariable String userId) {
        return this.userAuditService.getUserChanges(userId);
    }

    /**
     * Just for tests
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/filter")
    public String withFilter(@PathVariable String userId,
                             @RequestParam String from,
                             @RequestParam String to,
                             @RequestParam String changedProperty) {
        return this.userAuditService.withFilters(userId, from, to, changedProperty);
    }

}