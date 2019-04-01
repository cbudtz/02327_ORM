package homemade;

import data.IUserDTO;
import data.UserDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ORM {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        IUserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setUserName("Navn");
        userDTO.setIni("initialer");
        ORM orm = new ORM();
        String tableStateMent = orm.createTableStateMent(userDTO);
        System.out.println("Made CREATE Statement: \r\n" + tableStateMent);

        String insertStatement = orm.insertStatement(userDTO);
        System.out.println("Made INSERT Statement: \r\n" + insertStatement);
    }

    private String insertStatement(Object object) throws InvocationTargetException, IllegalAccessException {
        Class objectClass = object.getClass();
        Method[] methods = objectClass.getMethods();
        List<String> felter = new ArrayList<>();
        List<String> data = new ArrayList<>();
        String tableName = objectClass.getSimpleName();
        for (Method method : methods){
            if (method.getName().startsWith("get")){
//                System.out.println("Fandt Getter: "+ method.getName());
                if (!method.getName().equals("getClass")){
                    felter.add(method.getName().substring(3));
                    Object value = method.invoke(object, null);
//                    System.out.println("Fandt data: " + value.toString());
                    data.add(value.toString());
                }
            }
        }
        String statement = "INSERT INTO " + tableName + " ( ";
        for (String felt : felter){
            statement += (felt + ", ");
        }
        statement = statement.substring(0,statement.length()-2);
        statement += ") VALUES ( ";
        for (String value : data){
            statement += value + ", ";
        }
        statement = statement.substring(0,statement.length()-2);
        statement+= " );";
        return statement;
    }

    public String createTableStateMent(Object object) throws InvocationTargetException, IllegalAccessException {
        Class objectClass = object.getClass();
        Method[] methods = objectClass.getMethods();
        List<String> felter = new ArrayList<>();
        String tableName = objectClass.getSimpleName();
        for (Method method : methods){
            if (method.getName().startsWith("get")){
//                System.out.println("Fandt Getter: "+ method.getName());
                if (!method.getName().equals("getClass")){
                    felter.add(method.getName().substring(3));
                }
            }
        }
        String statement = "CREATE TABLE " + tableName + " ( ";
        for (String felt : felter){
            statement += (felt + " varchar(45), ");
        }
        statement = statement.substring(0,statement.length()-2);
        statement+= " );";
        return statement;

    }
}
