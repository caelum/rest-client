<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	</head>
	<body>
		<div id="left">
			<h2>Resource Creation</h2>
			<script type="text/javascript">
				function callbackCriado(uri) {
					console.log(uri);
					return function(data, textStatus){
						
					};
				}
				function cria() {
					var uri = $('#uri').val();
					$.getJSON("createSomething", $('#form').serialize(),
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
		
		</div>
	</body>
</html>