<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.hms.Patients"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Management</title> 
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script> 
<script src="Components/patients.js"></script>
</head>

<body>
 <div class="container">
  <div class="row">  
  <div class="col-6"> 
      
      <h1>Patient Management</h1> 
       <form id="formPatient" name="formPatient"> 
        Patient Name:  <input id="PatientName" name="PatientName" type="text" class="form-control form-control-sm"> 
        <br> 
        Age:  <input id="Age" name="Age" type="text" class="form-control form-control-sm">  
        <br> 
        Contact Number:  <input id="PhoneNo" name="PhoneNo" type="text" class="form-control form-control-sm">  
        <br> 
        Email:  <input id="Email" name="Email" type="text" class="form-control form-control-sm">  
        <br> 
        Address:  <input id="Address" name="Address" type="text" class="form-control form-control-sm">  
        <br>
        <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
        <input type="hidden" id="hidPatientIdSave" name="hidPatientIdSave" value=""> 
        </form> 
        
         <div id="alertSuccess" class="alert alert-success"></div>
        <div id="alertError" class="alert alert-danger"></div>
        
        <br>
        <div id="divPatientGrid">         
        <%
            Patients patientObj = new Patients();
            out.print(patientObj.readPatient());
        %>
      
       </div>
        
     </div>
    </div>
   
  </div>

</body>
</html>