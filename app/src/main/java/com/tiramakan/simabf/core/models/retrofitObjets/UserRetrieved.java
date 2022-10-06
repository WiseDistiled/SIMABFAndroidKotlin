package com.tiramakan.simabf.core.models.retrofitObjets;

/**
 * Created by tiramakan on 05/04/2016.
 */
public class UserRetrieved {
    public String[] roles;
    public String access_token;
    public String username;
    public String[] getRoles() {
        return roles;
    }
    public String getToken() {
        return access_token;
    }
    public String getUsername() {
        return username;
    }

}
