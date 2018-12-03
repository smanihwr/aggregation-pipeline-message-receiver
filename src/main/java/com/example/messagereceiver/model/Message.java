package com.example.messagereceiver.model;

public class Message {

    private Long groupId;
    private int messageId;
    private String tag;
    private String text;

    public Message() {
    }

    public Message(Long groupId, int messageId, String tag, String text) {
        this.groupId = groupId;
        this.messageId = messageId;
        this.tag = tag;
        this.text = text;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "groupId=" + groupId +
                ", messageId=" + messageId +
                ", tag='" + tag + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
