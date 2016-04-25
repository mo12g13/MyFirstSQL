package com.Momo;

import java.sql.SQLException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        // driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";     // where's the database?
    static final String DB_Name = "CubesDatabase";
    static final String USER = "Momo";
    static final String PASSWORD = "password";


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        try {

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

        Statement statement = null;
        Connection conn = null;

        /** New code here **/

        ResultSet rs = null;

        /** End of new code **/

        try {

            //You should have already created a database via terminal/command prompt OR MySQL Workbench

            conn = DriverManager.getConnection(DB_CONNECTION_URL+ DB_Name, USER, PASSWORD);
            statement = conn.createStatement();

            //Create a table in the database, if it does not exist already
         String sql = "CREATE TABLE if not exists CUBES(Cube_Solver varchar(50), Time_In_Seconds DOUBLE  )";
         statement.executeUpdate(sql);
            System.out.println("");



            //Add some data
            String addDataSQL = "INSERT INTO CUBES VALUES ('Fakhri Raihaan', 27.23)";
            statement.executeUpdate(addDataSQL);
            System.out.println("Added two rows of data");

            String addData2 = "INSERT INTO CUBES VALUES ('Cubestormer II robot', 5.270)";
            statement.executeUpdate(addData2);
            System.out.println("Create table");


            String addDataSql = "INSERT INTO CUBES VALUES ('Ruxin Liu (age 3)', 99.33)";
            statement.executeUpdate(addDataSql);
            System.out.println("add another data");

            String addDatasSql = "INSERT INTO CUBES VALUES('Mats Valk ', 6.27)";
            statement.executeUpdate(addDatasSql);

            String nameOfSolver;
            double time_in_Seconds;
            while(true){
                nameOfSolver = solverName();
                System.out.println(nameOfSolver);
                time_in_Seconds = getPositiveDoubleInput();
                System.out.println(time_in_Seconds);
            String prepStatInsert = "INSERT INTO CUBES VALUES ( ? , ? )";
            PreparedStatement psInsert = conn.prepareStatement(prepStatInsert);
            psInsert.setString(1, nameOfSolver);
            psInsert.setDouble(2, time_in_Seconds);
            psInsert.executeUpdate();

                System.out.println("Do you want to enter more data. Enter y for yes and n for no ");
                String exit = input.nextLine();
                if(exit.equalsIgnoreCase("y")){
                    continue;
                }
                if(exit.equalsIgnoreCase("n")){
                    break;
                }

           }

        } catch (SQLException se) {
            se.printStackTrace();
        }
        //MethodCall


try{

            String fetchAllDataSQL = "SELECT * FROM CUBES";
            rs = statement.executeQuery(fetchAllDataSQL);
            while (rs.next()) {
                String solver = rs.getString("Cube_Solver");
                double time_in_seconds = rs.getDouble("Time_In_Seconds");
                System.out.println("Cube solver = " + solver+ " Time in seconds = " + time_in_seconds);
            }

            /** End of new code here **/



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // A finally block runs whether an exception is thrown or not.
            // Close resources and tidy up, whether this code worked or not.

            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("ResultSet closed");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }

            try {
                if (statement != null) {
                    statement.close();
                    System.out.println("Statement closed");
                }
            } catch (SQLException se){
                //Closing the connection could throw an exception too
                se.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();  //Close connection to database
                    System.out.println("Database connection closed");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("End of program");
    }

    public static String solverName(){
       String solverName = null;
        boolean myboo = true;
        Scanner input = new Scanner(System.in);
       do{
           try{
               System.out.println("Enter name of solver: ");
               solverName = input.nextLine();
               myboo = false;

           }catch (NumberFormatException e){
               System.out.println("Please enter a string");
           }

       }while (myboo);

        return solverName;
    }


    private static double getPositiveDoubleInput() {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Please enter time in seconds: ");
                String stringInput = scanner.nextLine();
                double time_in_seconds = Double.parseDouble(stringInput);
                if (time_in_seconds >= 0) {
                    return time_in_seconds;
                } else {
                    System.out.println("Please enter a positive number");
                    continue;
                }
            } catch (NumberFormatException ime) {
                System.out.println("Please type a positive number");
            }
        }

    }

}