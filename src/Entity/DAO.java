package Entity;

import Entity.DataMessage;
import Entity.User;
import javafx.util.Pair;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DAO {
    private String url = "jdbc:postgresql://localhost:5432/chatdata";
    private String user = "postgres";
    private String password = "855492";
    private Connection con;

    public DAO(){
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url,user,password);
            System.out.println("CONNECTED DATABASE");

            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null,null,"userdata",null);
            if (!resultSet.next()) {
                System.out.println("Table USERDATA is not existing");
                PreparedStatement preparedStatement = con.prepareStatement("create table userdata (user_id integer not null, username text not null, passwornd text not null, PRIMARY KEY (user_id));");
                PreparedStatement preparedStatement1 = con.prepareStatement("insert into userdata values (?,?,?)");
                preparedStatement.execute();
                for(int i = 0; i < 7; i++){
                    int user_id = i + 1;
                    String usertestname = "user" + (i+1);
                    String usertestpassword = "user" + (i+1);
                    preparedStatement1.setInt(1,user_id);
                    preparedStatement1.setString(2,usertestname);
                    preparedStatement1.setString(3,usertestpassword);
                    preparedStatement1.execute();
                 }
            }
            ResultSet resultSet1 = databaseMetaData.getTables(null,null,"message",null);
            if(!resultSet1.next()){
                System.out.println("Table MESSAGE is not existing");
                PreparedStatement preparedStatement = con.prepareStatement("create table message (message_id integer not null, message_text text not null, user_id_s integer not null, " +
                        "user_id_r integer not null, message_date timestamp with time zone not null," +
                        "PRIMARY KEY (message_id), constraint mes_user_r_fk FOREIGN KEY (user_id_r) references userdata (user_id), " +
                        "constraint mes_user_s_fk FOREIGN KEY (user_id_s) references userdata (user_id));");
                preparedStatement.execute();
            }
            ResultSet resultSet2 = databaseMetaData.getTables(null,null,"userinfo",null);
            if(!resultSet2.next()){
                System.out.println("Table USERINFO is not existing");
                PreparedStatement preparedStatement = con.prepareStatement("create table userinfo (user_id integer not null, user_name text not null, user_surname text not null, " +
                        "primary key (user_id), constraint user_info_fk FOREIGN KEY (user_id) references userdata (user_id));");
                preparedStatement.execute();
                List<String> names = new ArrayList<String>();
                List<String> surnames = new ArrayList<String>();
                names.add("Jayden");names.add("Shannon");names.add("Martin");names.add("Kadin");names.add("Halle");names.add("Maxwell");names.add("Mihail");
                surnames.add("Mendez");surnames.add("Burgess");surnames.add("Buckley");surnames.add("Guerrero");surnames.add("Stuart");surnames.add("Carter");surnames.add("Konovalov");
                PreparedStatement preparedStatement1 = con.prepareStatement("insert into userinfo values (?,?,?)");
                for(int i = 0; i<7; i++){
                    int user_id = i + 1;
                    //String user_name = names.get(i+1);

                    //String user_surname = surnames.get(i+1);
                    preparedStatement1.setInt(1,user_id);
                    preparedStatement1.setString(2, names.get(i ));
                    preparedStatement1.setString(3,surnames.get(i));
                    preparedStatement1.execute();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username, String pass) throws SQLException {

        User user = new User();
        PreparedStatement preparedStatement =con.prepareStatement("select username, userdata.user_id, passwornd,user_name, user_surname from userdata,userinfo where username =? and passwornd =? and userdata.user_id = userinfo.user_id;");
        int k = 1;
        preparedStatement.setString(k++,username);
        preparedStatement.setString(k,pass);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Result Set occurred");
        if(resultSet.next()){
            System.out.println("Logined");
            user.setId(resultSet.getInt(2));
            user.setUsername(resultSet.getString(1));
            user.setPassword(resultSet.getString(3));
            user.setFirstname(resultSet.getString(4));
            user.setLastname(resultSet.getString(5));
        }
        //PreparedStatement preparedStatement1 = con.prepareStatement("select * from message where user_id_r=?");
        /*preparedStatement1.setInt(1,user.getId());
        ResultSet dateTest = preparedStatement1.executeQuery();
        while (dateTest.next()){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
            String mes_date = dateFormat.format(dateTest.getTimestamp(5));
            System.out.println(mes_date);
        }*/
        return user;
    }

    public User getUserById(int Id) throws SQLException {

        User user = new User();
        PreparedStatement preparedStatement =con.prepareStatement("select username, userdata.user_id, passwornd,user_name, user_surname from userdata, userinfo where userdata.user_id=? and userdata.user_id = userinfo.user_id;");
        preparedStatement.setInt(1,Id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            user.setId(resultSet.getInt(2));
            user.setUsername(resultSet.getString(1));
            user.setPassword(resultSet.getString(3));
            user.setFirstname(resultSet.getString(4));
            user.setLastname(resultSet.getString(5));
        }
        return user;
    }

    public List<DataMessage> MesList(User user) throws SQLException {
        List<DataMessage> dataMessages = new ArrayList<DataMessage>();

        PreparedStatement preparedStatement = con.prepareStatement("select * from message where user_id_r=? or user_id_s=?");
        preparedStatement.setInt(1,user.getId());
        preparedStatement.setInt(2,user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            User rec_user = getUserById(resultSet.getInt(3));
            User send_user = getUserById(resultSet.getInt(4));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
            String mes_date = dateFormat.format(resultSet.getTimestamp(5));
            System.out.println(mes_date);
            dataMessages.add(new DataMessage(resultSet.getInt(1),rec_user,resultSet.getString(2),mes_date,send_user));

        }
        /*for(int i = 0; i < dataMessages.size(); i++){
            dataMessages.get(i).Out();
        }*/
        return dataMessages;
    }

    public List<User> getContacts(User user) throws SQLException {
        List<User> userList = new ArrayList<User>();

        PreparedStatement preparedStatement = con.prepareStatement("select user_id_s from message where user_id_r=?");
        preparedStatement.setInt(1,user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            User contact = new User();
            contact = getUserById(resultSet.getInt(1));
            userList.add(contact);
        }
        System.out.println("Contacts count: "+userList.size());
        return userList;
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<User>();

        PreparedStatement preparedStatement = con.prepareStatement("select user_id from userdata");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            User temp = new User();
            temp = getUserById(resultSet.getInt(1));
            users.add(temp);
        }
        System.out.println("Amount of users registered: " + users.size());
        return users;
    }

    public User getUserByName(String name) throws SQLException {
        User userr = new User();

        PreparedStatement preparedStatement = con.prepareStatement("select user_id from userdata where username=?");
        preparedStatement.setString(1,name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            userr = getUserById(resultSet.getInt(1));
        }
        return userr;
    }

    public void setMes(String msgText, int User_id_s, int User_id_r, String msgDate) throws SQLException, ParseException {
        int Id = -1;
        PreparedStatement getMaxId = con.prepareStatement("select max(message_id) from message");
        ResultSet maxId = getMaxId.executeQuery();
        if(maxId.next()){
            Id = maxId.getInt(1)+1;
        }
        PreparedStatement preparedStatement = con.prepareStatement("insert into message values(?,?,?,?,?)");
        preparedStatement.setInt(1,Id);
        preparedStatement.setString(2,msgText);
        preparedStatement.setInt(3,User_id_s);
        preparedStatement.setInt(4,User_id_r);
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //java.util.Date d = new java.util.Date();
        //Date date = Date.valueOf(String.valueOf(d));
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        preparedStatement.setTimestamp(5,timestamp);


        preparedStatement.execute();

    }
}
