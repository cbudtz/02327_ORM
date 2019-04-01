package hibernate;

import data.IUserDTO;
import data.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class UserSaver {
    public static void main(String[] args) {

        //Configure Hibernate
        Properties prop= new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/chbu");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
        prop.setProperty("hibernate.connection.username", "chbu");
        prop.setProperty("hibernate.connection.password", "4thVbCaMOxKiLKnXi3aJ4");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        prop.setProperty("hibernate.hbm2ddl.auto","create"); //Opretter tabeller automatisk
        prop.setProperty("show_sql", "true"); //If you wish to see the generated sql query

        //Make a Hibernate Session
        SessionFactory sessionFactory = new Configuration()
                .addProperties(prop)
                .addAnnotatedClass(UserDTO.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        //Make a Dummy User
        IUserDTO user = new UserDTO();
        user.setUserName("Brian");
        user.setIni("testIni");
        user.addRole("Pedel");
        user.setUserId(10);
        //Start Hibernate transaction
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        //Try to fetch user from db
        UserDTO userDTO = session.get(UserDTO.class, 10);
        System.out.println(userDTO);
        session.close();
    }

}
