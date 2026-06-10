# Jeu de la Devinette (Style Pendu) - Application Android

Ce projet est une application Android native développée en **Java** dans le cadre d'un Travaux Pratiques (TP). L'objectif est de concevoir un jeu de devinette où l'utilisateur doit trouver un nombre mystère généré aléatoirement, avec une intégration progressive d'un visuel de jeu du pendu à chaque tentative échouée.

---

## 🚀 Fonctionnalités

- **Génération Aléatoire :** Un nombre secret entre 1 et 100 est généré automatiquement au lancement de la partie.
- **Stockage Sécurisé (Tag) :** Conformément aux consignes, le nombre à chercher est stocké de manière invisible dans l'attribut `tag` du `TextView` de résultat.
- **Interface Proportionnelle :** Utilisation de `layout_weight` dans un `LinearLayout` horizontal pour garantir une répartition stricte de la zone de saisie (**80%**) et du bouton de validation (**20%**).
- **Indicateurs de Guidage :** L'application indique si le nombre saisi est "trop petit" ou "trop grand".
- **Évolution Dynamique du Pendu :** - État initial : Potence vide (`img.png`).
  - Erreurs 1 à 5 : Ajout progressif des membres du personnage (`img_1.png` à `img_5.png`).
  - Erreur 6 (Échec total) : Affichage du personnage complètement pendu (`img_6.png`) et fin de la partie.
  - Victoire : Affichage d'une image de célébration (`img_7.png`).
- **Gestion de Fin de Partie :** Désactivation automatique des champs d'interaction (`EditText` et `Button`) dès que la partie se termine (victoire ou défaite).

---

## 📁 Structure du Projet

Le projet repose sur l'architecture standard d'Android Studio :

### 1. Interface Utilisateur (`/res/layout/activity_main.xml`)
Un agencement propre structuré verticalement contenant :
- Un `LinearLayout` horizontal avec `weightSum="100"` pour la répartition 80/20 de la saisie (`EditText`) et du bouton (`Button`).
- Un `TextView` central servant à la fois pour l'affichage textuel des indices et de conteneur pour le `Tag` de l'application.
- Un `ImageView` adaptatif en bas pour la progression visuelle du pendu.

### 2. Logique Métier (`/java/.../MainActivity.java`)
Contient l'ensemble des interactions et algorithmes du jeu :
- Liaison des composants graphiques via `findViewById`.
- Algorithme de vérification des saisies, gestion des exceptions (champs vides).
- Structure conditionnelle (`switch`) pour la mise à jour dynamique des ressources drawables en fonction du compteur d'erreurs.

### 3. Ressources Visuelles (`/res/drawable/`)
Le dossier contient les images officielles fournies pour le TP :
- `img.png` : La potence de départ.
- `img_1.png` à `img_6.png` : Les étapes d'échec du pendu.
- `img_7.png` : L'icône de succès / victoire.

---

## 🛠️ Installation et Exécution

1. **Cloner le projet** ou télécharger les fichiers sources.
2. Ouvrir le dossier racine avec **Android Studio**.
3. S'assurer que les images (`img.png` à `img_7.png`) sont bien placées dans le répertoire `app/src/main/res/drawable/`.
4. Synchroniser le projet avec **Gradle** (si demandé).
5. Lancer l'application sur un Émulateur (AVD) ou sur un appareil physique Android connecté en mode Débogage USB.

---

## 🎓 Contexte

Projet réalisé dans le cadre du cursus universitaire de la **Faculté des Sciences Appliquées (FSA) d'Ait Melloul**.
