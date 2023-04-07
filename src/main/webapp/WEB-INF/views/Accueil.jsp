<html>
<head>
    <meta charset="utf-8">
    <title>BigCloud: Accueil</title>
    <style><%@include file="/WEB-INF/Style/style.css"%></style>
</head>
<body>
    <div class="container" align="center">
        <h1><a rel="dofollow">BigCloud</a></h1>

        <h2 class="h2-login">Bienvenue ${Nom}</h2>
        <h3> <span id="error">${errorMsg}</span></h3>

        <div>${tableau}</div>
        <br/>
        <a href="share">
            <button class="button-login">Partager un fichier</button>
        </a>
        <a href="upload">
            <button class="button-login">Televerser un fichier</button>
        </a>
    </div>
</body>
</html>