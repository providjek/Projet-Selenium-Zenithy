@search-exercise
Feature: Rechercher et realiser des exercices

  Scenario: Recherche un Exercice
    Given Je suis connecté et sur la page d'acceuil
    When Je recherce "Exercise 1 Html" dans la plateforme
    Then J'ai des résultats d'elements à la fin de la recherche
    And Je choisis l'Exercice 1

    Scenario: Realisation d'un exercice
      Given J'ai choisi et ouvert "Exercise 1 Html Basic"
      When Je reponds aux questionnaires de l'exercice
      And Je valides mes réponses
      Then  Je peux voir mon score sur l'exercice
