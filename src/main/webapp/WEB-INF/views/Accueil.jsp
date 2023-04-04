<html>
<head>
  <meta charset="utf-8">
  <title>BigCloud: Accueil</title>
  <style><%@include file="/WEB-INF/Style/style.css"%></style>
</head>
<h1>BigCloud</h1>
<h2>Bienvenue ${Nom}</h2>
<h3> <span id="error">${errorMsg}</span></h3>

<div>${tableau}</div>
<a href="upload">
    <button>Televerser un fichier</button>
</a>
</html>