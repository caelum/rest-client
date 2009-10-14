<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/rest-client/css/styles.css">
	</head>
	<body>
		<div>
			<a href="javascript:showCreation(); return false;">Creation</a> | <a href="javascript:showView(); return false;">View</a>
		</div>
		<div id="left">
			<div id="creation">
			<h2>Resource Creation</h2>
			<script type="text/javascript">
				function showCreation() {
					$('#creation').show();
					$('#view').hide();
				}
				function showView() {
					$('#creation').hide();
					$('#view').show();
				}
				function callbackCriado(uri) {
					console.log(uri);
					return function(result, status){
						$('#activities').append('<tr><td>' + result.method + '</td>\
								<td>' + result.uri + '</td>\
								<td>' + result.responseCode + '</td>\
								<td><a href="javascript:view(' + result.location + '); return false;">' + result.location + '</a></td></tr>');
					};
				}
				function cria() {
					var uri = $('#uri').val();
					$.getJSON('createSomething', $('#form').serialize(),
						callbackCriado(uri)
					);
				}
				function view(uri) {
					$('#creation').hide();
					$('#view').show();
					$('#view').load(uri);
				}
			</script>
			<form action="" method="post" id="form" >
			URI: <input type="text" name="uri" id="uri" style="width: 400px;"/>
				<br />
				HTTP Method: <select name="method">
					<option value="POST">POST</option>
					<option value="GET">GET</option>
					<option value="PUT">PUT</option>
					<option value="DELETE">DELETE</option>
					<option value="OPTIONS">OPTIONS</option>
				</select>
				<br />
				Name: <input type="text" name="contentName" id="contentName"><br />
				Value: <textarea name="contentValue" style="width: 400px"></textarea>
				
				<br />
				
				<input type="button" value="Send"  onclick="cria()">
			</form>
			</div>
			<div id="view" style="display: none;">
			</div>
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