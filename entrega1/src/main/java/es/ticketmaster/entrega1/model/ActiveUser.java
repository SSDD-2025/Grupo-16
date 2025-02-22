package es.ticketmaster.entrega1.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * This Component will keep the login information throught the session
 */
@Component
@SessionScope
public class ActiveUser {
    private boolean logged;
    private UserEntity activeUser;

    /** 
     * Constructor: initializes the object UserEntity and its not logged (new session)
     */
    public ActiveUser(){
        activeUser = new UserEntity();
        logged = false;
    }

    /** 
     * Inicializes the ActiveUser to not logged
     * This happens when the session is new
     */
    public void init(){
        logged = false;
    }

    /**
     * Sets the information of the user thats being logged into de @Autowired ActiveUser (which is the User logged in the session)
     * @param newUser the user thats being logged
     */
    public void setNewActiveUser(UserEntity newUser){
        activeUser.setAttributes(newUser);
        logged = true;
    }

    /**
     * Active user getter
     * @return the active user as an UserEntity
     */
    public UserEntity getActiveUser() {
        return activeUser;
    }

    /**
     * Active user setter
     * @param activeUser the new active user
     * this method should not be used to stablish the active user, it is better to do a copy of it with the method setNewActiveUser
     */
    public void setActiveUser(UserEntity activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * It tells you whether there is a logged user or not (anon user)
     * @return true if there is a user logged
     */
    public boolean isUserLogged(){
        return logged;
    }
}
