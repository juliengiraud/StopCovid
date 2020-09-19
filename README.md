# Le projet StopCovid

![STOP (image libre de droit)](rapport/stop.png) ![COVID (image libre de droit)](rapport/covid.png)

Réalisé par **Julien Giraud** (P1704709) et **Ulysse Regnier** (P1711637)

## Présentation du projet

StopCovid est le prototype d'une application pour smartphone dont le but est de limiter au maximum la propagation du covid-19.  
Pour luter efficacement contre le virus nous informons les utilisateurs à risque qu'ils ont été en contact avec le virus, afin qu'ils limitent au maximum leurs interractions sociales en attendant de savoir s'ils sont contaminés.

## Aperçus

| Interface utilisateur | Interface serveur |
| --------------------- | ----------------- |
| ![Visuel utilisateur](rapport/userView.png) | ![Visuel serveur](rapport/serverView.png) |

Sur notre prototype vous trouverez ces interfaces dans la même fenêtre.

| Interface | Description |
| --------- | ----------- |
| Utilisateur | Les utilisateurs disposent d'une interface sur laquelle il peuvent voir leur statut, se déclarer infecté, indiquer qu'ils sont en contact avec des personnes et supprimer des contacts.
| Serveur | Le serveur quant à lui peut décider que des utilisateurs suffisamment proches deviennent des contacts, il s'occupe de sauvegarder toutes les informations des utilisateurs et détermine grace à un algorithme paramétrable si certains utilisateurs sont risqués. |

## Lancer l'application

Malheureusement nous n'avons pas réussi à générer un `.jar` qui s'exécute sans problème (erreurs avec JavaFX), il faut donc lancer l'application depuis les sources.

1. Assurez-vous d'avoir **maven** et **openJDK 11** installé sur votre machine

2. Ouvrez un terminal dans le dossier stopcovid

3. Exécutez la commande suivante

    ```shell
    mvn clean install compile exec:java
    ```

## Design Paterns

Donner une motivation des choix d’architecture (et des patterns choisis) en s’aidant de **diagrammes simples** adaptés au degré de précision et au type d’explication. Donc des **diagrammes de classe, mais pas que** cela, et pas de plats de spaghettis générés automatiquement représentant tout le code.

Dans le cadre de ce projet nous avons mise en place pas moins de 6 design paterns qui ont chacun un role **primordiale** dans le bon fonctionnement de notre application.

### Model View Controller (MVC)

Toute l'**architecture** de notre application est basée sur un MVC afin de bien séparer les différentes parties du code.

Notre MVC fonctionne de la façon suivante :

- Le contrôleur à **accès** au model et la vue a accès au contrôleur.
- La vue **demande** au contrôleur les informations à **afficher**, le contrôleur les récupèrent dans le modèle.
- Lorsqu'une **action** se produit sur la vue (une rencontre par exemple), celle-ci **informe** le contrôleur qui mettra à jour modèle.
- *Il y a également un système de notification pour les mises à jour de la vue, nous en parlons après.*

![Schéma UML à mettre](rapport/mvc.png)

Ainsi les vues sont complètement indépendantes du modèle.

```java
new MainView(stage, WIDTH, HEIGHT, controller);
new MainView(new Stage(stage.getStyle()), WIDTH, HEIGHT, controller);
```

Nous avons utilisé le code ci-dessus afin de vérifier le bon fonctionnement de notre MVC. Il permet d'instancier deux fenêtres de l'application StopCovid branchées sur le même contrôleur.  
Après utilisation, on constate que chaque modification faite sur l'une des deux fenêtres se retrouve instantanément faite sur l'autre, ce qui est logique puisqu'elles affichent les données du même modèle.

Conclusion, notre super MVC fonctionne !

### Observer

Une fois l'architecture MVC en place nous avions absolument besoin d'un pattern observer afin de gérer la mise à jour des composants graphiques.

![Schéma UML à mettre](rapport/observer.png)

Cette solution est vraiment indispensable car notre ancien système de mise à jour des vues était très mauvais en deux points.

- Il y avait des spaghettis d'appels de fonctions avec des noms différents.
- Le mécanisme utilisé se résumait à stocker des références de partout et parcourir toutes les vues afin de leur demander à toutes de se mettre à jour.

L'implémentation du pattern observer a permis de clarifier ces fonctions de mise à jour et de gagner en perfornance car nous ne mettons à jour que les composants graphiques qui ont besoin de l'être.

#### *L'anecdote de ce pattern*

> Nous avons tous les deux déjà utilisé le pattern observer avec Java dans le cadre d'un TP pendant notre DUT informatique, il y a deux ans. Nous venons seulement de comprendre que l'objectif de ces TP était de nous faire utiliser un système de notifications.  
> Mieux vaut trop tard que jamais !

Bilan du pattern, le code relatif aux mises à jour graphiques est plus facile à lire, plus performant et plus facile à maintenir que notre implémentation précédente.

### Data Access Object (DAO) et Singleton

Lorsque notre application démarre le contrôleur est instancié, il a alors besoin de récupérer la liste des utilisateurs du système depuis une base de données super sécurisée. Pour éviter au contrôleur de gérer cette partie, nous avons implémenté un DAO qui fonctionne en singleton.

![Schéma UML à mettre](rapport/dao-singleton.png)

Le DAO s'occupe d'établir une connexion sécurisée à la base de données du système et il offre des méthodes publiques pour interragir avec celle-ci, sans se préocuper du mécanisme de connexion ou du langage de requête.

Le singleton quant à lui permet de limiter les instances du DAO au nombre de une. Ainsi, un seul DAO est instancié et on ne fait qu'une seule connexion à la base de données.

L'avantage de cette méthode est qu'il est possible de changer de technologie de base de données sans modifier une ligne de code dans le contrôleur.

> Ça tombe plutôt bien car pour des raisons budgétaires nous avons dû nous résoudre à abandonner la base de données super sécurisée au profit d'un fichier CSV.

### Strategy

Notre classe modèle a besoin de mettre en oeuvre différents algorithmes afin de choisir quels utilisateurs sont risqués. Afin d'abstraire l'implémentation de ces algorithmes du point de vue de la classe User nous avons utilisé le pattern stratégie.

![Schéma UML à mettre](rapport/strategy.png)

Ce pattern nous permet d'avoir autant d'implémentations que l'on veut pour l'algorithme, sans modifier une seule ligne de code dans la classe User.

```java
List<User> riskyUsers = riskStrategy.getRiskyContacts(this);
```

Concrètement la classe User se contente d'appeler la méthode `getRiskyContacts` sur un objet de type `RiskStrategy` (voir ci-dessus). Cet objet correspond sistématiquement à une stratégie spécifique, sélectionnée par le serveur.

Ce pattern est redoutablement efficace pour abstraire différentes versions d'une même méthode, pour autant son implémentation se résume à une utilisation basique des interfaces Java.

### Builder

Pour des raisons évidentes de sécurité, nous avons une forte séparation du personnel qui travail sur notre application. Ainsi, les personnes chargées de gérer la base de données n'ont pas accès au code du contrôleur, et vice-versa. Cependant, à l'initialisation du contrôleur nous avions besoin d'ajouter des utilisateurs dans l'application, nous avons donc mis en place un builder sur le contrôleur afin de résoudre ce problème.

![Schéma UML à mettre](rapport/builder.png)

Plus sérieusement, nous avons mis en place le pattern builder sur le contrôleur afin de lui ajouter des utilisateurs depuis sa ligne de code d'initialisation.

```java
Controller controller = new ControllerBuilder()
        .addUser("Sally")
        .addUser("Roxanne")
        .addUser("Alberto")
        .build();
```

En réalité ce pattern n'est pas du tout essentiel au bon fonctionnement du projet, mais c'est une solution élégante et pratique pour ajouter les utilisateurs au contrôleur. Nous en avons également profité pour pour utiliser la méthode de sauvegarde de notre DAO. En effet, les utilisateurs rajoutés par le builder sont automatiquement sauvegardés dans le fichier CSV à condition d'en informer le builder avec un `.build(true)`.

## Tests

Ici j'ai envie qu'on parle du casse-tête que c'est d'immaginer toutes les combinaisons d'action d'une application

J'ai aussi envie qu'on parle de la propreté du code, surtout avec le test du checkstyle parce que sérieux, faut avouer que même si ça nous fait chier ça force à coder propre, à bien commenter et ça au long terme c'est vachement pratique

Faut aussi expliquer (sans trop être négatif) qu'on est loin d'avoir testé toutes les lignes de code et tous les cas d'utilisation, dans un monde idéal rien que pour le modèle à chaque cas d'utilisation spécifique avec A et B action en entrée il faudrait vérifier le résultat de (A.B), (A.-B), (-A.B), (-A.-B). Nous on a plutôt tendance à en vérifier un ou deux sur les 4 pour chaque cas...

## Étique

Là faut parler du fait que les utilisateurs savent qui ils ont rencontré, c'est quand même pas ouf, faudrait au minimum remplacer les noms par les ID des users

Bon ensuite j'aimerai une petite blague de genre 3 lignes sur la sécurité de notre base de données claquée au sol

Ensuite ya le côté serveur qui connait la totalité des données, en vraie c'est dommage parce que vu que c'est l'utilisateur qui informe sa liste de contacte on pourrait immaginer une application où le serveur connais juste la liste des utilisateurs sans connaître leurs contacts -> dans ce cas là il faudrait rajouter une couche de séparation : vue utilisateur, BACK UTILISATEUR, vue serveur, back serveur

Il faudrait dire qu'une bonne idée d'évolution serait de remplacer la liste de contacts par une liste de rencontre avec "rencontre" défini comme un lieu et une date, comme ça tu sais pas qui tu as rencontré et tu peux les supprimer après 3 semaines automatiquement par exemple (si pas de contamination), ça serait d'autant mieux si avec ça on ajoute l'idée que le serveur ne connais pas les rencontres et qu'elles sont enregistrées dans les données des appli utilisateurs
