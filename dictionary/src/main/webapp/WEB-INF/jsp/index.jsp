<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
</head>
<body>

	<form class="form-horizontal" id="search">
		<div class="input-group mb-3">
		  <div class="input-group-prepend">
		    <span class="input-group-text"  id="interface">Field Name (Code)</span>
		  </div>
		  <input type="text" class="form-control" aria-describedby="basic-addon1"  id="fieldName">
		</div>
		<button type="submit" class="btn btn-success" id="searchBtn">Search</button>
	</form>
	<div id="response">
		<table class="table table-hover" id="fieldsTable">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Entity Name</th>
		      <th scope="col">Code</th>
		      <th scope="col">Description</th>
		      <th scope="col">Label Code</th>
		      <th scope="col">Label Description</th>
		      <th scope="col">Data Type</th>
		      <th scope="col">Field Length</th>
		      <th scope="col">Mandatory</th>
		      <th scope="col">Read Only</th>
		      <th scope="col">Sort-able</th>
		      <th scope="col">Hidden</th>
		      <th scope="col">Hidden for UI</th>
		      <th scope="col">Display format</th>
		    </tr>
		  </thead>
		  <tbody id="tbodyMain"></tbody>
		</table>
	</div>

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function () {
	    $("#search").submit(function (event) {
	        event.preventDefault();
	        field_search();
	    });
	});
	
	function field_search(){
		var fieldName = $("#fieldName").val();
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/dictionary/search",
	        data: fieldName,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	var context = "";
	        	$.each(data["infos"], function (index, field) {
	        		context += "<tr><th scope=\"row\">"+(index+1)+"</th>";
	        		context += "<td>"+field["entityName"]+"</td>";
	        		context += "<td>"+field["strCode"]+"</td>";
	        		context += "<td>"+field["strDesc"]+"</td>";
	        		context += "<td>"+field["labelCode"]+"</td>";
	        		context += "<td>"+field["labelDesc"]+"</td>";
	        		context += "<td>"+field["dataType"]+"</td>";
	        		context += "<td>"+field["length"]+"</td>";
	        		context += "<td>"+field["mandatory"]+"</td>";
	        		context += "<td>"+field["readOnly"]+"</td>";
	        		context += "<td>"+field["sortable"]+"</td>";
	        		context += "<td>"+field["hidden"]+"</td>";
	        		context += "<td>"+field["hiddenForUI"]+"</td>";
	        		context += "<td>"+field["displayFormat"]+"</td>";
	        		context += "</tr>";
	        	});
	        	$('#tbodyMain').html(context);
	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
	        }
	    });
	}
	</script>

</body>

</html>
