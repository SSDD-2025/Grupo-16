# Name of the Application TBD

## Participants of Group 16

<table>
  <thead>
    <th>Full Name</th>
    <th>Degree</th>
    <th>University Email</th>
    <th>Github Account</th>
  </thead>
  <tbody>
    <tr>
      <td>Arminda García Moreno</td>
      <td>GII + MAT</td>
      <td>a.garciamore.2022@alumnos.urjc.es</td>
      <td>armiiin-13</td>
    </tr>
    <tr>
      <td>Alfonso Rodríguez Gutt</td>
      <td>GII</td>
      <td>a.rodriguezgu.2022@alumnos.urjc.es</td>
      <td>AlfonsoRodr</td>
    </tr>
    <tr>
      <td>David Rísquez Gómez</td>
      <td>GII + GMAT</td>
      <td>d.risquez.2022@alumnos.urjc.es</td>
      <td>davidrisqgom</td>
    </tr>
  </tbody>
</table>

## Execution Instructions
<strong><em>AQUÍ HABRÍA QUE INDICAR LOS PASOS QUE HAY QUE SEGUIR PARA DESCARGARSE EL REPOSITORIO DESDE GITHUB, COMO EJECUTAR LA APLICACIÓN Y LAS VERSIONES NECEESARIAS. </em></strong>

<strong><em>INDICAR TAMBIÉN EL USUARIO Y CONTRASEÑA DEL USUARIO ADMINISTRADOR. </em></strong>

## Entities Information
Next, all the entities that are part of the application will be shown, as well as the relationships between them.

### UserEntity
<table>
  <thead>
    <th>Related Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
  </thead>
  <tbody>
    <tr>
      <td>Artist</td>
      <td>1..N</td>
      <td>@OneToMany</td>
    </tr>
    <tr>
      <td>Ticket</td>
      <td>1..N</td>
      <td>@OneToMany</td>
    </tr>
  </tbody>
</table>

### Artist
<table>
  <thead>
    <th>Related Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
  </thead>
  <tbody>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
  </tbody>
</table>

### Concert
<table>
  <thead>
    <th>Related Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
  </thead>
  <tbody>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
  </tbody>
</table>

### Ticket
<table>
  <thead>
    <th>Entity</th>
    <th></th>
  </thead>
  <tbody>
    <tr>
      <td>UserEntity</td>
      <td>N..1</td>
      <td>@ManyToOne</td>
    </tr>
    <tr>
      <td>Concert</td>
      <td>N..1</td>
      <td>@ManyToOne</td>
    </tr>
  </tbody>
</table>


To provide better clarity when viewing these relationships, the relational diagram of the application is shown below.

<strong><em>INSERTAR FOTO DEL DIAGRAMA RELACIONAL AQUÍ </em></strong>

## User Privileges
<strong><em>EN ESTA SECCIÓN HAY QUE EXPLICAR BREVEMENTE LOS PERMISOS DE CADA TIPO DE USUARIO. TAMBIÉN HAY QUE INDICAR DE QUÉ ENTIDADES ES DUEÑO CADA USUARIO </em></strong>

## Image Management
<strong><em>EN ESTA SECCIÓN HAY QUE INDICAR QUÉ ENTIDADES TIENEN ASOCIADAS UNA O VARIAS IMÁGENES POR CADA OBJETO/REGISTRO </em></strong>

## Application Functionality Overview
<strong><em>AQUÍ SE EXPLICARÁ A GROSSO MODO LA FUNCIONALIDAD DE LA APLICACIÓN </em> </strong>
<strong><em> AQUÍ SE INSERTARÍA EL DIAGRAMA DE NAVEGACIÓN Y EL DIAGRAMA DE CLASES/TEMPLATES </em></strong>

## Team Members Participation
In this section, each of the participants in the development of the application will explain the tasks they have been responsible for, showing their most notable commits, and those files on which they worked the most.

<strong><em>EN ESTA SECCIÓN SE INSERTARÍA EL LINK PARA EL TRELLO. SE IRÁ VIENDO SI AL INICIO (JUSTO AQUÍ) O AL FINAL, ES DECIR, DESPUÉS DE QUE CADA UNO HAYA TERMINADO DE EXPLICAR SU PARTE. </em></strong>

## Alfonso Rodríguez Gutt
<strong> Commit List </strong>
1. [Adding all User files (Entity, Service and Repository)](https://github.com/SSDD-2025/Grupo-16/commit/04e8fe9fb732fa6bf1aa67009ebd2a9d1cfafaee).
2. [Adding all Ticket files (Entity, Service and Repository)](https://github.com/SSDD-2025/Grupo-16/commit/db40a969aab6353c5729374c2beefb25568a1102).
3. [Addition to the TicketController of the ticket purchase method.](https://github.com/SSDD-2025/Grupo-16/commit/bcf4139c1fcbdff3650e72fffc5418e7b155c4b6).
4. [Fixed some aspects for the TicketController](https://github.com/SSDD-2025/Grupo-16/commit/524a4c0d2374c04c513546deedbb755d088dea5d).
5. [Re-design of the sign-in/sign-up page](https://github.com/SSDD-2025/Grupo-16/commit/5383e7c5c7b46133c9e9a3a6c26a8f4f0283cddc).

<strong>Note: The third commit has a continuation and is the following:</strong> [Third Commit Continuation](https://github.com/SSDD-2025/Grupo-16/commit/afae2f872d3f723196328732de9148975db88888).

<strong>Note: Accessing commits with the links specified above may not reflect the current version of the file. This is because there were smaller commits after that, where small errors were corrected, comments were added, etc.</strong>

<strong>Files with the most participation</strong>
1. UserController.java <strong><em>(SE METERÁ UN ENLACE AL ARCHIVO)</em></strong>
2. UserService.java <strong><em>(SE METERÁ UN ENLACE AL ARCHIVO)</em></strong>
3. TicketController.java <strong><em>(SE METERÁ UN ENLACE AL ARCHIVO)</em></strong>
4. TicketService.java <strong><em>(SE METERÁ UN ENLACE AL ARCHIVO)</em></strong>
6. ConcertRepository.java <strong><em>(SE METERÁ UN ENLACE AL ARCHIVO)</em></strong>

In addition to these files, I worked on other files with a lower workload, or that did not have the same level of importance as the previous ones mentioned. These are:
1. UserEntity.java
2. UserRepository.java
3. Ticket.java
4. TicketRepository.java
5. ConcertService.java
6. CardVerifyingService.java
7. sign-in.html and its correspondant css (sign-in-styles.css).
8. error.html and its correspondant css (message-styles.css). This css is made in collaboration with colleague Arminda García Moreno.
9. sign-in-validation.html and its correspondant css (validation-styles.css).
10. sign-up-validation.html and its correspondant css (validation-styles.css).
11. purchase-confirmation.html and its correspondant css (purchase-confirmation-styles.css).

Once the files in which I was a participant have been mentioned, I will explain in detail what was done in the most notable files:

### UserController
This `@Controller` will be in charge of managing everything related to user actions in the web application.

This will be all the methods this `@Controller` will have:

<strong>Note: The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author. </strong>

<table>
  <thead>
    <th>Name</th>
    <th>Returning Template</th>
    <th>Mapping Type</th>
    <th>URL</th>
    <th>Parameters</th>
    <th>Description</th>
  </thead>
  <tbody>
    <tr>
      <td>signInUser</td>
      <td>sign-in</td>
      <td>@GetMapping</td>
      <td>/sign-in</td>
      <td>Model model</td>
      <td>It will show the log in display by setting the mustache variable (existUser) to true</td>
    </tr>
    <tr>
      <td>signUpUser</td>
      <td>sign-in</td>
      <td>@GetMapping</td>
      <td>/sign-up</td>
      <td>Model model</td>
      <td>It will show the register display by setting the mustache variable (existUser) to false</td>
    </tr>
    <tr>
      <td>verifySignIn</td>
      <td>sign-in-validation or sign-in (in case of error)</td>
      <td>@PostMapping</td>
      <td>/sign-in/validation</td>
      <td>Model model, @RequestParam String userName, password</td>
      <td>It will verify if the given userName and password matches with the one in the database, and establish a session to that user.   
       If there any error, an error message will be shown
      </td>
    </tr>
    <tr>
      <td>verifySignUp</td>
      <td>sign-up-validation or sign-in (in case of error)</td>
      <td>@PostMapping</td>
      <td>/sign-up/validation</td>
      <td>Model model, @RequestParam String userName, password, country, email</td>
      <td>It will try to register the user in the database (if he does not exist) and establish him a session. If the user is already in 
      the database or an error occurs, the user will be notified</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `UserService` class, which will be under the `@AutoWired` annotation.

### UserService
This `@Service` will serve as an intermediate between the `UserController` and the `UserRepository` in order to follow the `Hexagonal Arquitecture`. This service will have all the operations the `@Controller`s will need in order to access the data on the database, in other words, the querys.

This are the methods that this `@Service` will have:

<strong>Note: The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author. </strong>

<table>
  <thead>
    <th>Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>verifyUser</td>
      <td>UserEntity or null (in case the user does not exists)</td>
      <td>It will verify if exists an user with the given userName in the database</td>
      <td>String userName</td>
    </tr>
    <tr>
      <td>verifyPassword</td>
      <td>boolean</td>
      <td>It will verify if the given password matches the one in the database. This method will always be called after the verifyUser     
       one</td>
      <td>String userName, password</td>
    </tr>
    <tr>
      <td>registerUser</td>
      <td>UserEntity</td>
      <td>It will save a given user in the database. If there will be any error, and exception will be thrown</td>
      <td>UserEntity newUser</td>
    </tr>
    <tr>
      <td>recoverUser</td>
      <td>UserEntity</td>
      <td>It will recover the information of a given user, in order to establish his session</td>
      <td>String userName, password</td>
    </tr>
  </tbody>
</table>

There will be an instance of the `UserRepository` from which all the querys will be made.

### TicketController
This `@Controller` will be in charge of managing everything related to the tickets for the different concerts that are registered in the application.

This will be all the methods this `@Controller` will have:

<strong>Note: The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author. </strong>

<table>
  <thead>
    <th>Name</th>
    <th>Returning Template</th>
    <th>Mapping Type</th>
    <th>URL</th>
    <th>Parameters</th>
    <th>Description</th>
  </thead>
  <tbody>
    <tr>
      <td>showPurchaseInformation</td>
      <td>String</td>
      <td>@PostMapping</td>
      <td>/concert/{id}/purchase</td>
      <td>Model model, @PathVariable long id, @RequestParam String number, ticketType</td>
      <td>Once the ticket is chosen, it will show all the relevant information regarding the concert selected by the user, and the tickets to be purchased</td>
    </tr>
    <tr>
      <td>showPurchaseConfirmation</td>
      <td>purchase-confirmation or purchase (If something goes wrong)</td>
      <td>@PostMapping</td>
      <td>/concert/{id}/purchase/confirmation</td>
      <td>Model model, @PathVariable long id, @RequestParam String ticketType, int number, String cardHolder, cardType, cardId, expDate, cvv</td>
      <td>It will verify if the information of the credit card is valid, if it is, the purchase-confirmation template will be shown, 
       otherwise, the purchase template will be shown with an error message.</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `CardVerifyingService `, `ConcertService` and `TicketService` classes, which will be under the `@AutoWired` annotation.

### TicketService
This `@Service` will serve as an intermediate between the `TicketController` and the `TicketRepository` in order to follow the `Hexagonal Arquitecture`. This service will have all the operations the `@Controller`s will need in order to access the data on the database, in other words, the querys.

This are the methods that this `@Service` will have:

<strong>Note: The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author. </strong>

<table>
  <thead>
    <th>Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getTicket</td>
      <td>Ticket</td>
      <td>Obtain a ticket form the database</td>
      <td>long id</td>
    </tr>
    <tr>
      <td>createTicket</td>
      <td>Ticket</td>
      <td>Creates a new Ticket and save it in the database</td>
      <td>String zone, float price, Concert concert</td>
    <tr>
      <td>associateUserWithTicket</td>
      <td>void</td>
      <td>This method will create a ticket (by calling the createTicket method); and associate it with its owner (setting the ticket to that user). And vice versa, it will update the user's ticket list with the new ones. Finally, once both entities are updated, they will be saved in their respective repositories.</td>
      <td>String type, int number, long idConcert</td>
    </tr>
  </tbody>
</table>

There will be an instance of `TicketRepository` , `UserRepository` and `ConcertRepository` from which all the querys will be made. Adittionally, an `ActiveUser` instance will also be present.

### ConcertRepository
This is not a class created by me, it is one in which I collaborated by adding the 4 queries to the concert database that will be mentioned below. In addition, these queries controlled the concurrency problems that could be generated when trying to purchase a greater number of tickets than available.

<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>existConcert</td>
      <td>boolean</td>
      <td>It will verify if the concert exist or not.</td>
      <td>Concert concert</td>
    </tr>
    <tr>
      <td>availableWestStandsTickets</td>
      <td>int</td>
      <td>It will make a @Query obtaining the number of West Side Tickets required. It will control the concurrent access to the repository, thus preventing a user from trying to obtain a number of tickets greater than the one available.</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
    <tr>
      <td>availableEastStandsTickets</td>
      <td>int</td>
      <td>It will make a @Query obtaining the number of East Side Tickets required. It will control the concurrent access to the repository, thus preventing a user from trying to obtain a number of tickets greater than the one available.</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
    <tr>
      <td>availableSouthStandsTickets</td>
      <td>int</td>
      <td>It will make a @Query obtaining the number of South Side Tickets required. It will control the concurrent access to the repository, thus preventing a user from trying to obtain a number of tickets greater than the one available.</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
    <tr>
      <td>availableGeneralAdmissionTickets</td>
      <td>int</td>
      <td>It will make a @Query obtaining the number of General Admission Tickets required. It will control the concurrent access to the repository, thus preventing a user from trying to obtain a number of tickets greater than the one available.</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
  </tbody>
</table>

### ConcertService
Although this file is not included in the 5 files in which I participated the most, it is a service in which, as with the `ConcertRepository`, I participated with a method to verify if there are available entries of a specific type:

<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>verifyAvailability</td>
      <td>boolean</td>
      <td>It will check if there are tickets available of a certain type</td>
      <td>long id, int number, String type</td>
    </tr>
  </tbody>
</table>

## Arminda García Moreno

## David Rísquez Gómez
