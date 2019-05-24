package Entity;

public class User {
    private String username;
    private String password;
    private int id;
    private String firstname;
    private String lastname;

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User(String username, String password, int id, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {
        this.password = "defPass";
        this.firstname = "defFirst";
        this.lastname = "defLast";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
