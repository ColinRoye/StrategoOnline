package cerulean.hw1.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
public class Player {
    @Id
    public ObjectId _id;
    public String name;
    // Constructors
    public Player() {}

    public Player(ObjectId _id, String name) {
        this._id = _id;
        this.name = name;
    }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

}