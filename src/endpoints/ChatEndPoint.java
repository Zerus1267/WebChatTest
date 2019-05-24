package endpoints;

import Entity.DAO;
import Entity.Message;
import Entity.User;
import coders.MessageDecoder;
import coders.MessageEncoder;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@ServerEndpoint(value = "/chat/{username}", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ChatEndPoint {

    private Session session=null;
    private static List<Session> sessionList = new LinkedList<>();
    private static HashMap<String, String> users = new HashMap<>();
    User user;
    boolean sendToMyselfFlag = false;

    @OnOpen
    public void onOpen(Session session, @PathParam("username")String username) throws SQLException {
        System.out.println("WevSocket Connection detected!");
        this.session = session;
        users.put(session.getId(),username);
        System.out.println("Web socket user: " + username);
        sessionList.add(session);
        System.out.println("Users in the chat: " + sessionList.size());
        DAO dao = new DAO();
        user = dao.getUserByName(username);
    }

    @OnClose
    public void  onClose(Session session){
        this.sessionList.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, Message msg){
        //System.out.println("Web socket text msg: <" + msg.getText()+">");
        DAO dao = new DAO();

        boolean flag = false;

        for (Session s:sessionList
             ) {
            if(msg.getTo().equals(users.get(s.getId()))){
                flag = true;
                System.out.println("User is online");
                break;
            }
            else System.out.println("User is't online? Sorry :D");
        }
        if(flag) {
            for (Session s :
                    sessionList) {


            if (msg.getTo().equals(users.get(s.getId()))) {
                try {
                    System.out.println("Sending to online user!");
                    int toIndex = dao.getUserByName(msg.getTo()).getId();
                    if(Integer.parseInt(msg.getFromindex()) == toIndex){
                        if(!sendToMyselfFlag) {s.getBasicRemote().sendObject(new Message("Hi me :D", " ", String.valueOf(toIndex), msg.getFromindex())); sendToMyselfFlag = true;}
                        else{break;}
                    }
                    else{
                        s.getBasicRemote().sendObject(msg);
                        dao.setMes(msg.getText(),Integer.parseInt(msg.getFromindex()),toIndex,msg.getDate());
                        break;
                    }
                } catch (IOException | EncodeException | ParseException | SQLException e) {
                    e.printStackTrace();
                }
            }
            }
        }
        else {
            try {
                dao.setMes(msg.getText(),Integer.parseInt(msg.getFromindex()),dao.getUserByName(msg.getTo()).getId(),msg.getDate());
                System.out.println("Sending to offline user");

            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            }
        }
        /*sessionList.forEach(s -> {
            if (msg.getTo().equals(users.get(s.getId()))) {
                try {
                    s.getBasicRemote().sendObject(msg);
                    dao.setMes(msg.getText(),Integer.parseInt(msg.getFromindex()),dao.getUserByName(msg.getTo()).getId(),msg.getDate());
                    return;
                } catch (IOException | EncodeException | ParseException | SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    dao.setMes(msg.getText(),Integer.parseInt(msg.getFromindex()),dao.getUserByName(msg.getTo()).getId(),msg.getDate());
                    return;
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
            }

        });*/
        /*sessionList.forEach(s -> {
            if(s == this.session) return;
            try {
                s.getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });*/
    }
}
