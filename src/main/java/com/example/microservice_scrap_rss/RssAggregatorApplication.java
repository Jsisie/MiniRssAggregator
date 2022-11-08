package com.example.microservice_scrap_rss;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import com.example.microservice_scrap_rss.rssfeedscraper.Answer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication()
public class RssAggregatorApplication {

    private static final Logger LOG = LoggerFactory.getLogger(RssAggregatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RssAggregatorApplication.class, args);
    }

  /*  @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, List<Answer>> kafkaTemplate) {
        var answer = new Answer(UUID.randomUUID(),"Lord of the Rings","Book about adventures", LocalDate.now(),"google.fr");
        return args -> kafkaTemplate.send("rss", List.of(answer));
    }*/

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            var connector = new CassandraConnector();
//            var session = connector.connect();
//            KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
//            keyspaceRepository.createKeyspace("neskey",2);
//        };
//    }
}
