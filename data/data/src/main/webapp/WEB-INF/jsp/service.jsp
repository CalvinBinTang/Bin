<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
</head>
<body>
	<div><a href="/">back</a></div>
	<div display="none" id="fieldsCount"></div>
	<div class="container" style="min-height: 500px">
		<div class="starter-template">
	        <h1>Data Generation</h1>
			<p />
			<p />
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">Records to Generate</span>
				</div>
				<input type="text" class="form-control" aria-label="records" id="records" aria-describedby="basic-addon1">
			</div>
			<div class="form-check">
			  <input class="form-check-input" type="checkbox" value="" id="FilterOut" checked>
			  <label class="form-check-label" for="defaultCheck1">
			    Filter Out Fields? This will filter out the fields that no use frequently.
			  </label>
			</div>
			<h2>Table Information</h2>
	        <form class="form-horizontal" id="tableInfo">
	            <div class="input-group">
				  <select class="custom-select" id="tableSelect">
				    <option selected>Choose...</option>
				    <c:if test="${not empty tables}">
						<c:forEach var="table" items="${tables}">
							<option value="${table}" >${table}</option>
						</c:forEach>
					</c:if>
				  </select>
				  <div class="input-group-append">
				    <button class="btn btn-outline-secondary" type="submit" id="tableSearch">GO</button>
				  </div>
				</div>
				<h5> Table Name: </h5>
				<h6 id="tableName"></h6>
				<h5> Table Description: </h5>
				<h6 id="tableDescription"></h6>
	        </form>
	        <h3>-----------------------------------</h3>
	        <p />
	        <h2>Information</h2>
	        <form class="form-horizontal" id="generation">
	            <div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="preFix">Field Name Prefix</span>
					</div>
					<input type="text" class="form-control" aria-label="records" id="preFixContext" aria-describedby="basic-addon1">
				</div>
	        	<div class="accordion" id="tableDetail">
				</div>
				<button type="submit" class="btn btn-success" id="generationBtn">Generate</button>
	        </form>
	
	    </div>
	</div>

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function () {
		    $("#tableInfo").submit(function (event) {
		        event.preventDefault();
		        table_search_submit();
		    });
		    
		    $("#generation").submit(function (event) {
		        event.preventDefault();
		        data_generate_submit();
		    });
	
		});
		
		function table_search_submit(){
			var searchCondition = {};
			searchCondition["tableName"] = $("#tableSelect").val();
			searchCondition["filter"] =  $("#FilterOut").is(":checked");
		    $.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "/service/search",
		        data: JSON.stringify(searchCondition),
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
		        	$('#tableDescription').text(data["tableDescription"]);
		        	$('#tableName').text(data["tableName"]);
		        	var subtables = data["subTables"];
		        	var fieldCount = 0;
		        	var conText = "";
		        	var i = 0;
		        	$.each(subtables, function(index, sub){
		        		var subTableName = sub["subTableName"];
		        		var subTableDesc = sub["sbuTableDescription"];
		        		var fields = sub["fields"];
		        		fieldCount += fields.length * 1;
		        		conText += "<div class=\"card\">";
		        		conText += "<div class=\"card-header\" id=\""+subTableName+"\">";
		        		conText += "<h2 class=\"mb-0\">";
		        		conText += "<button class=\"btn btn-link collapsed\" type=\"button\" data-toggle=\"collapse\" data-target=\"#coll"+subTableName+"\" aria-expanded=\"false\" aria-controls=\"coll"+subTableName+"\">";
		        		conText += subTableName + " : " + subTableDesc; 
		        		conText += "</button></h2></div>";
		        		conText += "<div id=\"coll"+subTableName+"\" class=\"collapse\" aria-labelledby=\""+subTableName+"\" data-parent=\"#tableDetail\">";
		        		conText += "<div class=\"card-body\">";
		        		$.each(fields, function(index, value){
		        			conText += "<h6>Name: " + value["name"];  
			        	    if(value["isRequired"]){
			        	    	conText += "  <span style=\"color:#F00\" >  IsRequired: " + value["isRequired"] + "  </span> </h6>";
			        	    } else {
			        	    	conText += "</h6>";
			        	    }
			        	    conText += "<h6>Description: " + value["description"]  + " </h6>";
			        	    conText += "<h6>type: " + value["type"]  + " </h6>";
							conText += "<div class=\"form-check form-check-inline\"><input class=\"form-check-input\" type=\"radio\" name=\"radio"+i+"\" id=\"radio"+i+"\" value=\"fixed\" checked><label class=\"form-check-label\" for=\"radio"+i+"\">fixed</label></div>";
							conText += "<div class=\"form-check form-check-inline\"><input class=\"form-check-input\" type=\"radio\" name=\"radio"+i+"\" id=\"radio"+i+"\" value=\"random\"><label class=\"form-check-label\" for=\"radio"+i+"\">random</label></div>";
							conText += "<div class=\"form-check form-check-inline\"><input class=\"form-check-input\" type=\"radio\" name=\"radio"+i+"\" id=\"radio"+i+"\" value=\"auto-growth\"><label class=\"form-check-label\" for=\"radio"+i+"\">auto-growth</label></div>";
							conText += "<div class=\"input-group mb-3\"><div class=\"input-group-prepend\"><span class=\"input-group-text\" id=\"basic-addon1\">Prefix</span></div><input type=\"text\" class=\"form-control\" aria-describedby=\"basic-addon1\" id=\"pre"+i+"\"></div>";
							conText += "<div class=\"input-group mb-3\"><div class=\"input-group-prepend\"><span class=\"input-group-text\" id=\"basic-addon1\">Range Begin</span></div><input type=\"text\" class=\"form-control\" aria-describedby=\"basic-addon1\" id=\"rangeBegin"+i+"\"></div>";
							conText += "<div class=\"input-group mb-3\"><div class=\"input-group-prepend\"><span class=\"input-group-text\" id=\"basic-addon1\">Range End</span></div><input type=\"text\" class=\"form-control\" aria-describedby=\"basic-addon1\" id=\"rangeEnd"+i+"\"></div>";
							conText += "</br>";
							i++;
		        		});
		        		conText += "</div></div></div>";
		        	});
		        	$('#fieldsCount').val(fieldCount);
		        	$('#tableDetail').html(conText);
		        },
		        error: function (e) {
		            console.log("ERROR : ", e);
		        }
		    });
		}
		
		function data_generate_submit(){
			var commit = {};
			commit["tableName"] = $("#tableName").text();
			commit["records"] =  $("#records").val();
			var length = $("#fieldsCount").val()-1;
			var commitFields = [];
			for (var i = 0; i < length; i++) {
				var commitField = {};
				var radios = document.getElementsByName("radio"+i);
				for (var j = 0; j < radios.length; j++) {
					if (radios[j].checked) {
					 	commitField["type"] = radios[j].value;
				  	break;
				 	}
				}
				commitField["prefix"] = $("#pre"+i).val();
				commitField["rangeBegin"] = $("#rangeBegin"+i).val();
				commitField["rangeEnd"] = $("#rangeEnd"+i).val();
				commitFields.push(commitField);
			}
			commit["fields"] = commitFields;
			commit["isFilter"] = $("#FilterOut").is(":checked");
			commit["preFix"] = $("#preFixContext").val();
			var gen = JSON.stringify(commit);
			$.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "/service/generate",
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
