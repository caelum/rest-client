<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/rest-client/css/styles.css">
	</head>
	<body>
		<div>
			<a href="#" onclick="showCreation()">Creation</a> | <a href="#" onclick="showView()">View</a>
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
					return function(result){
						console.log(result);
						$('#activities').append('<tr><td>' + result.method + '</td>\
								<td>' + result.uri + '</td>\
								<td>' + result.responseCode + '</td>\
								<td><a href="#" onclick="view(\'' + result.location + '\')">' + result.location + '</a></td></tr>');
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
					$('#view').html('Loading...');
					$.getJSON('grab', { "uri" : uri},
						function(result){
							$('#view').html(result.response);

							$.each(result.links, function() {
								$('#view').append('<br />Link: ' + this.href + ' - ' + this.rel);
							});
						}
					);
				}

				function cleanUpActivities() {
					$.post('cleanUpActivities', function() {
						$('#activities').empty();
					});
				}
			</script>
			<form action="" method="post" id="form" >
			<p><label for="uri">URI:</label><input type="text" name="uri" id="uri" style="width: 400px;"/></p>
			<p><label for="method">Method:</label><select name="method" id="method">
					<option value="POST">POST</option>
					<option value="GET">GET</option>
					<option value="PUT">PUT</option>
					<option value="DELETE">DELETE</option>
					<option value="OPTIONS">OPTIONS</option>
				</select></p>
				<p><label for="contentName">Name:</label><input type="text" name="contentName" id="contentName"></p>
				<p><label for="contentValue">Value:</label><textarea id="contentValue" name="contentValue" style="width: 400px"></textarea></p>
				
				<input type="button" value="Send"  onclick="cria()">
			</form>
			</div>
			<div id="view" style="display: none;">
			</div>
		</div>
		<div id="right">
			<h2>Activity History</h2>
			<input type="button" value="CleanUp activities" onclick="cleanUpActivities()" />
			<table width="100%">
				<thead>
					<th>Method</th>
					<th>URI</th>
					<th>Response code</th>
					<th>Location</th>
				</thead>
				<tbody id="activities">
					<c:forEach items="${activities}" var="activity">
						<tr>
							<td>${activity.method}</td>
							<td>${activity.uri }</td>
							<td>${activity.responseCode}</td>
							<td><a href="#" onclick="view('${activity.location}')">${activity.location}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>