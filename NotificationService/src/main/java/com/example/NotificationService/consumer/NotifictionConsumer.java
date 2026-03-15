package com.example.NotificationService.consumer;

import com.example.NotificationService.config.RabbitMQConfig;
import com.example.NotificationService.dto.MovieAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotifictionConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleMovieAdded(MovieAddedEvent event) {
        log.info("======================================");
        log.info("New movie notification received!");
        log.info("Title   : {}", event.getTitle());
        log.info("Genre   : {}", event.getGenre());
        log.info("Director: {}", event.getDirector());
        log.info("Year    : {}", event.getReleaseYear());
        log.info("======================================");
        // email sending skipped for now
    }

//    private void sendNotificationEmail(MovieAddedEvent event) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo("subscriber@example.com");   // hardcoded for now
//            message.setSubject("New Movie Added: " + event.getTitle());
//            message.setText(
//                    "A new movie has been added!\n\n" +
//                            "Title: "   + event.getTitle()       + "\n" +
//                            "Genre: "   + event.getGenre()        + "\n" +
//                            "Director: "+ event.getDirector()     + "\n" +
//                            "Year: "    + event.getReleaseYear()  + "\n\n" +
//                            "Check it out on VeloStream!"
//            );
//
//            mailSender.send(message);
//            log.info("Notification email sent for movie: {}", event.getTitle());
//
//        } catch (Exception e) {
//            log.error("Failed to send email: {}", e.getMessage());
//        }
//    }
}
