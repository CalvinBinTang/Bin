<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
</head>
<body>

	<a href="/service">Service</a>
	<P />
	<a href="/sales">Sales</a>
	<form class="form-horizontal" id="download">
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text" id="basic-addon1">FileName</span>
		</div>
		<input type="text" class="form-control" aria-label="fileName" id="fileName" aria-describedby="basic-addon1">
	</div>
	<button type="submit" class="btn btn-success" id="downloadBtn">Download</button>
	</form>

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function () {
	    $("#download").submit(function (event) {
	        event.preventDefault();
	        table_download();
	    });
	});
	
	function table_download(){
		var fileName = $("#fileName").val();
       	$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: "/download?fileName="+fileName,
	        cache: false,
	        timeout: 600000,
	        success: function (res) {
	        	window.open("/download?fileName="+fileName);
	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
	        }
	    });
	}
	</script>

</body>

</html>
