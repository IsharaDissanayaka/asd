$(document).ready(function() { 
	if($("#alertSuccess").text().trim()== ""){
	   $("#alertSuccess").hide();  
    }
	$("#alertError").hide(); 
});


//=================================================SAVE=============================================================
$(document).on("click", "#btnSave", function(event) { 
	
	// Clear alerts--------------------- 
	$("#alertSuccess").text(""); 
	$("#alertSuccess").hide(); 
	$("#alertError").text(""); 
	$("#alertError").hide(); 
	
	// Form validation-------------------  
	var status = validatePatientForm();  
	if (status != true)  {  
		$("#alertError").text(status);   
		$("#alertError").show();   
		return; 
		} 
	 
	 // If valid------------------------  
	var type = ($("#hidPatientIdSave").val() == "") ? "POST" : "PUT"; 
	
	$.ajax(
			{  url : "PatientAPI", 
			   type : type,  
			   data : $("#formPatient").serialize(),  
			   dataType : "text",  
			   complete : function(response, status)  
			   {   
				   onPatientSaveComplete(response.responseText, status); 
			    } 
		  });
}); 

function onPatientSaveComplete(response, status) {  
	
	if (status == "success") {

		var resultSet = JSON.parse(response); 
		
		if (resultSet.status.trim() == "success") {  
			$("#alertSuccess").text("Successfully saved."); 
			$("#alertSuccess").show(); 

		    $("#divPatientGrid").html(resultSet.data); 
		    } else if (resultSet.status.trim() == "error") { 
		    	
		    	$("#alertError").text(resultSet.data);  
		    	$("#alertError").show(); 
		    	
		    }else if (status == "error") {  
		    	$("#alertError").text("Error while saving.");  
		    	$("#alertError").show(); 
		    	
		    } else {  $("#alertError").text("Unknown error while saving..");  
		              $("#alertError").show(); 
		           }
		
		$("#hidPatientIdSave").val(""); 
		$("#formPatient")[0].reset(); 
		
	}
	
	

}
	 
//update button
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidPatientIdSave").val($(this).closest("tr").find('#hidPatientIdUpdate').val());
	//$("#PaymentID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#PatientName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#Age").val($(this).closest("tr").find('td:eq(1)').text());
	$("#PhoneNo").val($(this).closest("tr").find('td:eq(2)').text());
	$("#Email").val($(this).closest("tr").find('td:eq(3)').text());
	$("#Address").val($(this).closest("tr").find('td:eq(4)').text());
});

//================================================REMOVE============================================================
$(document).on("click", ".btnRemove", function(event) {  
	$.ajax(  {   
		url : "PatientAPI",   
		type : "DELETE",   
		data : "PatientId=" + $(this).data("patientid"),   
		dataType : "text",   
		complete : function(response, status)   {    
			onPatientDeleteComplete(response.responseText, status);   
			}  
	}); 
}); 


function onPatientDeleteComplete(response, status) {  
	if (status == "success")  {   
		var resultSet = JSON.parse(response); 

if (resultSet.status.trim() == "success")   {    
	$("#alertSuccess").text("Successfully deleted.");    
	$("#alertSuccess").show(); 

    $("#divPatientGrid").html(resultSet.data);  
    
    } else if (resultSet.status.trim() == "error")   {   
    	$("#alertError").text(resultSet.data);    
    	$("#alertError").show();   
    	} 

} else if (status == "error")  {   
	$("#alertError").text("Error while deleting.");   
	$("#alertError").show();  
	} else  {   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show(); 
		} 
	} 

//==================================Client-Model=====================================================
function validatePatientForm(){
	
	//Name
	if($("#PatientName").val().trim() == ""){
		
		return "Insert Patient Name";
	}
	
	//Age
	if($("#Age").val().trim() == ""){
		
		return "Insert Patient Age";
	}
	
	//PhoneNo
	if($("#PhoneNo").val().trim() == ""){
		
		return "Insert Patient Contact Number";
	}
	
	//Email
	if($("#Email").val().trim() == ""){
		
		return "Insert Patient Email";
	}
	
	var emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var tmpEmail =  $("#Email").val().trim();
	if(!tmpEmail.match(emailReg)){
		return "Insert a valid Email...!";
	}
	
	//Address
	if($("#Address").val().trim() == ""){
		
		return "Insert Patient Address";
	}
	
	// is numerical value  
	var tmpAge = $("#Age").val().trim();  
	   if (!$.isNumeric(tmpAge))  {  
		   return "Insert a numerical value for Age."; 
		   } 
	   
	// is numerical value  
		var tmpPhoneNo = $("#PhoneNo").val().trim();  
		   if (!$.isNumeric(tmpPhoneNo))  {  
			   return "Insert a numerical value for Contact Number."; 
			   } 
		   var phoneReg = /^\d{10}$/;
			var tmpPhoneNo =  $("#PhoneNo").val().trim();
			if(!tmpPhoneNo.match(phoneReg)){
				return "Enter 10 digit Phone Number...!";
			}
		   
		   return true;
	 
	 
}