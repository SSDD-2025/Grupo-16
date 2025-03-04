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
    private Long userId;

    /**
     * Inicializes the ActiveUser to not logged This happens when the session is
     * new
     */
    public void init() {
        logged = false;
    }

    /**
     * Sets the information of the user thats being logged into de @Autowired
     * ActiveUser (which is the User logged in the session)
     *
     * @param newUser the user thats being logged
     */
    public void setNewActiveUser(UserEntity newUser) {
        userId = newUser.getId();
        logged = true;
    }

    /**
     * It tells you whether there is a logged user or not (anon user)
     *
     * @return true if there is a user logged
     */
    public boolean isUserLogged() {
        return logged;
    }

    /**
     * @return active user's id
     */
    public long getId() {
        return userId;
    }

    /**
     * When an user deletes its profile, its session is cleaned
     */
    public void setUserAsNotActive() {
        this.userId = null;
        this.logged = false;
    }
}
