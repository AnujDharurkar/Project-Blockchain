import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

class MedicalObject implements Serializable {
    String TYPE;
    String NAME;
    int QUANTITY;
    float AMOUNT;
    String Blood_type;
    String url;

    // Basic Attributes of a medical object
    // Maybe this will be a base class for other specific classes, like Drugs, syringes etc.
    // This may be populated by a database.
    MedicalObject(String type, String name, int quant, float amt){
        this.TYPE = type;
        this.NAME = name;
        this.QUANTITY = quant;
        this.AMOUNT = amt;

        url = "jdbc:sqlite:Object.db";
    }
    void setBloodType(String bg){
        Blood_type = bg;
    }

    void store() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        String SQL;
        if(TYPE.equals("Organ")){
            SQL = "select * from Organs where NAME="+"'"+NAME+"';";
            ResultSet rs = stmt.executeQuery(SQL);
            if(rs.next()){
                SQL = "update Organs set QUANTITY=QUANTITY+"+QUANTITY+" where NAME="+"'"+NAME+"'";
                stmt.execute(SQL);
            }
            else{
                SQL = "insert into Organs values(null," +"'"+NAME+"',"+QUANTITY+",'"+Blood_type+"');";
                System.out.println(SQL);
                stmt.execute(SQL);
            }
        }
        else{
            SQL = "select * from Drugs where NAME="+"'"+NAME+"';";
            ResultSet rs = stmt.executeQuery(SQL);
            if(rs.next()){
                SQL = "update Drugs set QUANTITY=QUANTITY+"+QUANTITY+" where NAME="+"'"+NAME+"';";
                stmt.execute(SQL);
            }
            else{
                SQL = "insert into Drugs values(null," +"'"+NAME+"',"+QUANTITY+");";
                //System.out.println(SQL);
                stmt.execute(SQL);
            }
        }
        stmt.close();
    }

    void getFromDB(String obj,String bg){
        try {
            Connection conn;
            String SQL;
            SQL="select QUANTITY from Organs where NAME='obj' and BLOOD_TYPE='bg'";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
class MedicalHistory implements Serializable {
    int ID;
    String NAME;
    String ADDRESS;
    String BLOODGROUP;
    int AGE;
    ArrayList<String> DISEASES;
    ArrayList<String> ALLERGIES;
    String url;

    MedicalHistory(String name, String address, String bg, int age){
        this.ID = (int) (Math.random() * 10000);
        this.NAME = name;
        this.ADDRESS = address;
        this.BLOODGROUP = bg;
        this.AGE = age;
        url = "jdbc:sqlite:History.db";
    }
    void setDISEASES(ArrayList<String> d){
        DISEASES = d;
    }
    void setALLERGIES(ArrayList<String> a){
        ALLERGIES = a;
    }
    void setBG(String bg){ this.BLOODGROUP = bg; }

    void store() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        String SQL, SQL1, SQL2;

        SQL = "insert into history values("+ID+",'"+NAME+"',"+ "'"+ADDRESS+"',"+"(select ID from Blood where type='"+BLOODGROUP+"'));";
        stmt.execute(SQL);

        for (String st: DISEASES){
            SQL1 = "insert into Diseases values("+ID+", '"+st+"');";
            stmt.execute(SQL1);
        }
        for (String st: ALLERGIES){
            SQL2 = "insert into Allergies values("+ID+", '"+st+"');";
            stmt.execute(SQL2);
        }
        stmt.close();
    }

    void getFromDB(int id){
        return;
    }
}

class CreateDB{
    String url1,url2;
    Connection conn;

    CreateDB(){
        url1 = "jdbc:sqlite:" + "History.db";
        url2 = "jdbc:sqlite:" + "Object.db";
    }

    void create_conn_history(){

        try {

            // Creating a structure for history database
            conn = DriverManager.getConnection(url1);
            Statement stmt = conn.createStatement();
            stmt.execute("create table Blood(ID INTEGER PRIMARY KEY AUTOINCREMENT, type varchar(3));");
            stmt.execute("create table history(ID integer primary key autoincrement, name varchar(25), address varchar(50), bg_id int, foreign key(bg_id) references Blood(ID));");
            stmt.execute("create table Diseases(ID integer, disease varchar(15), foreign key('ID') references history('ID'));");
            stmt.execute("create table Allergies(ID integer, allergy varchar(15), foreign key('ID') references history('ID'));");

            stmt.execute("insert into Blood values(0,'A+');");
            stmt.execute("insert into Blood values(NULL,'A-');");
            stmt.execute("insert into Blood values(NULL,'B+');");
            stmt.execute("insert into Blood values(NULL,'B-');");
            stmt.execute("insert into Blood values(NULL,'O+');");
            stmt.execute("insert into Blood values(NULL,'O-');");
            stmt.execute("insert into Blood values(NULL,'AB+');");
            stmt.execute("insert into Blood values(NULL,'AB-');");

            // Creating a structure for object database
            conn = DriverManager.getConnection(url2);
            stmt = conn.createStatement();
            stmt.execute("create table Organs(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME varchar(20),QUANTITY integer,BLOOD_TYPE varchar(3));");
            stmt.execute("create table Drugs(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME varchar(20),QUANTITY integer);");



        } catch (SQLException e) {
            System.out.println("Database creation failed!");
            e.printStackTrace();
            return;
        }
    }



    public static void main(String[] args) {
        CreateDB db = new CreateDB();
        db.create_conn_history();
    }
}