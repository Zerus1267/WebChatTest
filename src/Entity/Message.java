package Entity;

public class Message {
    private String text;
    private String date;
    private String to;
    private String fromindex;
    public Message() {
    }

    public Message(String msg_text, String date, String to,String fromIndex) {
        this.text = msg_text;
        this.date = date;
        this.to = to;
        this.fromindex = fromIndex;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromindex() {
        return fromindex;
    }

    public void setFromindex(String fromindex) {
        this.fromindex = fromindex;
    }
}
