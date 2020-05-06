package com.hcs.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hcs.model.Patient;
import com.hcs.util.DBConnection;


public class PatientController {
	
	private static Connection connection;
	private static PreparedStatement ps;
	private static ResultSet rs;

	public String AddPatient(Patient patient) {
		String output = "";
		try {
			connection = DBConnection.getConnection();
			 if (connection == null)
			 {return "Error while connecting to the database for inserting."; } 
			
			ps = connection.prepareStatement(
					"INSERT INTO  patient(PatientId,PatientName,Age,PhoneNo,Email,Address) "
							+ "	VALUES (?,?,?,?,?,?)");

			ps.setInt(1, 0);
			ps.setString(2, patient.getPatientName());
			ps.setInt(3, patient.getAge());
			ps.setInt(4, patient.getPhoneNo());
			ps.setString(5, patient.getEmail());
			ps.setString(6, patient.getAddress());

			ps.execute();
			 connection.close();
			 output = "Inserted successfully"; 

		}
		 catch (Exception e)
		 {
		 output = "Error while inserting the patient.";
		 System.err.println(e.getMessage());
		 }
		 return output; 

		
	}
	
	public List<Patient> readPatients() {
		List<Patient> patients = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				System.err.println("connecting failed.");
			}
			

			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from patient");
			

			// iterate through the rows in the result set
			while (rs.next()) {
				Patient pt = new Patient();
				pt.setPatientId(rs.getInt("PatientId"));
				pt.setPatientName(rs.getString("PatientName"));
				pt.setAge(rs.getInt("Age"));
				pt.setPhoneNo(rs.getInt("PhoneNo"));
				pt.setEmail(rs.getString("Email"));
				pt.setAddress(rs.getString("Address"));

				patients.add(pt);
				
				
			}
			connection.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return patients;
	}
	

	public String updatepatient(Patient patient) {
		String output = "";
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			ps = connection.prepareStatement(
					"UPDATE patient SET PatientName=?,Age=?,PhoneNo=?,Email=?,Address=? WHERE PatientId=?");

			// binding values
			ps.setString(1, patient.getPatientName());
			ps.setInt(2,patient.getAge());
			ps.setInt(3,patient.getPhoneNo());
			ps.setString(4, patient.getEmail());
			ps.setString(5, patient.getAddress());
			ps.setInt(6, patient.getPatientId());
			// execute the statement
			ps.execute();
			connection.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the patient.-"+e;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePatient(String PatientId) {
		String output = "";
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement

			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("delete from patient where PatientId=?");
			// binding values
			ps.setInt(1, Integer.parseInt(PatientId));
			// execute the statement
			ps.execute();
			connection.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the patient. -"+ e.getMessage();
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public Patient searchPatients(String PatientId) {
		Patient pt = new Patient();
	
		try {
			connection = DBConnection.getConnection();
			if(connection == null) {
				System.out.println("connection failed");
			}
			
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery("select * from patient p where PatientId="+PatientId+"");
			
			while(rs.next()) {
				
				pt.setPatientId(rs.getInt("PatientId"));
				pt.setPatientName(rs.getString("PatientName"));
				pt.setAge(rs.getInt("Age"));
				pt.setPhoneNo(rs.getInt("PhoneNo"));
				pt.setEmail(rs.getString("Email"));
				pt.setAddress(rs.getString("Address"));
	
				
			}
			connection.close();
		}catch (Exception e) {
			
			System.out.println(e.getMessage());
			
		}
		return pt;
	}
	
}
