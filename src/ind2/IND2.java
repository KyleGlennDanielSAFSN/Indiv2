/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ind2;
import java.sql.*;
import org.json.simple.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
//why is this not covered under the * ?

/**
 *
 * @author KYLE GLENN DANIEL SAFSN
 It broke last time so this is take 2
 * I got confused so this is a abomination but most of my code ends up monstrous so thats par for the course
 * in fact the reason i sign all my code with SAFSN is because it signifies "sound and fury signifying nothing" which is most of my code
 */
public class IND2 {

    /**
     * @param args the command line arguments
     */
  @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            JSONArray plswork = new JSONArray();
            plswork.add(getJSONData());
            String jsonString = JSONValue.toJSONString(plswork);
            System.out.println(jsonString);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(IND2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static JSONArray getJSONData() throws SQLException {
        
        Connection conn;
        ResultSetMetaData metadata;
        ResultSet resultset = null;        
        PreparedStatement pstSelect = null, pstUpdate = null;
        boolean hasresults;
        String query, key, value;
        //String newFirstName = "Alfred", newLastName = "Neuman";
        int resultCount, columnCount,updatecount;
        
        JSONObject record = null;
        //
        JSONArray array = new JSONArray(); // Container array
        
        try {

            /* Identify the Server */

            String server = ("jdbc:mysql://localhost/p2_test");
            String username = "root";
            String password = "cs488";
            System.out.println("Connecting to " + server + "...");

            /* Open Connection (MySQL JDBC driver must be on the classpath!) */

            conn = DriverManager.getConnection(server, username, password);

            /* Test Connection */

            if (conn.isValid(0)) {

                /* Connection Open! */

                System.out.println("Connected Successfully!");

                query = "SELECT firstname, middleinitial, lastname, address, city, state, zip FROM people";
                pstSelect = conn.prepareStatement(query);
                //pstUpdate.setString(1, newFirstName);
                //pstUpdate.setString(2, newLastName);
                 
                /* Execute Select Query */
                //updateCount = pstUpdate.executeUpdate();
                 /*if (updateCount > 0) {
            
                    resultset = pstUpdate.getGeneratedKeys();

                    if (resultset.next()) {

                        System.out.print("Update Successful!  New Key: ");
                        System.out.println(resultset.getInt(1));

                    }

                }
                /* Prepare Select Query */
                /*
                query = "SELECT * FROM people";
                pstSelect = conn.prepareStatement(query);
                
                */
                /* Execute Select Query */
                System.out.println("Submitting Query ...");

                hasresults = pstSelect.execute();                

                /* Get Results */

                System.out.println("Getting Results ...");

                if ( hasresults ) {

                    /* Get ResultSet Metadata */

                    resultset = pstSelect.getResultSet();
                    metadata = resultset.getMetaData();
                    columnCount = metadata.getColumnCount(); 

                    while(resultset.next()) {

                        /* Loop Through ResultSet Columns and Rows */
                        record = new JSONObject();
                        for (int i = 1; i <= columnCount; i++) {

                            key = metadata.getColumnLabel(i);
                            //keys.add(key);
                            value = resultset.getString(i);
                            //values.add(value);
                            record.put(key,value);

                        }
                       array.add(record);
                    }
                }

            }

            conn.close(); 
      
        } catch (Exception e) { e.printStackTrace(); }
        
        
        /* Close Other Database Objects */
        
        finally {
            
            if (resultset != null) { try { resultset.close(); } catch (Exception e) { e.printStackTrace(); } }
            
            if (pstSelect != null) { try { pstSelect.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return array;
    
    } 
}

