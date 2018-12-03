package com.example.messagereceiver;

import com.example.messagereceiver.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootApplication
@EnableBinding(Sink.class)
public class MessageReceiverApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiverApplication.class);

    private HashMap<Long, ArrayList<Message>> messageMap = new HashMap<>();
    private HashMap<Long, Boolean> messageGroupEndFlagMap = new HashMap<>();
    private HashMap<Long, Integer> messageGroupTotalCountMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(MessageReceiverApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void onMessage(Message message) {
        LOG.trace("Receiver : " + message);

        Long groupId = message.getGroupId();

        ArrayList<Message> currentList = messageMap.get(groupId);
        if (null == currentList) {
            currentList = new ArrayList<>();

            //defaulting
            messageGroupEndFlagMap.put(groupId, false);
            messageGroupTotalCountMap.put(groupId, 0);
        }

        currentList.add(message);
        messageMap.put(groupId, currentList);

        ArrayList<Message> savedList = messageMap.get(message.getGroupId());

        if (messageGroupEndFlagMap.get(groupId) && messageGroupTotalCountMap.get(groupId) == savedList.size()) {
            LOG.debug("Group Id " + groupId + " received completely. Total messages " + savedList.size());
            return;
        } else if ("end".equals(message.getTag())) {
            messageGroupEndFlagMap.put(groupId, true);
            messageGroupTotalCountMap.put(groupId, message.getMessageId());
            if (message.getMessageId() == savedList.size()) {
                LOG.debug("Group Id " + groupId + " received completely. Total messages " + savedList.size());
                return;
            }
        }
    }
}
