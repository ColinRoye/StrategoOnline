package cerulean.hw1.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * User Account Model. Contains minimum info needed for account management (i.e. _id, username, and password).
 * (Don't worry, password isn't stored in plaintext)
 */
public class Account {

    @Id
    private ObjectId _id;
    private String username;
    private String password;

    public Account(ObjectId _id, String username, String password) {
        this._id = _id;
        this.username = username;
        this.password = password;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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
}
