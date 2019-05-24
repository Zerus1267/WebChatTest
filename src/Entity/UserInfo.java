package Entity;

public class UserInfo {
    private User user;
    private String firstname;
    private String lastname;

    public UserInfo() {
    }

    public UserInfo(User user, String firstname, String lastname) {
        this.user = user;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
