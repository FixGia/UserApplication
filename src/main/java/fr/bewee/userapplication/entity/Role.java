package fr.bewee.userapplication.entity;


public class Role {

    public Role() {
    }

    /**
     * The Constant Role SuperAdmin
     * Everything is possible !
     */
    public final static String SUPERADMIN = "SuperAdmin";
    /**
     * The Constant Role Admin
     * can use the application, add User and replace City etc
     */
    public final static String ADMIN = "Admin" ;

    /**
     * The Constant Role User
     *  can use the application but can't add User
     */
    public final static String USER = "User" ;

}