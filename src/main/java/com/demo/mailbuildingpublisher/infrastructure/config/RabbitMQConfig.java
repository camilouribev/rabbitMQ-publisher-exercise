package com.demo.mailbuildingpublisher.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue firstFloorQueue() {
        return new Queue("1st", false);
    }

    @Bean
    Queue secondFloorQueue() {
        return new Queue("2nd", false);
    }

    @Bean
    Queue thirdFloorQueue() {
        return new Queue("3rd", false);
    }

    @Bean
    Queue fourthFloorQueue() {
        return new Queue("4th", false);
    }

    @Bean
    Queue fifthFloorQueue() {
        return new Queue("5th", false);
    }

    @Bean
    Queue evenFloorQueue() {
        return new Queue("evenFloors", false);
    }

    @Bean
    Queue oddFloorQueue() {
        return new Queue("oddFloors", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("reception-desk-exchange");
    }

    @Bean
    Binding firstFloorBinding(Queue firstFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(firstFloorQueue).to(topicExchange).with("odd.first");
    }

    @Bean
    Binding secondFloorBinding(Queue secondFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(secondFloorQueue).to(topicExchange).with("even.second");
    }

    @Bean
    Binding thirdFloorBinding(Queue thirdFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(thirdFloorQueue).to(topicExchange).with("odd.third");
    }

    @Bean
    Binding fourthFloorBinding(Queue fourthFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(fourthFloorQueue).to(topicExchange).with("even.fourth");
    }

    @Bean
    Binding fifthFloorBinding(Queue fifthFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(fifthFloorQueue).to(topicExchange).with("odd.fifth");
    }

    @Bean
    Binding evenFloorsBinding(Queue evenFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(evenFloorQueue).to(topicExchange).with("even.#");
    }

    @Bean
    Binding oddFloorsBinding(Queue oddFloorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(oddFloorQueue).to(topicExchange).with("odd.#");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
