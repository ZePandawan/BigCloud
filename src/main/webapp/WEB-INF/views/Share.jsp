<html>
<head>
    <meta charset="utf-8">
    <title>File Upload</title>
    <style><%@include file="/WEB-INF/Style/style.css"%></style>
</head>
<body>
<div class="container" align="center">
    <h1 class="upload-h1">Partage de fichier</h1>
    <form action="/share" method="POST">
        <span id="error">${errorMsg}</span>
        <span id="file-name"></span>
        <br>
        <input id="file-input" type="file" name="file" class="inputfile" />
        <input id="test_path" name="test_path" type="hidden" />
        <input type="submit" value="Upload" class="btn-upload" />
    </form>
</div>
</body>
</html>