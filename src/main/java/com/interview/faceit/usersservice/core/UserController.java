package com.interview.faceit.usersservice.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

  private static final Logger LOG = LogManager.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
    User addedUser = userService.addUser(user);

    return ResponseEntity.ok(addedUser);
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> removeUser(
      @RequestParam(required = false) String id,
      @RequestParam(required = false) String nickname) {

    User removedUser = userService.removeUser(id, nickname);

    return ResponseEntity.ok(removedUser);
  }
}