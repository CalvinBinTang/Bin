<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
</head>
<body>

	<form class="form-horizontal" id="exportJmeter">
		<label >Export Type : </label>
		<div class="form-check form-check-inline">
		  <input class="form-check-input" type="radio" name="Exptype" id="script" value="script">
		  <label class="form-check-label">Runnable Script</label>
		</div>
		<div class="form-check form-check-inline">
		  <input class="form-check-input" type="radio" name="Exptype" id="fragment" value="fragment" checked>
		  <label class="form-check-label">Fragment</label>
		</div>
		<div class="input-group mb-3">
		  <div class="input-group-prepend">
		    <span class="input-group-text"  id="interface">Interface Name</span>
		  </div>
		  <input type="text" class="form-control" aria-describedby="basic-addon1"  id="interfaceName">
		</div>
		<div class="input-group mb-3">
		  <div class="input-group-prepend">
		    <span class="input-group-text" id="method" >Method Name</span>
		  </div>
		  <input type="text" class="form-control" aria-describedby="basic-addon1"  id="methodName">
		</div>
		<div class="input-group">
		  <div class="input-group-prepend">
		    <span class="input-group-text" id="script" >SOAP Script</span>
		  </div>
		  <textarea class="form-control" id="soapBody"></textarea>
		</div>
		<h5>Enter Validation Fields Separated by Comma</h5>
		<div class="input-group">
		  <div class="input-group-prepend">
		    <span class="input-group-text" id="validate" >Validate Field Names</span>
		  </div>
		  <textarea class="form-control" id="fields"></textarea>
		</div>
		<h5>Enter Extractor Fields Separated by Comma</h5>
		<div class="input-group">
		  <div class="input-group-prepend">
		    <span class="input-group-text" id="extractor" >Extractor Field Names</span>
		  </div>
		  <textarea class="form-control" id="exfields"></textarea>
		</div>
		<h5>Special Extractor Fields Separated by Comma - Fields which cannot extractor by SOAP field Name such as "&#60WorkCode&#47&#62"</h5>
		<div class="input-group">
		  <div class="input-group-prepend">
		    <span class="input-group-text" id="special" >Special Extractor Field Names</span>
		  </div>
		  <textarea class="form-control" id="spfields"></textarea>
		</div>
		<div class="input-group mb-3">
		  <div class="input-group-prepend">
		    <span class="input-group-text"  id="component">Component Name</span>
		  </div>
		  <input type="text" class="form-control" aria-describedby="basic-addon1"  id="componentName">
		</div>
		<button type="submit" class="btn btn-success" id="generationBtn">Generate</button>
	</form>

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		$(document).ready(function () {
		    $("#exportJmeter").submit(function (event) {
		        event.preventDefault();
		        export_jmeter_submit();
		    });
		});
		
		function export_jmeter_submit(){
			var commit = {};
			commit["interfaceName"] = $("#interfaceName").val();
			commit["methodName"] =  $("#methodName").val();
			commit["soapBody"] =  $("#soapBody").val();
			commit["fieldName"] = $("#fields").val().split(',');
			commit["exFieldName"] = $("#exfields").val().split(',');
			commit["spFieldName"] = $("#spfields").val().split(',');
			commit["exportType"] = $("input[name='Exptype']:checked").val();
			commit["dataComponentName"] = $("#componentName").val();
			var gen = JSON.stringify(commit);
			$.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "/jmeter/generate",
		        data: gen,
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
		        	alert("success");
		        	$.ajax({
				        type: "GET",
				        contentType: "application/json",
				        url: "/download?fileName="+data["fileName"],
				        cache: false,
				        timeout: 600000,
				        success: function (res) {
				        	window.open("/download?fileName="+data["fileName"]);
				        },
				        error: function (e) {
				            console.log("ERROR : ", e);
				        }
				    });
		        },
		        error: function (e) {
		            console.log("ERROR : ", e);
		        }
		    });
		}
		
	</script>

</body>

</html>
