<html>
<head>
	<meta charset="utf-8">
	<title>File Upload</title>
	<style><%@include file="/WEB-INF/Style/style.css"%></style>
</head>
<body>
<div class="container" align="center">
	<h1 class="upload-h1">Televersez votre fichier !</h1>
	<form action="/upload" method="POST" enctype="multipart/form-data" class="upload-form">
      <label for="file-input" class="custom-file-upload">
        <i class="fa fa-cloud-upload"></i> Choisissez le fichier...
      </label>
      <span id="error">${errorMsg}</span>
      <span id="file-name"></span>
      <br>
      <input id="file-input" type="file" name="file" class="inputfile" />
        <input id="test_path" name="test_path" type="hidden" />
      <input type="submit" value="Envoyer" class="btn-upload" />
    </form>
</div>
<script>
    const fileInput = document.getElementById('file-input');
    const fileName = document.getElementById('file-name');
    fileInput.addEventListener('change', function() {
        console.log(this.value);
        fileName.textContent = this.value.split("\\").pop();
    });



  </script>
</body>
</html>