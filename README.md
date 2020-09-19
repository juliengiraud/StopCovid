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

### Model-View-Controller (MVC)

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

### Builder

```java
Controller controller = new ControllerBuilder()
        .addUser("Sally")
        .addUser("Roxanne")
        .addUser("Alberto")
        .build();
```

### Strategy

### DAO / Singleton

## Étique
