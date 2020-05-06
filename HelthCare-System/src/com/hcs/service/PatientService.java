package com.hcs.service;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.hcs.controller.PatientController;
import com.hcs.model.Patient;

@Path("/patients")
public class PatientService {
	
	
	PatientController patientController = new PatientController();

	@GET
	@Path("/read")
	@Produces({ MediaType.TEXT_PLAIN })
	public String readItems() {
		return new Gson().toJson(patientController.readPatients());
	}

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPatient(@FormParam("PatientName") String PatientName,@FormParam("Age") String Age,@FormParam("PhoneNo") String PhoneNo,
			@FormParam("Email") String Email, @FormParam("Address") String Address) throws ParseException {

		Patient patient = new Patient();
		patient.setPatientName(PatientName);
		patient.setAge(Integer.parseInt(Age));
		patient.setPhoneNo(Integer.parseInt(PhoneNo));
		patient.setEmail(Email);
		patient.setAddress(Address);

		return patientController.AddPatient(patient);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(@PathParam("id")String PatientId) {

		return patientController.deletePatient(PatientId);
	}
	
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePatient(@FormParam("PatientId") String PatientId,@FormParam("PatientName") String PatientName,@FormParam("Age") String Age,@FormParam("PhoneNo") String PhoneNo,
			@FormParam("Email") String Email, @FormParam("Address") String Address) throws ParseException {

		Patient patient = new Patient();
		patient.setPatientId(Integer.parseInt(PatientId));
		patient.setPatientName(PatientName);
		patient.setAge(Integer.parseInt(Age));
		patient.setPhoneNo(Integer.parseInt(PhoneNo));
		patient.setEmail(Email);
		patient.setAddress(Address);

		return patientController.updatepatient(patient);
	}
	
	@GET
	@Path("/search/{id}")
	@Produces({MediaType.TEXT_PLAIN})
	public String searchPatient(@PathParam("id")String PatientId) {
		return new Gson().toJson(patientController.searchPatients(PatientId));
	}
	
}
