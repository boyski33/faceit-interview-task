package com.interview.faceit.usersservice.core;

public interface NotificationService {

  void notifyUserAdded(User user);

  void notifyUserModified(User user);

  void notifyUserDeleted(User user);
}
