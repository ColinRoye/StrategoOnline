package cerulean.hw1.Api.Models.Sessions;

import com.google.gson.Gson;

public abstract class Session {
    private String sessionId;
    public void Session(String sessionId){
        this.sessionId = sessionId;
    }
    public boolean verify(String sessionId){
        return (sessionId.equals(this.sessionId));
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
