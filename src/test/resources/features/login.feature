@login
Feature: Conexion et deconnexion à la page

  @login-passant
  Scenario: Connexion - cas passant
    Given Je suis sur la page de connexion
    When Je renseigne mon adresse "provi-test5@yopmail.com" et mon mot de passe "P@ss1234"
    Then Je suis connecté et rédirigé sur la page d'accueil
  @login-non-passant
  Scenario Outline: Connexion - non passant
    Given Je suis sur la page de connexion
    When je connecte avec des informations invalides <email> et <mdp>
    Then Le connexion echoue et je reçois le <msg>

    Examples:
      |email                     |mdp       |msg       |
      |"emailInexist@yopmail.com"|"P@ss1234"|"A user with this email does not exist"|
      |"provi-test5@yopmail.com" |"pass"    |"Make sure you type your email and password correctly. Both your password and email are case-sensitive."|

  @logout
  Scenario: Deconnexion - Cas passant
    Given Je suis connec
    When Je me déconnecte
    Then Je suis déconnecté et redirigé vers la page de connexionté à la plateforme



