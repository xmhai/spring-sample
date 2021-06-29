package com.cl.smart.flow.web.rest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.smart.flow.domain.GroupCreateRequest;
import com.cl.smart.flow.domain.UserCreateRequest;

/**
 * REST controller for identity management.
 */
@RestController
@RequestMapping(value = "/api/identity", produces = "application/json")
public class IdentityResource {
	
	@Autowired
	IdentityService identityService;
	
	/**
	 * Create a user
	 *
	 * @param user information
	 * @return success/failed
	 */
	@PostMapping(value = "/users")
	public String createUser(@RequestBody UserCreateRequest request) {
		User user = identityService.newUser(request.getUserId());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		identityService.saveUser(user);
		
		if (StringUtils.isNotBlank(request.getGroupId())) {
			identityService.createMembership(request.getUserId(), request.getGroupId());
		}
		return "success";
	}
	
	/**
	 * Create a group
	 *
	 * @param group information
	 * @return success/failed
	 */
	@PostMapping(value = "/groups")
	public String createUser(@RequestBody GroupCreateRequest request) {
		Group group = identityService.newGroup(request.getGroupId());
		group.setName(request.getGroupName());
		identityService.saveGroup(group);
		return "success";
	}
	
	/**
	 * Add user to group
	 *
	 * @param	userId
	 * 			groupId
	 * @return success/failed
	 */
	@PostMapping(value = "/groups/{groupId}/members/{userId}")
	public String addGroupUser(@PathVariable String groupId, @PathVariable String userId) {
		identityService.createMembership(userId, groupId);
		return "success";
	}
	
	/**
	 * Remove user from group
	 *
	 * @param	userId
	 * 			groupId
	 * @return success/failed
	 */
	@DeleteMapping(value = "/groups/{groupId}/members/{userId}")
	public String removeGroupUser(@PathVariable String groupId, @PathVariable String userId) {
		identityService.deleteMembership(userId, groupId);
		return "success";
	}
}
