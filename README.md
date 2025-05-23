# LiveTicket

## 👥 Participants of Group 16

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

## 🧾 Table of Contents
- [Execution Instructions](#-execution-instructions)
- [Phase 1](#-phase-1)
  - [Entities Information](#entities-information)
  - [User Privileges](#user-privileges)
  - [Image Management](#image-management)
  - [Application Functionality Overview](#application-functionality-overview)
  - [Team Members Participation](#team-members-participation)
- [Phase 2](#-phase-2)
  - [Modifications with respect to phase 1](#-modifications-with-respect-to-phase-1)
  - [News](#-news)
  - [Types of Users and Browsing Permissions](#--types-of-users-and-browsing-permissions)
  - [Errors Management](#-errors-management)
  - [API REST Documentation](#-api-rest-documentation)
  - [Team Participation](#-team-participation)
- [Phase 3](#-phase-3)
  - [Considerations](#-considerations) 
  - [Execution Instructions for the Dockerized Application](#-execution-instructions-for-the-dockerized-application)
  - [Docker Image Building Documentation](#-docker-image-building-documentation)
  - [Documentation for Deploying to Virtual Machines](#-documentation-for-deploying-to-virtual-machines)
  - [Application Deployed on Virtual Machines](#-application-deployed-on-virtual-machines)
- [License](#-license)

## 🔧 Execution Instructions
1. Download the repository and unzip it.
2. Run the application in your preferred IDE. We recommend that you use `Visual Studio Code`.
3. Once the application is running, open your prefferred browser and go to: `https:\\localhost:8443/`.
4. You can access the application as an administrator by using one of the following accounts:
   * Username: armiiin13; Password: eras1325.
   * Username: Fonssi29; Password: pollitoPio.
   * Username: davih; Password: davilico.

In addition to the steps above, you will need to meet the following requirements:

### ☕ Java 21 (JDK 21)
In case you do not have JDK installed in your computer, this is how you can get it:
* If you are a Windows user [Click Here](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.zip).
* If you use Linux as your operative system, you will need to put the following command in the terminal:
```sh
sudo apt install openjdk-21
````

### ⚙️ Maven v0.44.0
If you are using `Visual Studio Code` as your IDE, you can get it by searching the `Maven for Java` extension.

In other case, you can get it [Here](https://maven.apache.org/download.cgi).

### 💡 Spring Boot 3.4.2
The reason we strongly recommend using `Visual Studio Code` as the IDE to launch the application, is because of the Spring Boot version. In this IDE, it is possible to use version `3.4.2`, however, in other IDEs such as `SpringToolSuite` the higher version available is `3.4.1`, so it may not work as it should.

> [!WARNING]
> At this time, version `3.4.3` is available, however we do not recommend using it for this project.

If you use `Visual Studio Code`, you will need to have the `Spring Boot Extension Pack` extension.

# 🌐 Phase 1

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
      <td>@OneToMany(mappedBy="ticketUser")</td>
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
      <td>Concert</td>
      <td>1..N</td>
      <td>@OneToMany(mappedBy = "artist", cascade=CascadeType.ALL, orphanRemoval=true)</td>
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
      <td>Artist</td>
      <td>N..1</td>
      <td>@ManyToOne</td>
    </tr>
    <tr>
      <td>Ticket</td>
      <td>1..N</td>
      <td>@OneToMany(mappedBy="concert", cascade=CascadeType.ALL, orphanRemoval=true)</td>
    </tr>
  </tbody>
</table>

### Ticket
<table>
  <thead>
    <th>Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
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

![Relational Diagram](Diagrams/DB_Relational_Diagram.png)

## User Privileges
Anonymous users will be able to search for artists and concerts and visit the artist's page for information, but they will not be able to purchase tickets to a concert. They can also register or log in on the page to become registered users.

In addition to the actions that an anonymous user can take, registered users can purchase tickets and modify their profile page (and add a profile photo that the page did not request when they registered). They will be able to see their purchased tickets and, in the future, they will be able to create a list with their favorite artists.

Admin users can do all this and also create, modify and delete concerts and artists. In their profile photo they have two options: "Concert Manager" and "Artist Manager", where each link takes them to the corresponding page.

Regarding the entities that the user owns, anonymous users have nothing to own, but each registered user and administrator will own a list of tickets, which is the list of tickets that the user has purchased. 

> [!NOTE]
> In the future, these types of users will also have an artist list, which contains the user's favorite artists.

## Image Management
The user entity has only one image associated with it, this being its profile picture.

The artist entity has three images associated with it, these being its photo and two album covers (of the albums that the administrator fill out information for in the corresponding form). 

The concert entity has only one image associated with it, this being its promotional poster.

## Application Functionality Overview
The objective of this webpage is to purchase concert tickets, like some real websites like Ticketmaster. As an extra to these kind of websites, our proyect also lets the user visit the artist’s page, which contains information about him/ her and shows a selection of albums (and their links to Spotify and Apple Music) and displays a music video so you can get to know the artist and purchase tickets to their upcoming concerts.

> [!IMPORTANT] 
> If the user changes the URL for the artist’s page (changing the ID) it will work and show another page, but if he does it when modifying  concerts or artist, ir will show the error page.

### ScreenShots
- Main Page
![Main Page](Diagrams/Screenshots/main_page.png)
**Description:** this is the main page of the web. If the user is logged it will show the concerts on his continent so he can access to them more easily. On the other case, it will show all the concerts kept in the database (this will change in later versions of the web). In these two cases, then its showed a display of the new artist added (only if it has a page to show). It also has the header which lets you to log in or register (if its an anonymous user) or to visit your profile, see your list of favourite artist and see the tickets that you have purchase. This header also has a search bar that lets you to search whatever concert or artist you want. It also contains the footee with the main information of the page (as a company).

- Search Page
![Search Page](Diagrams/Screenshots/search_page.png)
**Description:** this is the page that shows the results of the search done in the header searchbar. First, it will show the artists whose names contains the string searched. Then, it will display the concerts that are by said artists and the concerts whose names contains that string. If the user is logged, prior to this display it will show the same (concerts by the artists and concerts with the string) that take place at the user's continent. This page also has the header as the main page.

- Artist Page
![Search Page](Diagrams/Screenshots/artist_page.png)
**Description:** this page will show the artist information saved at the database. If there is no artist or the artist does not have a page, it will show a message telling so. This page shows the artist's photo next to the main information paragraph. Below this, it will show the extended information paragraph with additional information of the artist. Then, it will show the artist's two albums saved on the database as a photo with their respective Spotify and Apple Music's links inside to photos of the logos of said apps. At last, it will show a display of the concerts that this artist will be playing in every continent, with the possibility to go to the select tickets page for that concert. This page also has the header and the footer as the main page.

- Select Tickets Page
![Select Tickets Page](Diagrams/Screenshots/ticket_selection.png)
**Description:** this page will only be accessible if the user is logged. It will show a predetermined map of the arena where the concert is going to take place (is the same for every concert) and next to it will be a form so the user can choose the type of ticket he wants and the number of them. It also shows the price of the tickets. This page also has the header as the main page.

- Purchase Page
![Purchase Page](Diagrams/Screenshots/purchase_page.png)
**Description:** on this page the user will have to enter his back data so he can purchase the tickets. First, the page shows the information of the tickets that the user is going to buy, giving him the option to cancel the purchase (which takes him back to a page thats shows a message telling the cancelation was succesfull). Below that is the form I named earlier, where the user has to enter its bank account data to purchase the tickets.

- Profile Page
![Profile Page](Diagrams/Screenshots/profile_page.png)
**Description:** this page will show the main information of the user, giving him the option to change it. It will also let him to add/ change a profile picture. This page also has the header as the main page.

- My Concerts' Page
![My Concerts Page](/Diagrams/Screenshots/my_concerts.png)
**Description:** this page will show the tickets the user has purchased. By hovering over each one, the ticket turns over and shows the ticket details as well as the name of the user who purchased it. This page also has the header as the main page.

- Administrator Pages
![Administrator Pages](Diagrams/Screenshots/admin_pages.png)
**Description:** these pages will show a button to add a concert or artist and all the artists/ concerts saved at the database (so the administrator can select them in order to modify them). It also includes a searchbar that lets the administrator to shorten this list to find the concert/ artist he wants to modify in an easier way.

- Add/ Modify Concerts (Administrator)
![Concert Workbench](Diagrams/Screenshots/concert_modify.png)
**Description:** this page will display a form that the administrator will fill out with the information of a brand new concert or will display a form with an existing concert data so the administrator can change it. There are some differences with each display: on the brand new concert form, the administrator should enter the number of tickets available for each type and its general price; on the modify concert form, it will show the information of how many tickets are left of each type and its type, letting the administrator only to indicate how many tickets of each type he wants to add. Also, in the modify concert form is a button to delete the concert.

- Add/ Modify Artists (Administrator)
![Artist Workbench](Diagrams/Screenshots/artist_modify.png)
**Description:** this page is similar to the concert one, with the peculiarity that there is a preview of the artist miniature that autocompletes at the same time that the administrator is entering the data.

- Log In/ Register Pages
![Log In and Register](Diagrams/Screenshots/login_register.png)
**Description:** these pages display a form so the user can log in or register. Each page lets the user to go to the other page if he wants to do the other activity. In the register form, the user has to enter his username, email, password and country but to log in, he only has to enter its username and his password.

- Message Pages
![Message Pages](Diagrams/Screenshots/message_collection.png)
**Description:** these pages are destined to show a message telling the user some information. These pages include the error page (static) that appears when an error has ocurred, the pages telling the administrator that the concert has been added/ modified/ deleted succesfully and the welcome pages when the user logs in or registers.

### Navigation Diagram
* Blue: all users
* Green: registered users and administrators
* Purple: only administrators
* Note: from any page you can reach the error page
![Navigation Diagram](/Diagrams/Navigation_Diagram.jpeg)

### Classes and Templates Diagram
![Template Diagram](/Diagrams/TemplateDiagram.jpg)

Additionally, to provide more information and better clarity of how the application works, it was decided to make some additional diagrams about the main functionalities of the application. These are the following:

### Sign In Comunication Diagram
![Sign In Comunication Diagram](/Diagrams/Sign+In.jpg)
> [!NOTE]
> Any errors that may occur during this action are not reflected in this diagram. The only error that could occur is that the username does not exist in the database, or the password does not match the one registered. If any of this happens, an error message will appear on the form itself.
### Sign Up Comunication Diagram
![Sign Up Comunication Diagram](/Diagrams/Sign+Up.jpg)
> [!NOTE]
> Any errors that may occur during this action are not reflected in this diagram. The only error that could occur is that the username does not exist in the database. If this happens, an error message will appear on the form itself.

### Ticket Purchase Activity Diagram
![Ticket Purchase Activity Diagram](/Diagrams/TicketActivityDiag.jpg)

### Add Artist Comunication Diagram
![Add Artist Comunication Diagram](/Diagrams/AddArtistCom.jpg)

### Modify Artist Comunication Diagram
![Add Concert Comunication Diagram](/Diagrams/AddConcertCom.jpg)

### Add Concert Comunication Diagram
![Modify Artist Comunication Diagram](/Diagrams/ModifyArtistCom.jpg)

### Modify Concert Comunication Diagram
![Modify Concert Comunication Diagram](/Diagrams/ModifyConcertCom.jpg)

## Team Members Participation
In this section, each of the participants in the development of the application will explain the tasks they have been responsible for, showing their most notable commits, and those files on which they worked the most.

To coordinate the team, we have used the Trello application, which allows us to create Kanban panels virtually.

The following link leads to our team dashboard:
[Link to the Trello Dashboard](https://trello.com/b/6p7qKvZK/ticketmaster-ssdd)

>[!IMPORTANT]
> Merge commits are not being considered to calculate the participation of each member on a file. This is because merge commits are commits that GitHub does when there has been a new upload while someone was working on the repository. So, looking on the merge commit it will appear that a member has touched a file, when that is not the case (also the commit will show the same code the other member uploaded on a earlier commit).

>[!NOTE]
> Each method will be explained by the corresponding author. However, the code was documented using `Javadoc` documentation for each method, providing greater clarity in understanding its purpose and operation.

## Alfonso Rodríguez Gutt

### Commit List
1. [Adding all User files (Entity, Service and Repository)](https://github.com/SSDD-2025/Grupo-16/commit/04e8fe9fb732fa6bf1aa67009ebd2a9d1cfafaee).
2. [Adding all Ticket files (Entity, Service and Repository)](https://github.com/SSDD-2025/Grupo-16/commit/db40a969aab6353c5729374c2beefb25568a1102).
3. [Addition to the TicketController of the ticket purchase method.](https://github.com/SSDD-2025/Grupo-16/commit/bcf4139c1fcbdff3650e72fffc5418e7b155c4b6).
4. [Finished the implementation of Cancel Purchase](https://github.com/SSDD-2025/Grupo-16/commit/f4b9c00c2eb97dd5ad5c5ab38b6a5081dad9e6b7).
5. [Re-design of the sign-in/sign-up page](https://github.com/SSDD-2025/Grupo-16/commit/5383e7c5c7b46133c9e9a3a6c26a8f4f0283cddc).

> [!IMPORTANT]
> Accessing commits with the links specified above may not reflect the current version of the file. This is because there were smaller commits after that, where small errors were corrected, comments were added, etc.

### Files with the most participation
1. UserController.java
2. UserService.java
3. TicketController.java
4. TicketService.java
6. ConcertRepository.java

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
12. cancel-verification.html.

Once the files in which I was a participant have been mentioned, I will explain in detail what was done in the most notable files:

### UserController
This `@Controller` will be in charge of managing everything related to user actions in the web application.

This will be all the methods this `@Controller` will have:

> [!IMPORTANT]
> The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author.

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

> [!IMPORTANT]
> The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author.

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

> [!IMPORTANT]
> The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author.

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
      <td>purchase-confirmation</td>
      <td>@PostMapping</td>
      <td>/concert/{id}/purchase/confirmation</td>
      <td>Model model, @PathVariable long id, @RequestParam String ticketType, @RequestParam int number</td>
      <td>It will show the "purchase-confirmation.html" template</td>
    </tr>
    <tr>
      <td>cancelPurchase</td>
      <td>cancel-verification</td>
      <td>@PostMapping</td>
      <td>/concert/{id}/cancel-purchase</td>
      <td>Model model, @PathVariable long id, @RequestParam String type, @RequestParam int number</td>
      <td>It will cancel the purchase.</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `ConcertService` and `TicketService` classes, which will be under the `@AutoWired` annotation.

### TicketService
This `@Service` will serve as an intermediate between the `TicketController` and the `TicketRepository` in order to follow the `Hexagonal Arquitecture`. This service will have all the operations the `@Controller`s will need in order to access the data on the database, in other words, the querys.

This are the methods that this `@Service` will have:

> [!IMPORTANT]
> The methods that I will explain below will be those carried out by me, the rest of the methods will be explained by the corresponding author.

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
    <tr>
      <td>restoreWestStandsTickets</td>
      <td>int</td>
      <td>It will make a @Query restoring the number of westStand tickets that the user has selected, when he cancel the purchase</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
    <tr>
      <td>restoreEastStandsTickets</td>
      <td>int</td>
      <td>It will make a @Query restoring the number of eastStand tickets that the user has selected, when he cancel the purchase</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
    <tr>
      <td>restoreNorthStandsTickets</td>
      <td>int</td>
      <td>It will make a @Query restoring the number of northStand tickets that the user has selected, when he cancel the purchase</td>
      <td>@Param ("id") long id, @Param ("number") int number</td>
    </tr>
    <tr>
      <td>restoreGeneralAdmissionTickets</td>
      <td>int</td>
      <td>It will make a @Query restoring the number of generalAdmission tickets that the user has selected, when he cancel the purchase</td>
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
      <td>existConcert</td>
      <td>boolean</td>
      <td>It will verify if the concert exist or not.</td>
      <td>Concert concert</td>
    </tr>
    <tr>
      <td>verifyAvailability</td>
      <td>boolean</td>
      <td>It will check if there are tickets available of a certain type</td>
      <td>long id, int number, String type</td>
    </tr>
    <tr>
      <td>restauringTickets</td>
      <td>void</td>
      <td>This method will restore the selected tickets when the user cancel de purchase</td>
      <td>long id, int number, String type</td>
    </tr>
  </tbody>
</table>

## Arminda García Moreno

### Commit List
1. [Adding and modifing concerts as an administrator.](https://github.com/SSDD-2025/Grupo-16/commit/01685b168b47b0e61289a6104313b0444fe36427)
2. [Added the ArtistController method to show the artist's page and modificated the HTML to be consistent.](https://github.com/SSDD-2025/Grupo-16/commit/5c3604a057ff7e083c25c96aeaa310d7d967a923)
3. [MainController mapping to "/" and Artist classes created.](https://github.com/SSDD-2025/Grupo-16/commit/18d390fc4e643460db3198fe4f469e2035d1a2a0)
4. [Administrator Concert Controller HTML documents.](https://github.com/SSDD-2025/Grupo-16/commit/906aa3f27c96235a4ea06122e4b4b75453e67501)
5. [Creation of ActiveUser and its implementation so the login sets the new information available.](https://github.com/SSDD-2025/Grupo-16/commit/f20226ff9ed4312e59d809437f619d71a2d47a84)

> [!IMPORTANT]
> Accessing commits with the links specified above may not reflect the current version of the file. This is because there were smaller commits after that, where small errors were corrected, comments were added, etc.

### Files with the most participation
1. ConcertController.java
2. ConcertService.java
3. ActiveUser.java
4. ArtistService.java
5. ArtistController.java

In addition to these files, I worked on other files with a lower workload, or that did not have the same level of importance as the previous ones mentioned. These are:
1. ArtistRepository.java
2. Artist.java
3. artist.html
4. MainController.java
5. main.html + display-concerts.html
6. ImageService.java
7. ImageController.java
8. ConcertRepository.java
9. purchase.html
10. concert-workbench.html

Once the files in which I was a participant have been mentioned, I will explain in detail what was done in the most notable files:
> [!NOTE]
> The following methods are the ones I created on each file, not every method that is on the class. Also, they are not all the methods I created, only the ones more important to the operation of the web page.

### ConcertController
Althought this class has not been created by me, I have implemented a lot of its method because I was in charge of the administrator concert control. This includes the mapping for adding, modifing and deleting concerts as the ones to show up the form to fill out the information or the search page to select the action the administrator wants to do.

Next, I will describe these methods specifically.

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
      <td>getAdminConcert</td>
      <td>admin-concerts</td>
      <td>@GetMapping</td>
      <td>/admin/concert</td>
      <td>Model model, @RequestParam(required = false) String search</td>
      <td>It will show a button so the administrator can go an add a concert or he/she could search or select a specific concert (already existing) to be modified</td>
    </tr>
    <tr>
      <td>formAddConcert</td>
      <td>concert-workbench</td>
      <td>@PostMapping</td>
      <td>/admin/concert/workbench</td>
      <td>Model model</td>
      <td>It will prepare the concert-workbench.html so the administrator can fill out a form to add a brand new</td>
    </tr>
    <tr>
      <td>formModifyConcert</td>
      <td>concert-workbench</td>
      <td>@PostMapping</td>
      <td>/admin/concert/{id}/modify</td>
      <td>Model model, @PathVariable long id</td>
      <td>It will prepare the concert-workbench.html so the administrator can modify the form (information) of the concert whose id is passed as a path variable</td>
    </tr>
    <tr>
      <td>postAddConcert</td>
      <td>concert-validation</td>
      <td>@PostMapping</td>
      <td>/admin/concert/register</td>
      <td>Model model, @ModelAttribute Concert newConcert, @RequestParam(required = false) MultipartFile poster, @RequestParam(required = false) Long id, @RequestParam(required = false) String newArtistName, @RequestParam Long artistId</td>
      <td>This method will be calling the proper ConcertService methods to save (id = null) or modify (id != null) the concert and will show the concert-validation.html with the proper message.</td>
    </tr>
    <tr>
      <td>postDeleteConcert</td>
      <td>concert-validation</td>
      <td>@GetMapping</td>
      <td>/admin/concert/delete</td>
      <td>Model model, @RequestParam long id</td>
      <td>This method will delete from the database the concert whose id is passed as a RequestParam and will shoe the concert-validation view with the correct message.</td>
    </tr>
  </tbody>
</table>

### ConcertService
As happens with the ConcertController, I did not create this class neither, but I have implemented some of its methods because of what my tasks where. Specifically I implemented those regarding the addition or elimination of the concerts on the database and some searches on the repository.

Next, I will describe these methods specifically.
<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getConcertDisplay</td>
      <td>List&lt;Concert&gt;</td>
      <td>This method will return the list of concerts to display on the main page. This list will change whether the user is logged (will search first for concerts on the user's continent) or anonymous (will show all the concerts on the database - it is going to be a better search in later versions)</td>
      <td>boolean userLogged</td>
    </tr>
    <tr>
      <td>saveConcert</td>
      <td>void</td>
      <td>This method will asign the concert passed as a parameter with its poster photo (if its not null the MultipartFile passed as a parameter) and the artist that sings on it. Then, this concert will be uploaded to the database using the concertRepository.</td>
      <td>Concert concert, MultipartFile poster, long artistId</td>
    </tr>
    <tr>
      <td>modifyConcert</td>
      <td>void</td>
      <td>This method will find the oldConcert (whose id is passed as a parameter), will update the tickets, the photo and the artist of the newConcert (the one passed as a parameter) depending on what has the oldConcert and what the administrator changed on the modifing form. At last, it will upload these changes onto the database using the concertRepository</td>
      <td>Concert concert, long id, MultipartFile poster, long artistId</td>
    </tr>
    <tr>
      <td>deleteConcert</td>
      <td>void</td>
      <td>This method will call the ConcertRepository to delete the concert whose id is the one passed as a parameter</td>
      <td>long id</td>
    </tr>
  </tbody>
</table>

### ArtistRepository
The ArtistRepository is an interface extension of the JpaRepositor&lt;Artist,Long&gt;that controlls queries on the artist database.

I created the queries that are used to display the artist onto the main page, which I will explain now:
<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>findTop10ByOrderByPopularityIndexDesc</td>
      <td>List&lt;Artist&gt;</td>
      <td>It returns the list of the top 10 artist with the highest popularity index</td>
      <td>None</td>
    </tr>
    <tr>
      <td>findTop10ByOrderBySessionCreatedDesc</td>
      <td>List&lt;Artist&gt;</td>
      <td>It returns the list of the top 10 artist with most recent session created date</td>
      <td>None</td>
    </tr>
    <tr>
      <td>findFirstByNameIgnoreCase</td>
      <td>Optional&lt;Artist&gt;</td>
      <td>It returns the artist with the name passed as a parameter (ignoring cases) if it exists</td>
      <td>String name</td>
    </tr>
  </tbody>
</table>

### ArtistService
The ArtistService `@Service` contains all the methods that could be used by the different `@Controller`s in order to make database querys and access in a simple an modularizated way to the artist specific data.

The following, are the methods available in this `@Service`:
<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getArtistDisplayByPopularity</td>
      <td>List&lt;Artist&gt;</td>
      <td>It returns a list to display on the main page with the top ten artist with the highest popularity rate</td>
      <td>None</td>
    </tr>
    <tr>
      <td>getArtistDisplayBySession</td>
      <td>List&lt;Artist&gt;</td>
      <td>It returns a list to display on the main page with the top ten artist recent added artist</td>
      <td>None</td>
    </tr>
    <tr>
      <td>getByNameIgnoreCase</td>
      <td>Optional&lt;Artist&gt;</td>
      <td>It returns the first artist found with the name passed as a parameter(should be the only one) ignoring cases</td>
      <td>String name</td>
    </tr>
    <tr>
      <td>artistExists</td>
      <td>boolean</td>
      <td>It returns if the artist whose name is passed as a parameter exists in the database ignoring cases</td>
      <td>String name</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `ArtistService ` and `ConcertService` classes, which will be under the annotation `@AutoWired`.

### ArtistController
This `@Controller` will be in charge of managing everything related to the artist main page. It will add all the attributes of the artist on the artist.html depending if the artists exists in the database or not.

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
      <td>getArtistPage</td>
      <td>artist</td>
      <td>@GetMapping</td>
      <td>/artist/{id}?artistName=""</td>
      <td>Model model, @PathVariable long id, @RequestParam String artistName</td>
      <td>It will add all the attributes to complete the creation of the artist html</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `ArtistService ` and `ConcertService` classes, which will be under the annotation `@AutoWired`.

### ActiveUser
The class ActiveUser is a `@Component` annotated with `@SessionScope`, which means that is an object related to the session thats being used by the webpage.

__Attributes__: a boolean variable called logged which is true if there is a user logged on the page and a long variable called userId, which contains the id of the user that is logged. 

When the user is anonymous, __logged__ will be false, but activeUser will be already created by Spring, so it should only be accessed if __logged__ has the value true (on the contrary, it will have a junk value).

__Methods__: the methods for this class are
<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>init</td>
      <td>void</td>
      <td>It inicialize the session (logged=false)</td>
      <td>void</td>
    </tr>
    <tr>
      <td>setNewActiveUser</td>
      <td>void</td>
      <td>Sets the userId to the id of the user passed as a parameter</td>
      <td>void</td>
    </tr>
    <tr>
      <td>isUserLogged</td>
      <td>boolean</td>
      <td>Returns whether the user is logged or not</td>
      <td>void</td>
    </tr>
  </tbody>
</table>

The attribute __userId__ also has its getter and setter defined.

> [!NOTE]
> Other methods or classes less important that I have been a part of are the implementation of the querys that update the database adding/ substracting available tickets (coauthor with my colleague Alfonso Rodríguez) and the creation and implementation of the InitService, where I added 3 initial artists and 10 initial concerts to the database.

## David Rísquez Gómez

### Commit List
1. [Artists administrator panel and workbench.](https://github.com/SSDD-2025/Grupo-16/commit/e071891ad9de1cd7b132d19f30424b6c32c49015)
2. [Profile display and modification enabled, with controllers and html structure.](https://github.com/SSDD-2025/Grupo-16/commit/7f2d5e957fdb856cbf015baefcd3528ae4614d0e)
3. [Added artist deletion and corrected encapsulation in some methods.](https://github.com/SSDD-2025/Grupo-16/commit/0e65018d35ea93477e0c4e18999a70aa2ac592cc)
4. [Restoration of the header to make it responsive, structurally better and more aesthetically pleasing.](https://github.com/SSDD-2025/Grupo-16/commit/ef0c41f3e6c78ee2b2ef1c36ca683605073f19a7)
5. [Added HTML structure, style and linked controllers for the concert ticket-selling page.](https://github.com/SSDD-2025/Grupo-16/commit/45c84af1aaf779c2f814fbccf9476996cba5efff)

> [!IMPORTANT]
> The commits accessed through the links provided may not reflect the current version of the file, as there have been subsequent smaller commits that address minor errors, add comments, and make other adjustments.

### Files with the most participation:
1. ArtistController.java
2. ArtistService.java
3. ConcertService.java
4. UserController.java
5. UserService.java

In addition to these files, I worked on other files where my work has been notable, but which intrinsic importance is not as important or determinant as the previous ones. These files are:
1. header.html
2. Concert.java
3. search-page.html
4. artist-workbench.html
5. TicketController.java
6. concert.html
7. MainController.java
8. my-profile.html
9. ArtistRepository.java
10. common-styles.css
11. ConcertRepository.java
12. ticket-selling.html
13. display-tickets.html

Furthermore, the implementation, style and structure within the administrator branch responsible for managing artists has served as a reference and model for the development of similar tasks. Moreover, the aesthetics, methodology, and concepts such as the 'workbench for creation, modification, and deletion of entities' are my own design and initiative.

Additionally, the search structures (search bars) on the pages within the header and the admin search sections have been developed by me.

After mentioning the files in which I contributed, I will provide a detailed explanation of the work done in the most notable files:

> [!NOTE]
> The methods listed below are the ones I developed in each file, focusing on those crucial for the functionality of the webpage, rather than including every method in the class.

### ArtistController
Even though the creation of this class was not authored by me, my work and contributions were considerable significance. I have developed nearly the entire class, as I was responsible for the Artist's administrator branch of work. Actions such as `Artist` modifying, adding or deleting artists are the primary functions carried out in this controller. Specifically, the URL mappings for the aforementioned actions are managed by methods contained within this class, which were created by me.

Below is a detailed description of the methods I developed inside this class:

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
      <td>artistsAdminPage</td>
      <td>admin-artists</td>
      <td>@GetMapping</td>
      <td>/admin/artist</td>
      <td>
        <ul>
          <li>Model model</li>
          <li>@RequestParam(required = false) String search</li>
        </ul>
      </td>
      <td>It is shown a button where the administrator can add a new artist and a section that, complemented with a search bar, can be used in order to search for existing artists so that is easy for the administrator to find an existing artist for modifying purposes.</td>
    </tr>
    <tr>
      <td>prepareArtistWorkbench</td>
      <td>artist-workbench</td>
      <td>@GetMapping</td>
      <td>/admin/artist/workbench</td>
      <td>
        <ul>
          <li>Model model</li>
        </ul>
      </td>
      <td>It prepares the artist workbench in order to be used by administrators to add a new artist (or modify a previous introduction that was not correct - by RedirectAttributes)</td>
    </tr>
    <tr>
      <td>modifyExistingArtist</td>
      <td>artist-workbench <br> error</td>
      <td>@PostMapping</td>
      <td>/admin/artist/{id}/modify</td>
      <td>
        <ul>
          <li>Model model</li>
          <li>@PathVariable long id</li>
        </ul>
      </td>
      <td>It takes the information from an existing artist and projects the date into the artist-workbench html so that the user has an easy way to see all the information displayed and, if necessary, modify it. Also, it checks if it exists and returning error page in case it does not.</td>
    </tr>
    <tr>
      <td>registerArtist</td>
      <td>redirect:/admin/artist/workbench <br> redirect:/admin/artist <br> redirect:/error</td>
      <td>@PostMapping</td>
      <td>/register-new-artist</td>
      <td>
        <ul>
          <li>Model model</li>
          <li>@ModelAttribute Artist artist</li>
          <li>@RequestParam(required = false) MultipartFile mainPhoto</li>
          <li>@RequestParam(required = false) MultipartFile bestPhoto</li>
          <li>@RequestParam(required = false) MultipartFile latestPhoto</li>
          <li>@RequestParam(required = false) Long id</li>
          <li>RedirectAttributes redirectAttributes</li>
        </ul>
      </td>
      <td>It takes the information from the artist-workbench form (in case of new artist registration or previous artist modification) and handles the saving of the artist in the database. Afterwards, depending on the result of the saving, complete error, partial error or administration page redirections occur.</td>
    </tr>
    <tr>
      <td>deleteExistingArtist</td>
      <td>redirect:/admin/artist <br> error</td>
      <td>@PostMapping</td>
      <td>/admin/artist/{id}/delete</td>
      <td>
        <ul>
          <li>Model model</li>
          <li>@PathVariable long id</li>
        </ul>
      </td>
      <td>It attempts the deletion of an artist, made from the artist-workbench and guided by the artist id. If the transaction happens correctly, the administrator is redirected to /admin/artist. Otherwise, the error html is displayed.</td>
    </tr>
  </tbody>
</table>

### ArtistService
Continuing with my work on the development of the artist administration branch, my involvement in the `ArtistService` is crucial. This is because it is the service that supports the `ArtistController`, enabling functionalities such as searching, adding or removing artists, and performing real-time verifications to maintain a smooth flow of information between the database and the controller.

Below is a detailed description of the methods I developed inside this class:

<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getArtist</td>
      <td>Artist</td>
      <td>This method searhces for an artist with an specific id. For that, a query is launched in the repository. Afterwards, it is checked if the query was successful, returning null in the negative case.</td>
      <td>
        <ul>
          <li>long id</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>getSearchBy</td>
      <td>List&lt;Artist&gt;</td>
      <td>This method is used when general searching is made. Its use is to find any artist whose name matches with the input search String. For that to happen, the artistRepository is used.</td>
      <td>
        <ul>
          <li>String search</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>registerNewArtist</td>
      <td>void</td>
      <td>Method that handles the artist registration. It makes use of the ImageService to set every image for the artist and stablishes the Artist's iternal values, such as hasPage and sessionCreated. Finally, it uses the ArtistRepository to save the artist.</td>
      <td>
        <ul>
          <li>Artist artist</li>
          <li>MultipartFile mainPhoto</li>
          <li>MultipartFile bestPhoto</li>
          <li>MultipartFile latestPhoto</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>getEveryArtist</td>
      <td>List&lt;Artist&gt;</td>
      <td>Method that searches for each and every artist existing in the database.</td>
      <td>
        No parameters
      </td>
    </tr>
    <tr>
      <td>modifyArtistWithId</td>
      <td>boolean</td>
      <td>Method that handles the artist modification. It checks which are the new attributes to be changed and takes the remaining ones from the instance of the artist in the database. After changing the new attributes and rebuilding the artist into it's new shape, it is saved and true is returned. In case no artist with the specified id existed or any error occured, it is returned false.</td>
      <td>
        <ul>
          <li>Artist artist</li>
          <li>long id</li>
          <li>MultipartFile mainPhoto</li>
          <li>MultipartFile bestPhoto</li>
          <li>MultipartFile latestPhoto</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>deleteArtistWithId</td>
      <td>boolean</td>
      <td>Method that provided with an id, handles the deletion of an artist by id. For that, the ArtistRepository is used and it is checked if the deletion has been successful or not. Trying to delete a non-existant artist is also considered an unsuccessful situation.</td>
      <td>
        <ul>
          <li>long id</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>checkIfExistsByName</td>
      <td>boolean</td>
      <td>Method that checks if an specific artist name is already added to the database so that unique names are mantained. For that, ArtistRepository is used.</td>
      <td>
        <ul>
          <li>String name</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>

### ConcertService
In line with one of my main tasks as a developer of the search engines, page, and structure, I contributed to the `ConcertController` by creating useful methods to perform searches based on various criteria. Additionally, in the task of ticket removal by a user, methods were developed to return tickets to their concerts so that they can be sold to others.

Below are described the most significant methods in this regard:

<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getsearchBy</td>
      <td>List&lt;Concert&gt;</td>
      <td>This method searhces for concerts which name or which artist's name matches with the search String introduced in the main searchbar. For that, two querys are made with ConcertRepository.</td>
      <td>
        <ul>
          <li>String search</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>findConcertById</td>
      <td>Concert</td>
      <td>This method finds an specific concert by its ID, making use of the ConcertRepository. The situation where no concert matches with that ID is also handled, returning null.</td>
      <td>
        <ul>
          <li>long id</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>getConcertsNearUser</td>
      <td>List&lt;Concert&gt;</td>
      <td>Method that searches by country depending on the user login state. When the user is logged, the UserService is used to get its country and use it as a searching attribute. If the user is not logged, null is returned, because no concerts match with its inexistant country.</td>
      <td>
        <ul>
          <li>boolean userLogged</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>returnTicket</td>
      <td>boolean</td>
      <td>Method that restores one ticket availability in a given zone of an specific concert, consequence of the deletion (devolution) of a ticket by an user. If the restoration is completed with success, true is returned. In any other case, false is returned.</td>
      <td>
        <ul>
          <li>Concert concert</li>
          <li>String zone</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>

### UserController
Another area of my work involved customizing user values, allowing users to modify certain attributes, change their profile picture, and view all their information in a simple and intuitive manner, with a straightforward menu. Thus, data management and modifications are handled in `UserController`. I will now detail my work within it:"

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
      <td>accessToProfile</td>
      <td>my-profile <br> redirect:/sign-in</td>
      <td>@GetMapping</td>
      <td>/profile</td>
      <td>
        <ul>
          <li>Model model</li>
          <li>@RequestParam(required=false) boolean showPersonalInfo</li>
          <li>@RequestParam(required=false) boolean showMyArtists</li>
          <li>@RequestParam(required=false) boolean showMyConcerts</li>
        </ul>
      </td>
      <td>This method is the principal handler for profile pages. The way personal pages were designed were using three variables that can be mutual-exclusively true. Each of them trigger the display of a different part of the user pages: personal information, my concerts (ticket visualization) and my artists. If the user not logged, it is redirected to the sign-in page. As well, through this method, administrator access is temporarily managed and granted.</td>
    </tr>
    <tr>
      <td>changeUserSettings</td>
      <td>redirect:/profile?showPersonalInfo=true <br> redirect:/error</td>
      <td>@PostMapping</td>
      <td>/profile/changeSettings</td>
      <td>
        <ul>
          <li>Model model</li>
          <li>@RequestParam long id</li>
          <li>@RequestParam(required = false)  String country</li>
          <li>@RequestParam(required = false) MultipartFile newPhoto</li>
        </ul>
      </td>
      <td>Stablish the changes made by the user over it's personal attributes and redirect the user back to their personal page where the changes can be seen applied. For that, use of UserService is used. As well, if any error occurs, the user is redirected to the error page.</td>
    </tr>
    <tr>
      <td>deleteUserProfile</td>
      <td>redirect:/ <br> redirect:/error</td>
      <td>@PostMapping</td>
      <td>/profile/delete-profile</td>
      <td>
        <ul>
          <li>@RequestParam long id</li>
        </ul>
      </td>
      <td>Method that handles the situation of trying to delete a user. Cases when the ID does not correspond to any user are also managed, redirecting the user to an error page. As well, if the deletion has been achievedm the user is redirected to the main page.</td>
    </tr>
  </tbody>
</table>

### UserService
Continuing with my work related to the modification, display, and deletion of aspects concerning the user and their tickets, my work in the `UserService` is focused on providing support for these matters to the `UserController`.

Below are described the most significant methods in this regard:

<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getActiveUser</td>
      <td>UserEntity</td>
      <td>Gets the actual active user with help of the SessionScope component ActiveUser. For that, a query is launched into the database. The method checks id there exists such user and in the negative case, null is returned.</td>
      <td>
        No parameters
      </td>
    </tr>
    <tr>
      <td>saveUserWithId</td>
      <td>boolean</td>
      <td>Saves in the database the user whose id is passed as a parameter, changing their country and profile photo in case they are not null (meaning the user wanted to change them). It returns true if the transaction occured with no errors and false in any other case.</td>
      <td>
        <ul>
          <li>long id</li>
          <li>String country</li>
          <li>MultipartFile newPhoto</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>removeExistingUserWithId</td>
      <td>boolean</td>
      <td>Method that, provided with an ID, handles the deletion of a user with the specified ID. For that, it is checked if the deletion as been successful or not, searching if an user with the given ID exists after the deletion. Tryinn to delete a non-existant user is also considered an unsuccessful situation.</td>
      <td>
        <ul>
          <li>long id</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>

>[!IMPORTANT]
>We have the guarantee that ID's are no repeated even when deletions are made because we use `@GeneratedValue(strategy = GenerationType.AUTO)` over every ID. This strategy does not recycle IDs. If the strategy changes, then deletion methods should also be changed.

To conclude this brief section, it is important to mention that I contributed to the default database by adding three artists and a total of nine concerts, including past events to preserve the historical record.

# 🔐 Phase 2

## ✨ Modifications with respect to phase 1
As a consequence of adding new functions to the application and fulfill the objectives stated in the rubric, some files have lost their importance/ function. So, to follow the YAGNI (You Aren't Gonna Need It) principle and improve the code quality, these files have been deleted (others have had some changes made).

The following section describes the situations or reasons why a file stops being used:
1. The `CardVerifyingService.java` has been eliminated because we decided not to verify the credit card entered as it got complicated (in real life the webs connect to an external payment gateway to secure the payment).
2. Adding the Spring Security part of the phase, makes Spring controlls the login and registration on the web. This makes the `validation.html` unnecessary, because this validation we did when this HTML showed. now it is done by Spring itself.
3. Adding the web pagination makes the displays (concert and artist) unnecessary, because this display is now controlled by the javascript files. This is why we have deleted the `display-concerts.html`.
4. As a consecuence of point 3, `main.html`, `admin-concerts.html` and `artist.html` got modified eliminating the concert-display inyectable and adding a div element to support all the concerts added with the javascript script and all the hidden inputs with the variables necessary to execute the script.
5. The `error.html` got change to be able to show more than one error (it only showed the 404). Depending on what the error is it will show the correct error code.

### Entities modification
In this phase, certain modifications have been introduced with respect to the previously defined entities. Firstly, the existence of `UserEntity` roles (which are persisted in the database) is represented in the relational diagram by means of a `UserRoles` entity, given that each user can hold various roles. Secondly, a modification has been implemented in the `Artist` entity, removing the images corresponding to the albums, by virtue of the fact that the visualization of albums is now performed using Spotify iframes, the documentation for which can be found at [this link](https://developer.spotify.com/documentation/embeds/tutorials/using-the-iframe-api).

### Diagrams modification
These modifications, along with their collateral effects having been implemented, have significantly simplified and optimized the REST and web image handling of the `Artist` entity, with the artist's profile photo now being optional. With all these alterations incorporated, the resulting relational diagram is as follows:

#### 📚 Relational Diagram
![Relational Diagram](Diagrams/DB_Relational_Diagram_2.png)

#### 📚 Navigation Diagram
Regarding the implementation of Spring Security, now we do not control the login or logout part of the web code. Because of this, our intermediate pages, like the Welcome page, are not being used. So, the new navigation diagram is:
* Blue: all users
* Green: registered users and administrators
* Purple: only administrators
* Note: from any page you can reach the error page
![Navigation Diagram](/Diagrams/Navigation_Diagram_Mod.jpeg)

>[!NOTE]
> Keep in mind that the new error page is dynamic, meaning it changes depending on the error that occurred.

>[!NOTE]
> Keep in mind that the login page is actually reachable from any page that requires authentication, if the user is anonymous.


## 🚀 News
Continuing with the development of this web application, the second phase focused primarily on enhancing the application's security and implementing the core back-end operations.

The most notable change was the transition from HTTP to HTTPS, which added the necessary security layer that was previously missing from the project. As a result of this change, it became essential to define the privacy level of each URL—that is, to specify which type of user (ADMIN or USER) has access to certain pages, thereby enforcing proper access control based on user roles.

In addition to the security improvements, a refactoring was carried out in the `@Service` and `@Controller` layers to prevent direct interaction with domain entities. Instead, `Data Transfer Objects (DTOs)` were introduced to promote a cleaner and more decoupled architecture.

Finally, a `REST API` was developed to mirror the operations previously available through the web controllers for each entity. This allows all standard operations (GET, POST, PUT, DELETE) to now be performed through REST controllers as well, enabling greater flexibility and compatibility with external clients. These RESTful operations were tested and executed using `Postman`, which served as the main tool for interacting with and validating the API.

To better understand the additions made in this phase, a `class diagram` is presented below, which clearly illustrates the changes that have been implemented.

### 📚 Class Diagram
>[!NOTE]
> For clarity and readability, the class diagram below will only show the relationships between the new classes implemented during this phase. Classes not included in the diagram have not had any changes to their relationships.

![Class Diagram](Diagrams/DigClasesREST.jpg)

Additionally, since this type of project can sometimes be challenging to visualize in terms of its architecture, a `project structure diagram` is also provided. This diagram offers a clear overview of how the application is organized and how its components are distributed.

### 🏗️ Project Structure Diagram
![Project Structure Diagram](Diagrams/ProjectStructureDiagram.png)
>[!NOTE]
> If you would like to better view and interact with the diagram, you can do so by following this [link](https://gitdiagram.com/SSDD-2025/Grupo-16).

## 👥 🔒 Types of Users and Browsing Permissions
As mentioned above, one of the most notable changes in this phase is the addition of security to the web application. In this section, we'll discuss and explain the different roles that a user can have, as well as the privacy settings for each URL, and therefore, what type of user can access each one.

### 👥 Types of Users
- **🔑 ADMIN:** This user has full access to all URLs within the application. Additionally, the admin is the only user authorized to perform the following operations:
  - Create, update, and delete artists.
  - Create, update, and delete concerts.

- **🧑‍💻 USER:** This is the standard user role for anyone registered in the database (i.e., with an account). A user can perform the essential operations of the application, including:
  - Purchasing tickets.
  - Updating their own profile.
  - Viewing information about artists and concerts.

- **🕵️‍♂️ GUEST/ANONYMOUS:** This refers to users who are not registered in the database (i.e., do not have an account). As a result, their access is very limited. To perform any essential operations, such as buying tickets or personalizing the experience, an account is required.

### 🔒 Browsing Permissions
<table>
  <thead>
    <th>Page</th>
    <th>Privacy</th>
    <th>User(s) allowed</th>
  </thead>
  <tbody>
    <tr>
      <td>Sign-in/Sign-up form</td>
      <td>Public</td>
      <td>All</td>
    </tr>
    <tr>
      <td>Menu</td>
      <td>Public</td>
      <td>All</td>
    </tr>
    <tr>
      <td>Profile</td>
      <td>Private</td>
      <td>ADMIN, USER</td>
    </tr>
    <tr>
      <td>Admin Concerts</td>
      <td>Private</td>
      <td>ADMIN</td>
    </tr>
    <tr>
      <td>Admin Artists</td>
      <td>Private</td>
      <td>ADMIN</td>
    </tr>
    <tr>
      <td>My Concerts</td>
      <td>Private</td>
      <td>ADMIN, USER</td>
    </tr>
    <tr>
      <td>Artist Info</td>
      <td>Public</td>
      <td>All</td>
    </tr>
    <tr>
      <td>Concert Info</td>
      <td>Public</td>
      <td>All</td>
    </tr>
    <tr>
      <td>Select Ticket(s)</td>
      <td>Private</td>
      <td>ADMIN, USER</td>
    </tr>
    <tr>
      <td>Purchase Ticket(s)</td>
      <td>Private</td>
      <td>ADMIN, USER</td>
    </tr>
    <tr>
      <td>Error</td>
      <td>Public</td>
      <td>All</td>
    </tr>
  </tbody>
</table>

>[!IMPORTANT]
>This privacy setting also applies to REST operations. In fact, attempting to perform an operation without authentication will return the error HTML instead of the corresponding JSON. To perform this authentication, a `POST` operation was performed with the URL `/api/auth/login`.

## 🚨 Errors Management
To be able to control what happens at the backend when sending petitions, we have created some exceptions the application throws when something has gone wrong or when we have to send a message explaining the situation to the user at the backend. All this exceptions are created extending the `RuntimeException` already existing in the Java API.

In general, we have created exceptions for the four entities the application has regarding the NotFoundException. Artist and User have another exception associated with them, these being because they not only identified with the id (names should be unique), which is called `AlreadyExistsException`. User has also another exception associated, this being `TicketListEmptyException` which is an alert to the user that they do not have tickets (this is to not show an empty response body with the information of an empty page).

In order to capture these thrown exceptions and convert them into alerts in the backend (response body of the Postman petitions), we have created a class called `GlobalExceptionHandler.java` which captures each exception created and returns the correct ResponseEntity build with the information of the http error code and description.

At the frontend, we have modified the `error.html` to dynamically capture the error and show its code with a descriptive message. We have only implemented this to show the following basic errors:
<table>
  <thead>
    <th>Error Code</th>
    <th>Error</th>
    <th>Description</th>
  </thead>
  <tbody>
    <tr>
      <td>400</td>
      <td>Bad Request</td>
      <td>Server cannot process the petition because it is incomplete or with incorrect data</td>
    </tr>
    <tr>
      <td>403</td>
      <td>Forbidden</td>
      <td>Server cannot authorize the petition due to lack of permits, access restrictions or server configuration</td>
    </tr>
    <tr>
      <td>404</td>
      <td>Not Found</td>
      <td>Server cannot localize the web page or resource ask by the user</td>
    </tr>
    <tr>
      <td>500</td>
      <td>Internal Server Error</td>
      <td>Server found a problem or unexpected error while processing the petition</td>
    </tr>
  </tbody>
</table>

These error codes are capture with the `CustomErrorController`, which we have created to do this specific feature. This `@Controller` implements the `ErrorController` Spring's class, so this is the controller Spring calls when an error occurs (mapping the "/error"). Inside this mapping controller method, we capture the request and obtain its status code (this being an attribute of the request). Once with this status, we can catalog it as one of the previous errors or we show an "unexpected error message" with that error code.

## 📖 API REST Documentation
The API documentation for this project was generated using Spring Doc. You can access it by following [this link](https://github.com/SSDD-2025/Grupo-16/tree/main/entrega1/api-docs).
>[!NOTE]
> The link above redirects to a folder containing the documentation in both `.html` and `.yaml` formats

## 🤝 Team Participation
In this section, each of the participants in the development of the application will explain the tasks they have been responsible for, showing their most notable commits, and those files on which they worked the most.

To coordinate the team, we have used the Trello application, which allows us to create Kanban panels virtually.

The following link leads to our team dashboard:
[Link to the Trello Dashboard](https://trello.com/b/6p7qKvZK/ticketmaster-ssdd)

## Alfonso Rodríguez Gutt
### ✅ Commit List

1. [Add the UserRestController](https://github.com/SSDD-2025/Grupo-16/commit/3f7edb9796ca4b5dd0cef52028c1e9cb005ed32e)  

2. [Refactorize the UserService with the UserDTOs](https://github.com/SSDD-2025/Grupo-16/commit/b910ad923e50ab73be513e48522a1aa6151f13cf)  

3. [Add POST and DELETE methods in TicketRestController](https://github.com/SSDD-2025/Grupo-16/commit/bb05532870049a58a629f3ca5901032a96dd8f67)    

4. [Add the filterChain for the API REST](https://github.com/SSDD-2025/Grupo-16/commit/4e471de5cf7abf75d1c7cfa7721fe88519f793da)   

5. [Add Error Handler](https://github.com/SSDD-2025/Grupo-16/commit/b4582be28dc6eb9c5d93a6a9503fb86212a1139b)

> [!IMPORTANT]
> Accessing commits with the links specified above may not reflect the current version of the file. This is because there were smaller commits after that, where small errors were corrected, comments were added, etc.

### 📂 Files with the most participation
1. `UserRestController.java`
2. `TicketRestController.java`
3. `WebSecurityConfig.java`
4. `CustomErrorController.java`
5. `UserService.java`

## Arminda García Moreno
### ✅ Commit List

1. [Added HTTPS protocol and main security configurations methods](https://github.com/SSDD-2025/Grupo-16/commit/1746459b82637769c559b02c5ec7a46da87ec74c)  

2. [Refactoring the ConcertService and ConcertController using DTOs](https://github.com/SSDD-2025/Grupo-16/commit/2b5ce34d8ac19120f802ee2b6ff213bb24b3e328)  

3. [ConcertRestController and Postman requests](https://github.com/SSDD-2025/Grupo-16/commit/be729b7984d498019772f6ff248b2b281c664a9a)    

4. [Concert Web Pagination](https://github.com/SSDD-2025/Grupo-16/commit/d480ad4e2c2908d14191a9e103aafb56ab0c3855)   

5. [Concert Rest Pagination (and changes regarding this implementation)](https://github.com/SSDD-2025/Grupo-16/commit/dacd63dbf39572a1e823f6fb5fcc28093a78a5f6)

> [!IMPORTANT]
> Accessing commits with the links specified above may not reflect the current version of the file. This is because there were smaller commits after that, where small errors were corrected, comments were added, etc.
### 📂 Files with the most participation
1. `concertPagination.js`
2. `ConcertRestController.java`
3. `ConcertService.java`
4. `ArtistMapper.java`
5. `TicketRestController.java`
   
## David Rísquez Gómez
### ✅ Commit List
1. [ArtistService refactor and securization (see commit comment).](https://github.com/SSDD-2025/Grupo-16/commit/12365403a257809ee6d023a49adb3df7edeac424)  

2. [Artist REST photo and Spotify iFrame API implementation (and correcting its side effects).](https://github.com/SSDD-2025/Grupo-16/commit/1f28b560d7b323798ad70a05fd72afcf0810edd0) 

3. [TicketService refactor and securization.](https://github.com/SSDD-2025/Grupo-16/commit/29040e7f34115ff9650737e12ff8112d6e2c4174)

4. [Artist REST and Web pagination.](https://github.com/SSDD-2025/Grupo-16/commit/bb91b54131d1d4c2791bd5e28b590d2bbfc44deb)     

5. [Migrate Web Login and Register to Spring Security.](https://github.com/SSDD-2025/Grupo-16/commit/b7d3b8f0f41ac54eded7abb16bde98f1bbf3bfc0)

> [!NOTE]
> Some of the preceding commits are partial commits related to a specific feature. Furthermore, other commits, such as the addition of Postman queries or documentation of methods, have not been incorporated due to their lower perceived importance compared to application functionalities.

> [!IMPORTANT]
> The file version accessible through the previously specified commit links may differ from the current status. This is due to incremental commits made thereafter, focusing on error rectifications, documentative comments integration, and related enhancements.
### 📂 Files with the most participation
1. `ArtistService.java`
2. `ArtistRestController.java`
3. `artistPagination.js`
4. `TicketService.java`
5. `SpotifyUpdate.js`

> [!NOTE]
> The documents highlighted exhibit participation that is either near-comprehensive or substantial. It should be acknowledged that significant contributions may also be present, albeit distributed, within other documents like UserService or ArtistController.

# 🐳 Phase 3

## 📌 Considerations
To deploy the application with all of its images, they were uploaded to [Postimage](https://postimages.org/es/), an online image hosting service. As a result, all links referencing the images have been updated to point to their corresponding URLs on this platform. For example: https://i.postimg.cc/vDyFw0SM/MPT-poster.jpg

The same applies to the page icons.

>[!NOTE]
> Please note that images may be displayed at a lower quality than in previous phases due to this hosting service.

### Postman Collection
Due to the need to access the web application remotely, a new URL specifying the target IP address is required. To facilitate this, two environments were created giving the variable `localURL` different values, depending if it is used in the virtual machines or not:
<table>
  <thead>
    <th>Environment</th>
    <th>Value</th>
  </thead>
  <tbody>
    <tr>
      <td>LiveTicket Local</td>
      <td>https://localhost:8443</td>
    </tr>
    <tr>
      <td>LiveTicket Remote</td>
      <td>https://193.147.60.56:8443</td>
    </tr>
  </tbody>
</table>

>[!IMPORTANT]
> Please note that these environments are not included when exporting a Postman collection. Therefore, to use them, you will need to create them manually in your own Postman.

## 📝 Execution Instructions for the Dockerized Application
The only requirement to execute the Dockerized Application is to have `docker compose` installed and operative in the host where the app is wanted to be executed. Normally,`docker desktop` (Windows and Mac) includes `docker compose` by default. Also, `docker engine` (Linux) may require of an installation to use docker compose.

Anyways, to find help with the installation of `docker engine`, `docker desktop` or `docker compose` visit the [official docker documentation](https://docs.docker.com/desktop/).

>[!IMPORTANT]
> Bare in mind that our public images in DockerHub were developed with Linux. The following explanations work correctly in Linux, but some as the point 3 may cause errors in Windows.

### 1) Production docker-compose (`docker-compose.prod.yml`).
Considerations for this execution method:
<ul>
  <li>A public docker image of the app is used (liveticket/liveticket:1.0.0).</li>
  <li>No Dockerfile is used.</li>
  <li>No login is required (for execution).</li>
</ul>

Once the `docker-compose.prod.yml` is fully configured, we situate the terminal in `/Grupo-16/docker`. There we will execute:

````sh
docker compose -f docker-compose.prod.yml up
````
>[!IMPORTANT]
> Note that this command only works if it is executed inside `/Grupo16/docker`. If you want to execute it from another directory, adjust the path.

After that, the containers will be running in foreground until they are stopped (`Ctrl+C` or `^C`).

In order to mantain the process in background so that the bash is available for more commands, just include the argument `-d` in the previous command.

After the execution, the next elements are created:
<ul>
  <li>Docker Network created automatically by the Docker Compose to link the containers.</li>
  <li>Docker Volume to persist the data saved in the database.</li>
  <li>Docker Container of the Web App.</li>
  <li>Docker Container of the Database.</li>
</ul>

Observe that the initiation of the Database container occurs in first place. It is not until this initiation occurs that the Web App container begins to activate. This is because the `docker-compose.prod.yml` checks the health state of the containers on which others depend and does not run them until `service_healthy` is returned.

If all of the previous steps took place correctly and the initializing execution finished, you will have access to the webpage in `https://localhost:8443` (at port 8443 of the host that executes the docker-compose).

### 2) Local docker-compose (`docker-compose.local.yml`).

Considerations for this execution method:
<ul>
  <li>No public docker image is needed to execute the app with this method.</li>
  <li>A functional Dockerfile is used.</li>
  <li>A local volume is used to save the database.</li>
  <li>No login is required (for execution).</li>
</ul>

Once the `docker-compose.local.yml` is fully configured, we situate the terminal in `/Grupo-16/entrega1`. There we will execute:

````sh
docker compose -f ../docker/docker-compose.local.yml up
````
>[!IMPORTANT]
> This command only works if it is executed inside `/Grupo16/entrega1`, since the docker-compose was explicitly configured fot that. If you want to execute it from another directory, adjust the path.

After that, the containers will be running in foreground until they are stopped (`Ctrl+C` or `^C`).

In order to mantain the process in background so that the bash is available for more commands, just include the argument `-d` in the previous command.

In the execution, the construction of a local docker image takes place and it is connected to the database container by an automatically created Docker Network. As well, a directory named `mysql` will be created at the same level than `entrega1` or `docker`. Here, the database will be saved. 

Also, this method might use a previously built image. In order to force docker compose to build the image from scratch (f.e. we change something in a HTML), you might just add `--build` at the end of the previous command:

````sh
docker compose -f ../docker/docker-compose.local.yml up --build
````

Observe that the initiation of the Database container occurs in first place (directory creation and general structuration). It is not until this initiation occurs that the Web App container begins to activate. This is because the `docker-compose.local.yml` checks the health state of the containers on which others depend and does not run them until `service_healthy` is returned.

If all of the previous steps took place correctly and the initializing execution finished, you will have access to the webpage in `https://localhost:8443` (at port 8443 of the host that executes the docker-compose). 

### 3) App execution using the public OCI Artifact.
>[!IMPORTANT]
> In Windows problems may occur with this method as far as ./env is concerned. Please, consider using WSL.

Considerations for this execution method:
<ul>
  <li>This method can be executed from any host since it is based in the usage of public docker-compose files in DockerHub.</li>
  <li>It makes use of a public docker-compose OCI Artifact (liveticket/liveticket-compose:1.0.0)</li>
  <li>There are no local files or local specific directories needed for the execution.</li>
  <li>No login is required (for execution).</li>
</ul>

With this method, the configuration file is not local, it needs to be downloaded from an OCI register (DockerHub). In order to run the app like this, the next command needs to be executed:
````sh
docker compose -f oci://docker.io/liveticket/liveticket-compose:1.0.0 up
````
Now, the OCI artifact corresponding to the `docker-file.yml` will be locally downloaded and the initiation of the different services (volumes, containers and networks) will happen similarly as in the first method.

Observe that the initiation of the Database container occurs in first place. It is not until this initiation occurs that the Web App container begins to activate. This is because the public docker compose checks the health state of the containers on which others depend and does not run them until `service_healthy` is returned.

For each of the needed images, Docker will check if the image is downloaded in the host so that is available to use or will pull them from their repository (in this case, from DockerHub).

After, you will have access to the webpage in `https://localhost:8443` (at port 8443 of the host that executes the artifact).

>[!NOTE]
> In order to delete or finish the execution in any of the exposed methods, just execute `docker compose -f path_to_docker-compose down -v` (without `-v` if you want to persist the volumes).

## 🔨 Docker Image Building Documentation
In order to dockerize the spring application (only the web part, no database included) it is needed to have the `docker engine` installed in your local machine. In case you do not have it, you can download it by accessing the official [docker documentation](https://docs.docker.com/desktop/).

Once your `docker engine` is up and running, you will be able to build the `docker image` based on the `Dockerfile` located in the [docker folder](https://github.com/SSDD-2025/Grupo-16/blob/main/docker/Dockerfile), by executing the next command:
````sh
docker build -f docker/Dockerfile -t liveticket/liveticket:1.0.0 ./entrega1
````

>[!IMPORTANT]
> Note that this command only works if run from `Grupo16/`. If you're running it from a different path, you'll need to adjust the path.

Once the `docker image` has been succesfully built, you can upload it to your `Docker Hub account` by following the next steps:

### 1) Login into your docker personal account
You have two ways in which you can login. The first one is by doing 
````sh
docker login
````
And the second one by doing:
````sh
docker login -u <dockerhub-username>
````
where `<dockerhub-username>` is your docker username account. This differs from the previous method by entering your password or `Personal Access Token (PAT)`. 

If you prefer to authenticate using a PAT, you'll first need to create a new one:

i) Go to [Docker Hub](https://hub.docker.com/) and login.

ii) Click on your profile picture (the circle in the upper right corner) and go to `account settings`.

iii) In the panel that should appear on the righ side, click on `Personal access tokens` and then click on `Generate new token`.

iv) Enter the information that it is required. We recommend you to establish an `expiration date` for your token.

v) Once you have click on the  `Generate` button, the token will appear and you will need to put it in the terminal where you are currently authenticating yourself.

>[!WARNING]
> Please note that the token will no longer appear, so be careful not to lose it.

### 2) Push the Image
Now that you've successfully logged into your account, you'll just need to run the following command to upload the image to DockerHub:
````sh
docker push <username>/<image-name>:<tag>
````
Where:
- `<username>` is your docker username.
- `<image-name>` is the name of the image (In this case is `liveticket`).
- `<tag>` is the version of the application (In this case is `1.0.0`).

### Image Build and Push using Scripts
This guide explains how to build and publish a Docker image for your Spring application using provided scripts. 

>[!NOTE]
> Since some colleagues experience issues with `docker login` when using Bash terminals (such as Git Bash), the scripts are provided in two versions: `.sh` for Linux and `.ps1` for Windows. This approach ensures that both Linux and Windows users can execute the scripts without compatibility problems.
>
> [Linux Scripts](https://github.com/SSDD-2025/Grupo-16/tree/main/docker/scripts/linux)
>
> [Windows Scripts](https://github.com/SSDD-2025/Grupo-16/tree/main/docker/scripts/windows)

#### Build Docker Image
In order to build the docker image of the application, you will need to excute the `create_image` script. 

If you are a Linux user, open a `Bash terminal`, navigate to `Grupo16/docker/scripts/linux/`, and execute:
````sh
./create_image.sh
````

If you are a Windows user, open a `PowerShell terminal`, navigate to `Grupo16/docker/scripts/windows/`, and execute:
````ps
./create_image.ps1
````

#### Publish Docker Image on Docker Hub
Once the previous script has been executed, you can publish the image to your `Docker Hub account` by executing the `publish_image` script.

If you are a Linux user, open a `Bash terminal`, navigate to `Grupo16/docker/scripts/linux/`, and execute:
````sh
./publish_image.sh
````

If you are a Windows user, open a `PowerShell terminal`, navigate to `Grupo16/docker/scripts/windows/`, and execute:
````ps
./publish_image.ps1
````

### Buildpack
Another alternative to build the docker image is with `buildpack`. This can be done by executing the following command:
````log
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=liveticket/liveticket:1.0.0
````

## 💻 Documentation for Deploying to Virtual Machines
In order to deploy the application onto the virtual machines, you will first need the IP addresses and access keys to each virtual machine involved in the process. In this case we will be explaining how to deploy the application using two virtual machines: one for the application and other for the database, as well as how to create a connection between them.

All the connections will be made using __Secure Shell (SSH)__, which is a network protocol that allows secure communication between the user and a remote machine over a network, ensuring the protection of transmitted data.

From this point on, we will refer to the virtual machine hosting the application as __MV1__ and the one hosting the database as __MV2__.

>[!NOTE]
>To facilitate the following steps we recommend you execute the commands from the folder where you have the access keys to the virtual machines, to ease the description of the relative paths to each key. To move between directories you can directly open a terminal from the folder or move to it using the `cd` command.

### 1) Connecting our computer to the MV1
Open a Windows/ Linux terminal on your computer and execute a command that connects your computer to the MV1. This command is build the following way:
````sh
ssh -i <access-key> <user>@<IP-address-MV1>
````
Where:
- `<access-key>` is the access key to the virtual machines.
- `<user>` is your username inside the MV1.
- `<IP-address>` is the public IP address or domain of the virtual machine.

In our case, the command should be:
````sh
ssh -i ssh-keys/sidi16.key vmuser@193.147.60.56
````
>[!NOTE]
>Executing this command for the first time will show a warning message telling you the machine you want to connect to is unknown. To complete the communication you will need to write `y` or `yes`(depending on the terminal).

### 2) Create a SSH Tunel between the Virtual Machines
Open another terminal on your computer. Now we will be creating the tunel between the two machines and, when finished, it will show us the terminal of the MV2. This command looks similar to the previous one, having the following structure:
````sh
ssh -t -i <access-key> <user>@<IP-address> ssh <IP-address-MV2>
````
Where:
- `<access-key>` is the access key to the virtual machines.
- `<user>` is your username inside the MV1.
- `<IP-address-MV1>` is the public IP address or domain of the virtual machine.
- `IP-address-MV2` is the IP address to the MV2 or its domain
  
In our case it should be:
````sh
ssh -t -i ssh-keys/sidi16.key vmuser@193.147.60.56 ssh sidi16-2
````

>[!IMPORTANT]
>If the virtual machines do not have `Docker` install you must install it. This is because the application is containerized and runs inside a Docker environment.
>To install Docker on a Linux machine you can execute the following command:
>````bash
>curl -fsSL https://get.docker.com -o get-docker.sh
>````
>You can see other ways to install it or more information on [Install Docker Ubuntu](https://docs.docker.com/engine/install/ubuntu/)

### 3) Deploying the Application
To this point you should have opened two terminals on your computer: one being the MV1 and the other being the MV2. Both virtual machines should have docker installed.

Before starting the application, you need to create and run the database on the MV2. To do this, you will launch a `MySQL` container on the MV2 using a Docker image with the root_password, password and database name specified in the application's `application.properties` file. This container will use the port number 3306 of MV2 and will connect to the port 3306 of the MV1.
````bash
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=LiveTicket -e MYSQL_DATABASE=liveticketdb -e MYSQL_PASSWORD=LiveTicket -p 3306:3306 -d mysql:8
````

When the database is created, switch to the MV1 terminal and launch the application from the Docker image. It is important that, in the command, you specify the database URL because now it is not `localhost` as we have in the `application.properties`, but it is located at the MV2 address.
````bash
docker run -d --name liveticket-container -p 8443:8443 -e SPRING_DATASOURCE_URL=jdbc:mysql://192.168.110.114/liveticketdb liveticket/liveticket:1.0.0
````
>[!NOTE]
>Notice the `-d` parameter on both run commands. This is so the command runs on the background, leaving the prompt active in order to execute other operations as `docker ps` or `docker stop`.

## 🚀 Application Deployed on Virtual Machines
When the application is launch from the virtual machines, you can access to it by its URL as the same way we previous do using `https://localhost:8443`. Because now the application does not run on our computer we cannot use the `localhost` variable, instead we need to use the public IP address of our virtual machine. In our case, this is:
https://193.147.60.56:8443

The application works the same way as it used to be, it just runs in other place. You can access it using the following default administrator users:
  * Username: armiiin13; Password: eras1325.
   * Username: Fonssi29; Password: pollitoPio.
   * Username: davih; Password: davilico.

You can also create your own account to test it.

# 📜 License
This project follows the Apache 2.0 license regulations. For more information you can consult it [Here](LICENSE).
