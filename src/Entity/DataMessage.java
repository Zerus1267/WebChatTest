package Entity;

import coders.DataCoder;

public class DataMessage {
    private  int id;
    private User sender;
    private String text;
    private String date;
    private User receiver;

    public DataMessage() {
    }

    public DataMessage(int id, User user_id, String text, String date, User reciever) {
        this.id = id;
        this.sender = user_id;
        this.text = text;
        DataCoder dataCoder = new DataCoder();
        this.date = dataCoder.getMonth(date);
        this.receiver = reciever;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    void Out(){
        System.out.println("Id " + this.id + " Text " + this.text + " Sender " + this.sender.getId() + " Receiver " + this.receiver.getId() + " Date " + this.date);
    }
}
