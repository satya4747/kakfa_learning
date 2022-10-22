package com.training.kafkaExample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaService {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${kafka.topic}")
	String topicName;

	public void sendMessage(String msg) {
	    kafkaTemplate.send(topicName, msg);
	}
	
	public void sendMessageWithCallback(String message) {
		ListenableFuture<SendResult<String, String>> future = 
			      kafkaTemplate.send(topicName, message);
				
			    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			        @Override
			        public void onSuccess(SendResult<String, String> result) {
			            System.out.println("Sent message=[" + message + 
			              "] with offset=[" + result.getRecordMetadata().offset() + "]"+ 
			              "with partition=[" + result.getRecordMetadata().partition() + "]");
			        }
			        @Override
			        public void onFailure(Throwable ex) {
			            System.out.println("Unable to send message=[" 
			              + message + "] due to : " + ex.getMessage());
			        }
			    });
	}

}
