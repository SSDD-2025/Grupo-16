package es.ticketmaster.entrega1.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ActiveUser {
    private boolean logged;
    private UserEntity activeUser;

    public ActiveUser(){
        activeUser = new UserEntity();
        logged = false;
    }

    public void init(){
        logged = false;
    }

    public void setNewActiveUser(UserEntity newUser){
        activeUser.setAttributes(newUser);
        logged = true;
    }

    public UserEntity getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(UserEntity activeUser) {
        this.activeUser = activeUser;
    }

    public boolean isUserLogged(){
        return logged;
    }
}
