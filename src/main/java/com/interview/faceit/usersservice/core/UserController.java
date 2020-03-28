package com.interview.faceit.usersservice.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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