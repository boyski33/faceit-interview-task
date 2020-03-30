package com.interview.faceit.usersservice.core;

/**
 * This interface is to be implemented by any class handling notifications to other services.
 * For example, this could be done with an HTTP call or with a publish to a message queue.
 */
public interface NotificationService {

  void notifyUserAdded(User user);

  void notifyUserModified(User user);

  void notifyUserDeleted(User user);
}
