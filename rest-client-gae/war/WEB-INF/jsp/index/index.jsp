<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/styles.css">
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
					return function(result){
						logActivity(result.method, result.uri, result.responseCode, result.location);
					};
				}

				function logActivity(method, uri, responseCode, location) {
					$('#activities').append('<tr><td>' + method + '</td>\
							<td>' + uri + '</td>\
							<td>' + responseCode + '</td>\
							<td><a href="#" onclick="view(\'' + location + '\')">' + location + '</a></td></tr>');
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
							$('#view').html('').append($('<pre></pre>').text(result.response));

							$.each(result.links, function() {
								$('#view').append('<br /><form id="formNavigate'+ this.rel +'">\
									<input type="hidden" name="href" value="'+ this.href +'"/>Execute a \
									<input type="hidden" name="rel" value="' + this.rel + '"/>\
									<select name="method" id="method">\
									<c:forEach items="${methods}" var="method">\
										<option value="${method}">${method}</option>\
									</c:forEach>\
									</select> in order to \
									<input type="button" value="' + this.rel + '" onclick="navigate(\''+ this.rel +'\')">');
							});
						}
					);
				}

				function navigate(rel) {
					$.getJSON('navigate', $('#formNavigate' + rel).serialize(), function(result) {
						logActivity(result.method, result.uri, result.responseCode, '');
					});	
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
				<c:forEach items="${methods}" var="method">
					<option value="${method}">${method}</option>
				</c:forEach>
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
					<tr>
						<th>Method</th>
						<th>URI</th>
						<th>Response code</th>
						<th>Location</th>
					</tr>
				</thead>
				<tbody id="activities">
					<c:forEach items="${activities.activities}" var="activity">
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