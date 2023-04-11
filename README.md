# Projet Systemes Distribué

/!\ A lire avant d'upload un fichier !

Il faut faire attention car avec la libraire utilisée actuellement nous ne pouvons pas upload des fichiers de grande taille.
Pour tester il vaut mieux privilégier des fichiers txt / pdf ... avec des tailles assez petites.

Voici ci-dessous le schéma de la base de données

DATABASE :

| **Users** |   **Files**   |
|:---------:|:-------------:|
| id_users  | id_file       |
| name      | name          |
| mail      | size          |
| password  | creation_date |
| space     | file_location |
|           | owner         |
|           | shares        |
|           | time          |
