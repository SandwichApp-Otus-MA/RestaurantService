package com.sandwich.app.configuration.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sandwich")
@RequiredArgsConstructor
public class SandwichKafkaConfiguration {

    @NestedConfigurationProperty
    private KafkaProperties kafka = new KafkaProperties();

    @Bean
    public KafkaAdmin sandwichKafkaAdmin(ObjectProvider<SslBundles> sslBundles) {
        var adminProperties = this.kafka.buildAdminProperties(sslBundles.getIfAvailable());
        return new KafkaAdmin(adminProperties);
    }

//    @Bean
//    public KafkaTemplate<String, PaymentNotification> notificatonKafkaTemplate(ObjectProvider<SslBundles> sslBundles,
//                                                                               KafkaAdmin sandwichKafkaAdmin) {
//        var template = new KafkaTemplate<>(kafkaNotificatonProducerFactory(sslBundles));
//        template.setKafkaAdmin(sandwichKafkaAdmin);
//        template.setMicrometerTagsProvider(getMicrometerTagsProvider());
//        template.setObservationEnabled(true);
//        return template;
//    }
//
//    @Bean
//    public ProducerFactory<String, PaymentNotification> kafkaNotificatonProducerFactory(ObjectProvider<SslBundles> sslBundles) {
//        var producerProperties = this.kafka.buildProducerProperties(sslBundles.getIfAvailable());
//        return new DefaultKafkaProducerFactory<>(producerProperties, new StringSerializer(), new JsonSerializer<>());
//    }

}
