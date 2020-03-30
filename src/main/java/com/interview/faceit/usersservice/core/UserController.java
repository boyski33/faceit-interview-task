package com.interview.faceit.usersservice.core;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private static final Logger LOG = LogManager.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ApiOperation(value = "Retrieve a pageable list of users by multiple criteria", response = ResponseEntity.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list of users"),
      @ApiResponse(code = 400, message = "Page our of range"),
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<User>> getUsers(@PageableDefault(size = 100) Pageable pageable,
                                             @RequestParam(required = false) String id,
                                             @RequestParam(required = false) String nickname,
                                             @RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName,
                                             @RequestParam(required = false) String email,
                                             @RequestParam(required = false) String country) {

    List<User> users = userService.getUsers(pageable, id, nickname, firstName, lastName, email, country);

    return ResponseEntity.ok(users);
  }

  @ApiOperation(value = "Retrieve a user by ID", response = ResponseEntity.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved user"),
      @ApiResponse(code = 400, message = "ID not in a correct UUID format"),
      @ApiResponse(code = 404, message = "User for provided ID not found")
  })
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
    User user = userService.getUserById(userId);

    return ResponseEntity.ok(user);
  }

  @ApiOperation(value = "Create a user", response = ResponseEntity.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "User successfully created"),
      @ApiResponse(code = 400, message = "User in wrong format or already exists")
  })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
    User addedUser = userService.addUser(user);
    URI userUri = URI.create(String.format("/users/%s", addedUser.getId().toString()));

    return ResponseEntity.created(userUri).body(addedUser);
  }

  @ApiOperation(value = "Update a user", response = ResponseEntity.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "User successfully updated"),
      @ApiResponse(code = 400, message = "User in wrong format"),
      @ApiResponse(code = 404, message = "User for provided ID not found")
  })
  @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> updateUser(@PathVariable("userId") String userId,
                                         @RequestBody @Valid User user) {

    User updatedUser = userService.modifyUser(userId, user);

    return ResponseEntity.ok(updatedUser);
  }

  @ApiOperation(value = "Delete a user by id or nickname", response = ResponseEntity.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "User successfully deleted"),
      @ApiResponse(code = 400, message = "ID not in a correct UUID format"),
      @ApiResponse(code = 404, message = "User for provided ID not found")
  })
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> removeUser(@RequestParam(required = false) String id,
                                         @RequestParam(required = false) String nickname) {

    User removedUser = userService.removeUser(id, nickname);

    return ResponseEntity.ok(removedUser);
  }
}