package com.interview.faceit.usersservice.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<User>> getUsers(
      @RequestParam(required = false) String id,
      @RequestParam(required = false) String nickname,
      @RequestParam(required = false) String firstName,
      @RequestParam(required = false) String lastName,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String country) {

    List<User> users = userService.getUsers(id, nickname, firstName, lastName, email, country);

    return ResponseEntity.ok(users);
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
    User user = userService.getUserById(userId);

    return ResponseEntity.ok(user);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
    User addedUser = userService.addUser(user);
    URI userUri = URI.create(String.format("/users/%s", addedUser.getId().toString()));

    return ResponseEntity.created(userUri).body(addedUser);
  }

  @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> updateUser(@PathVariable("userId") String userId,
                                         @RequestBody @Valid User user) {

    User updatedUser = userService.modifyUser(userId, user);

    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> removeUser(
      @RequestParam(required = false) String id,
      @RequestParam(required = false) String nickname) {

    User removedUser = userService.removeUser(id, nickname);

    return ResponseEntity.ok(removedUser);
  }
}