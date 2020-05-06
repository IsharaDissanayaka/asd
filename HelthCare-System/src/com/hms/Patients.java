package com.hms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patients {
	
	private Connection connect()  {  
		Connection con = null; 
	 
	  try   {    
		  Class.forName("com.mysql.jdbc.Driver");    
		  con =  DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/hcs", "root", "");  
		  }  
	  catch (Exception e)   {    
		  e.printStackTrace();  
		  }
	 
	  return con;  
	 }  
	 
	public String readPatient() { 
		String output = ""; 
	 
	 try  {   
		 Connection con= connect(); 
	 
	  if (con == null)   { 
		  
		  return "Error while connecting to the database for reading.";  
		  } 
	 
	  // Prepare the html table to be displayed   
	  output = "<table border='1'><tr><th>PatientName</th>       "
	  		 + "<th>Age</th>  "
	  		 + "<th>PhoneNo</th><th>Email</th><th>Address</th>         "
	  		 + "<th>Update</th><th>Remove</th></tr>"; 
	 
	  String query = "select * from Patient";   
	  Statement stmt = con.createStatement();   
	  ResultSet rs = stmt.executeQuery(query); 
	 
	  // iterate through the rows in the result set   
	  while (rs.next())   {    
		  String PatientId = Integer.toString(rs.getInt("PatientId"));    
		  String PatientName = rs.getString("PatientName");    
		  String Age = Integer.toString(rs.getInt("Age"));    
		  String PhoneNo = Integer.toString(rs.getInt("PhoneNo"));    
		  String Email = rs.getString("Email"); 
		  String Address = rs.getString("Address"); 
	 
	   // Add into the html table    
	   output += "<tr><td><input id='hidPatientIdUpdate'        "
	   		  + "name='hidPatientIdUpdate'          "
	   		  + "type='hidden' value='" + PatientId
	   		  + "'>"  + PatientName +   "</td>";    
	       
	   output += "<td>" + Age + "</td>";    
	   output += "<td>" + PhoneNo + "</td>"; 
	   output += "<td>" + Email + "</td>"; 
	   output += "<td>" + Address + "</td>"; 
	 
	   // buttons    
	   output += "<td><input name='btnUpdate'          "
	   		  + "type='button' value='Update'         "
	   		  + "class='btnUpdate btn btn-secondary'></td>"      
	   		  + "<td><input name='btnRemove'         "
	   		  + "type='button' value='Remove'         "
	   		  + "class='btnRemove btn btn-danger'        "
	   		  + "data-PatientId='"       
	   		  + PatientId + "'>" + "</td></tr>"; 
	   } 
	 
	  con.close(); 
	 
	  // Complete the html table   
	  output += "</table>";  
	  } 
	
	catch (Exception e)  {   
		output = "Error while reading the patients.";   
		System.err.println(e.getMessage()); 
		} 
	 
	 return output; 
	 }
	
	public String insertPatient(String PatientName, String Age, String PhoneNo, String Email, String Address)  
	{   
		String output = ""; 
	 
	  try   {   
		        Connection con = connect(); 
	 
	   if (con == null)    {     
		   return "Error while connecting to the database for inserting."; 
		   } 
	 
	   // create a prepared statement    
	   String query = " insert into Patient(`PatientId`,`PatientName`,`Age`,`PhoneNo`,`Email`,`Address`)" 
	                +"	values (?,?,?,?,?,?)"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values    
	   preparedStmt.setInt(1, 0);    
	   preparedStmt.setString(2, PatientName);    
	   preparedStmt.setInt(3, Integer.parseInt(Age));    
	   preparedStmt.setInt(4, Integer.parseInt(PhoneNo));    
	   preparedStmt.setString(5, Email); 
	   preparedStmt.setString(6, Address); 
	 
	   // execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	 
	   String newPatients = readPatient();    
	   output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}";  
	   }   catch (Exception e)   {    
		   output = "{\"status\":\"error\", \"data\":         "
		   		+ "\"Error while inserting the patient.\"}";    
		   System.err.println(e.getMessage());  
	   } 
	 
	  return output;  
	  }
	
	public String updatePatients(String PatientId, String PatientName, String Age, String PhoneNo, String Email, String Address)  {   
		String output = ""; 
	 
	  try   {    
		  Connection con = connect(); 
	  
	 
	   if (con == null)    {     
		   return "Error while connecting to the database for updating.";    
		   } 
	 
	   // create a prepared statement    
	  String query = "UPDATE Patient SET PatientName=?,Age=?,PhoneNo=?,Email=?, Address=? WHERE PatientId=?"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values    
	   preparedStmt.setString(1, PatientName);    
	   preparedStmt.setInt(2, Integer.parseInt(Age));    
	   preparedStmt.setInt(3, Integer.parseInt(PhoneNo));    
	   preparedStmt.setString(4, Email);
	   preparedStmt.setString(5, Address);
	   preparedStmt.setInt(6, Integer.parseInt(PatientId)); 
	 
	   // execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	 
	   String newPatients = readPatient();    
	   output = "{\"status\":\"success\", \"data\": \"" +        
			   newPatients + "\"}";   
	   }   catch (Exception e)   {    
		   output = "{\"status\":\"error\", \"data\":         "
		   		+ "\"Error while updating the patient.\"}";    
		   System.err.println(e.getMessage());   
		   } 
	 
	  return output;  
	  } 
	 
	 public String deletePatients(String PatientId)  {   
		 String output = ""; 
	 
	  try   {    
		Connection con = connect(); 
	 
	   if (con == null)    {     
		   return "Error while connecting to the database for deleting.";    
		   } 
	   
	// create a prepared statement    
	   String query = "delete from Patient where PatientId=?"; 
	   
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values    
	   preparedStmt.setInt(1, Integer.parseInt(PatientId)); 
	 
	   // execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	 
	   String newPatients = readPatient();    
	   output = "{\"status\":\"success\", \"data\": \"" +        
			   newPatients + "\"}";  
	   }   catch (Exception e)   {    
		   output = "{\"status\":\"error\", \"data\":         "
		   		+ "\"Error while deleting the patient.\"}";    
		   System.err.println(e.getMessage());   
		   } 
	 
	  return output; 
	  } 
}
