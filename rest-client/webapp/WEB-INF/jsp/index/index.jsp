<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/rest-client/css/styles.css">
	</head>
	<body>
		<div id="left">
			<h2>Resource Creation</h2>
			<script type="text/javascript">
				function callbackCriado(uri) {
					console.log(uri);
					return function(data, textStatus){
						$('#activities').append('<tr><td>' + data.method + '</td>'+
								+'<td>' + data.uri + '</td>'+
								+'<td>'+ data.responseCode +'</td>'+
								+'<td><a href="' + data.location +'" target="_blank">' + data.location + '</a></td></tr>');
					};
				}
				function cria() {
					var uri = $('#uri').val();
					$.getJSON('createSomething', $('#form').serialize(),
						callbackCriado(uri)
					);
				}
			</script>
			<form action="" method="post" id="form" >
			URI: <input type="text" name="uri" id="uri" style="width: 400px;">
				<br />
				Name: <input type="text" name="contentName" id="contentName"><br />
				Value: <textarea name="contentValue" style="width: 400px"></textarea>
				
				<br />
				
				<input type="button" value="Send"  onclick="cria()">
			</form>
		</div>
		<div id="right">
			<h2>Activity History</h2>
			<table width="100%">
				<thead>
					<th>Method</th>
					<th>URI</th>
					<th>Response code</th>
					<th>Location</th>
				</thead>
				<tbody id="activities">
					
				</tbody>
			</table>
		</div>
	</body>
</html>