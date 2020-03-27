package com.interview.faceit.usersservice.notification;

import com.interview.faceit.usersservice.core.NotificationService;
import com.interview.faceit.usersservice.core.User;
import org.springframework.stereotype.Service;

/**
 * A dummy class used as an example of publishing to a Kafka stream
 * for notifying other microservices of a change of a user. As long as
 * the NotificationService interface is implemented, this class can
 * provide its own implementation. Other message brokers, such as
 * RabbitMQ can be used, or even simply sending HTTP requests with
 * a RestTemplate/FeignClient.
 */
@Service
public class KafkaNotificationProducer implements NotificationService {

  @Override
  public void notifyUserAdded(User user) {
    // todo
  }

  @Override
  public void notifyUserModified(User user) {
    // todo
  }
}
