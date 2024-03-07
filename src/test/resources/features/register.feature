@register
Feature: Création de compte
  En tant que qu'utilisateur, je souhaite créer mon compte afin d'utiliser le site W3

  Scenario: Création de compte - Cas passant
   Given Je suis sur la page d'inscription
   When Je renseigne mes informations valides
      |email                 |pwd     |fname|lname |
      |provi-test15@yopmail.com|P@ss1234|Djek |Provi |
   And Je confirme mon inscription grâce au lien reçu par mail de "W3schools" à "provi-test15@yopmail.com"
   Then Mon compte devrait être activé
   And Je devrais être redirigé vers la page d'accueil

  #Scenario: Création de compte - cas non passant
