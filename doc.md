# Documentation Version 1
## Requirement

## Design
<em>Main Page:</em>
<ul>
  <li>Searcher (it lets you to filter the search)</li>
  <li>Log In</li>
  <li>Check user page</li>
  <li>Settings</li>
  <li>For anon users, shows upcoming concerts taking place (sorted by artist fame)</li>
  <li>For registered users, shows upcoming concert taking place at the continent (could be country) of the user</li>
  <li>Show the new artists subscribed at the website</li>
</ul>
<em>Artist Page:</em>
<ul>
  <li>All the information of the artist kept at the database</li>
  <li>Image</li>
  <li>Upcoming concerts (with an option to see more --> extra)</li>
  <li>Most notable albums</li>
  <li>Buttons linking to Spotify and Apple Music</li>
  <li>Most notable music video</li>
</ul>
<em>Select Concert Page:</em>
<ul>
  <li>List of concerts by an especified artist (with all the posible dates). If there are no concerts, it will show a message informing it doesn't exist</li>
  <li>Cute button to show the information about the concert (showInfo) and to hide ir (showLess)</li>
</ul>
<em>Puchase Page:</em>
<ul>
  <li>Stadium map</li>
  <li>Choose between pit or bleachers (in a further model it could be possible to choose between more specific seats)</li>
  <li>Select number of tickets</li>
  <li>Button to purchase</li>
</ul>
<em>Charge Page:</em>
<ul>
  <li>Insert the information of the bank card</li>
</ul>
<em>Purchase Confirmation Page:</em>
<ul>
  <li>It shows the information whether if the purchase was successful or not</li>
  <li>Go to main page button</li>
</ul>
<em>Log In</em> <br>
<em>Register</em> <br>
<em>User Page:</em> <br>

## Entities
<em>User</em>
<em>Artist</em>
<em>Concert</em>
<em>Ticket</em>

## Main Page Information
The main page is composed by two distincted parts: display a list of concerts and display a list of artists. First, it displays a list of concerts (which changes if the user is logged or not). If the user is not logged, it will show a finite number of concerts, one besides the other (in a row) that are sorted by artist's fame. If the user is logged, it will show a list of concerts being held at the same country/ continent as the user. This list it is specified as an injectable HTML called `display-concerts.html` (see its information later on this page). The boolean variable to know if the user is logged is called `is-logged`.

For the artist's list, it is an injectable HTML called `display-artists.html` (see its information later on this page).

Important notes on element styling is that the `div` elements containing the photo and the button of each concert it has the display:inline-block that lets a finite number of concerts being on the same row (depending on the dimensions of the page on the computer). The same style is applied to the `div` inside the injectable HTML display-artists.html amd display-concerts.html. All the styles are specified at `main-style.css`.

## Artist Page
This is the information page of the artist searched. It contains all the artist's data that is saved in the database and also a visualization of two albums by the artist (and their corresponding links to Spotify and Apple Music) and a music video to watch from the webpage without having to access YouTube. It also has a display of the upcoming concerts of that artist.

First of all, the title of the page is the name of the artist, so the variable `name` its necessary to be able to put this title, in addition to the main title of the page (also this variable will be used a lot throughout the document). 

The main information block is made up of a photo of the artist on the left and a main paragraph containing a summary of the artist on the right (from their name and birthdate to their biggest songs and upcoming albums). The structure of this paragraph its the same for all the artist, so it has a lot of variables refering to data of the artist that should be on the database. The following table shows all the variables that are on the paragraph and which data it takes (and whether they are mandatory or not):
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/ null??</th>
  </thead>
  <tbody>
    <tr>
      <td>name</td>
      <td>Artist's name</td>
      <td>No</td>
    </tr>
    <tr>
      <td>other-jobs</td>
      <td>A list containing other jobs the artist do</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>birth-place</td>
      <td>Place of birth</td>
      <td>No</td>
    </tr>
    <tr>
      <td>birth-date</td>
      <td>Date of birth</td>
      <td>No</td>
    </tr>
    <tr>
      <td>is-female</td>
      <td>Boolean indicating whether the artist is female or not</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>fame-song</td>
      <td>Name of the song that made them famous</td>
      <td>No</td>
    </tr>
    <tr>
      <td>month-release</td>
      <td>Month and year when fame-song was released</td>
      <td>No</td>
    </tr>
    <tr>
      <td>songs</td>
      <td>List of famous songs by the artist</td>
      <td>No</td>
    </tr>
    <tr>
      <td>new-song</td>
      <td>Latest song released</td>
      <td>No</td>
    </tr>
    <tr>
      <td>album-name</td>
      <td>Latest album released</td>
      <td>No</td>
    </tr>
    <tr>
      <td>album-date</td>
      <td>Latest album release date</td>
      <td>No</td>
    </tr>
    <tr>
      <td>new-album</td>
      <td>Name of the upcoming album by the artist</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>album-release</td>
      <td>Date of the upcoming album release</td>
      <td>Yes (No if there is an upcoming album)</td>
    </tr>
  </tbody>
</table>

After that, there is a space for additional information saved in the database as a text. It is optional and its variable is called `more-info`.

Then comes the music part, with two album covers one besides the other. One refers to the best album done by the artist and the other one to the latest album released. Below each cover there are two images that works like a hyperlink to Spotify or Apple Music and it takes the user to the album page on that applications so it could hear them. The album cover photos are referenced by the variables `best-album` and `soon-album`. The four hyperlinks should be saved at the database and its variables are called `spotify-best-album`, `apple-best-album`, `spotify-soon-album` and `apple-soon-album`, respectively.

Then there is a video that is paused and muted until the user decides that he want to watch it and click on it. Then, the user should be able to watch that music video without leaving the page. It is muted, so the user should activate its sound as well. The hyperlink is referenced by `mv-link`.

At last, it shows the upcoming concerts list using the display-concerts.html injectable. Be sure to include the attribute `concert-list` in the controller while creating this page.

All the styles are specified on `artist-style.css`.

## Display-Artists (Injectable)
It is a HTML document specifically to be injected into a main HTML document. Displays the list of artist (which is passed through a variable) by placing the photo of each artist in a row and, below each one, a button connecting to the artist's page. This variable list is called `artist-list` and, if it is null, it will show the message "There are no artist to show".

This document consists on three elements classes: `image-container` (div envolving one image + button),`artist-photo` (artist's photo) and `info-artist` (the input that links to the artist's main page). To change the styles of this elements it must be specified at the CSS document applying to the main page where the injectable is. For later versions of the page it could be specified in the main styles page, but the photos could have different dimensions depending on which page the injectable is placed.

## Display-Concerts (Injectable)
It is a HTML document specifically to be injected into a main HTML document. Displays the list of concerts (which is passed through a variable) by placing the photo of each concerts's poster in a row and, below each one, a button connecting to the purchase page of that concert. This variable list is called `concert-list` and, if it is null, it will show the message "There are no concerts to show".

This document consists on three elements classes: `image-container` (div envolving one image + button),`concert-photo` (concert's poster) and `buy` (the input that links to the purchase page). To change the styles of this elements it must be specified at the CSS document applying to the main page where the injectable is. For later versions of the page it could be specified in the main styles page, but the photos could have different dimensions depending on which page the injectable is placed.

## Sign In and Sign Up
In this HTML document, the user will be able to create an account (in case he does not have any account or wants to create another one) and log in to an existing account with his credentials.

At the design level, both alternatives were designed on the same page, however, depending on whether or not the user is in the database,one design or another will be shown. This is the most relevant information regarding those posibilities:

### Sign In:
<ul>
  <li>Everything its contained in a <strong> <em> form </em> </strong> block usign the <strong> <em> post </em> </strong> method, so the information entered by the user can be collected and used throught his session. The corresponding button will follow the <strong> <em> TBD </em> </strong> action. </li>
  <li>The user must enter his username or the email he used when creating the account, as well as the password.</li>
</ul>

### Sign Up
<ul>
  <li>It will have the same aspects as those mentioned above, but, aditionally, the user must select his country, in order to perform certain functionalities which will be explained on its corresponding page.</li>
  <li>The corresponding button will follow the <strong> <em> TBD </em> </strong> action.</li>
</ul>

### Mustache Variable
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>existUser</td>
      <td>Will display the log in form if the user is in the database (true), in other case, will display the register one</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>error</td>
      <td>Indicates if the userName or the password introduced by the user is incorrect</td>
      <td>No</td>
    </tr>
    <tr>
      <td>newUser</td>
      <td>If the user is already register in the database, it will be indicated and also, a link to go to the log in form</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

This file can be found with the name `sign-in.html` and the styles that were used in the `sign-in-style.css` file.

## Purchase Confirmation
This page will only display a message telling the user that the ticket purchase was succesfull. Furthermore, this will help the web application to follow the principles of accessibility, more specifically, robustness, showing a feedback to the user that everything went correctly.

This file can be found with the name `purchase-confirmation.html` and the styles that were used in the `purchase-confirmation-style.css` file.

## Header (Injectable)
The header in file `header.html` will be present almost everywhere in the webpage. It's main function is to provide the user a direct, simple an accesible way to search for artists, concerts or visit it's personal-based pages. It is constructed so that it can be used in any dispositive (responsive), generating a burger menu when less than 900px are reached. Also, it makes another transformation when less than 575px are reached so that things do not collapse. It is structured in four distinct parts, each one with a diferent function.

Logo zone: presential and brand purposes place where the logo is displayed and, when clicked, it transports the user to the main page.

Menu: if the witdth of the screen is enough, it is displayed along the header. In another case, it is packed up into a burger menu that can be displayed with a click. Also, the different items displayed here deppends on the kind of user (Admin, Logged regular user or Anonymous, check the variable table for more information). Some of the items in the menu are the following:
<ul>
  <li>Register: when the user is not logged, it invites the user to register</li>
  <li>Sign In: when the user is not logged, it invites the user to login</li>
  <li>Add: admin purposes, page where entities can be added</li>
  <li>Profile: when the user is logged, access to the profile page</li>
  <li>My Concerts: when the user is logged, page where the user can search the tickets that he/she owns</li>
  <li>My Artists: when the user is logged, page where the user can search its followed artists</li>
</ul>

Searching zone: contains the searching bar and search button in form of a magnifying glass. From here, searches can be made as far as artists and concerts are concerned and the user is redirected to the search page.

User logo: if the user is logged, a user logo is displayed and can be clicked to visit the profile page. If the user is not logged, a logo with an interrogation mark '?' is shown, inviting the user to register or login.
<ul>
  <li>If the user is not registered or logged, shows the Login and Register buttons.</li>
  <li>If the user is logged, displays a button with it's profile photo and a dropdown menu with direct access to: My profile, My concerts and My artists.</li>
</ul>

__Mustache variables:__ 
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>isLogged</td>
      <td>Determines wether the user is logged or not, making personal options to appear</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>isAdmin</td>
      <td>Determines wether the user is an admin or not, making admin options to appear</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

__Use:__ To include the header into an HTML page, it should be injected using `{{>header}}` between a `<header></header>` block, located inside the `body` of the file. The styles used for the header are located at the `header-style.css`, be sure to link the file correctly.

## Concert (Injectable)
The concert contained in `concert.html` is an injectable piece of code that, located inside a Mustache Concert list, displays with style each concert most important information. It is thought to be injected inside a general container, but it is recommended to inject it inside a `general-container` class container. Each concert entry is structured in five parts with different purposes.

<ul>
  <li>Cover Image: shows the cover or promotional image of the concert.</li>
  <li>Date: displays in a bigger and remarked style the date of the concert.</li>
  <li>General information: artist name, hour, location and brief description of the concert.</li>
  <li>Buttons: Get Tickets, More Info and Artist Page buttons.</li>
  <li>Long description: initially hidden and when the More Info button is toogled, it is shown extra information of the concert.</li>
</ul>

__Mustache variables:__ 
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>artist.name</td>
      <td>Artist's name</td>
      <td>No</td>
    </tr>
    <tr>
      <td>link.to.image</td>
      <td>Concert main image or promotional cover (TO BE IMPLEMENTED)</td>
      <td>No</td>
    </tr>
    <tr>
      <td>date</td>
      <td>Exact date when the concert takes place</td>
      <td>No</td>
    </tr>
    <tr>
      <td>formattedTime</td>
      <td>Hour (with format) when the concert will start</td>
      <td>No</td>
    </tr>
    <tr>
      <td>place</td>
      <td>Place where the concert takes place</td>
      <td>No</td>
    </tr>
    <tr>
      <td>info</td>
      <td>Concert brief general description</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>isLogged</td>
      <td>Determines if the user is logged or not</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>-index</td>
      <td>Used to implement the More Info button</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

__Buttons Available:__ For each concert, there are displayed three different buttons, with different functionalities.
<table>
  <thead>
    <th>Button</th>
    <th>Action</th>
  </thead>
  <tbody>
    <tr>
      <td>Get Tickets</td>
      <td>Visit the Ticket purchase page of the specific concert (if the user is logged). Elsewise, it transports the user to the sign-in page</td>
    </tr>
    <tr>
      <td>Artist Page</td>
      <td>Visit the Artist's information page</td>
    </tr>
    <tr>
      <td>More Info</td>
      <td>Display a div container under the concert display, containing additional information about the concert</td>
    </tr>
  </tbody>
</table>

__Use:__ To include the concert into an HTML page, it should be injected using `{{>concert}}`, located between a Mustahce `{{#listOfConcerts}}{{/listOfConcerts}}` block, inside the `body` of the file. The styles used for each concert are located at the `concert-style.css`, be sure to link the file correctly.

## Search page
The search page contained in `search-page.html` is a dynamic piece of code that displays artists and concerts related to a search made by the user in the header's searchbar. If the user is logged, an extra section is shown with concerts related with the user's preferences. 

It makes use of `header`, `footer`, `display-artists` and `concert` injectable pieces of code. Three main sections can be distinguished:
<ul>
  <li>Artists: artists whose artist name is related with the search made by the user.</li>
  <li>Concerts: concerts which name or artist name is related with the search made by the user.</li>
  <li>Concerts near you (if the user is logged): same as concerts section but ensuring the concert takes place near where the user is registered.</li>
</ul>

__Mustache variables:__ 
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>isLogged</td>
      <td>The user is or is not logged</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>personalConcertsList</td>
      <td>Concerts near the user (if logged) list</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>generalConcertsList</td>
      <td>Concert list matching the search of the user</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

Bear in mind the fact that the pieces of code injected might have other variables, check it in its specific documentation place.

__Use:__ The search page HTML is a base file that need to have injected the files: `header`, `footer`, `display-artists` and `concert` in order to work correctly. The styles used for this page are contained in the general css `common-styles.css`, be sure to link the file correctly.

## Ticket Selling
The main purpose of this page is to manage the sellection of the ticket that the user wants to purchase. This page will give the user the possibility to access its profile in any instance of the process by clicking his profile picture, located in the upper right corner of the page. In its body, will show the name of the selected concert, an image of the plan of the stadium where said event will take place, where all its sections will be well specified, and next to this, a dropdown menu to select the user´s preferred section.

Great part of this information is managed through mustache variables, which are the following ones:
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>concert.name</td>
      <td>It is the name of the selected concert</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.place</td>
      <td>It is the name of the stadium where the concert will be held</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.id</td>
      <td>Is the id of the selected concert</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.artist.name</td>
      <td>It is the name of the artist giving the selected concert</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.formattedTime</td>
      <td>It is the time when the selected concert occurs</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.remainSouthStands</td>
      <td>Indicates if there are or not available entrances in the South zone</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.remainEastStands</td>
      <td>Indicates if there are or not available entrances in the East zone</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.remainWestStands</td>
      <td>Indicates if there are or not available entrances in the West zone</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.remainGeneralAdmissionStands</td>
      <td>Indicates if there are or not available entrances in the GeneralAdmission zone</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

__Note:__ Bear in mind that this piece of code is dependent on `header.html`, `ticket-selling-style.css` and `common-styles.css`. Be sure to link them correctly.

## Purchase Page
The main purpose of this page is to confirm the selected tickets and enter the information of the user's credit card to complete the tickets purchase.

At first it shows a gray block with the ticket information. Everything revolves around the variable `ticket`, which contains the tickets that are being purchased. If null, it will show an error message and a link to go back to the main page. If the ticket is an object it will show its information using the variables `name` (concert name), `date` (concert date), `number` (number of tickets purchase), `type` (type of the tickets) and `price` (price of the tickets per unit). Besides the information, it shows the concert poster using the variable `concert-poster` (which is the URL to the image). Below the information there is a button that lets the user to cancel the purchase and takes him back either to the main page or to the select ticket page (TO BE DISCUSSED).

Below this block there is a small block in a darker shade that shows the total pricing of the tickets (multiply ticket price by the number of tickets).

At last, there is another gray block with a form. This form is for the user to write down the information of his/ her credit card so the purchase can be completed. For now, this information will not be saved (as an complement to this proyect, the user can save their credit card information and it will show at this form as default information). This form is composed of three input text (cardholder name, card id and cvv), a select to choose from different card types and a month input to enter the expiration date. Below everything there is a submit button that takes you to the purchase confirmation page.

All the styles of this page are specified in the `purchase-style.css` document.

## Footer (Injectable)
The footer.html is a static injectable document that consists of a black div containing the name, phone number and email of the company that runs the webpage. It is shown at all the main pages as the footer of the page. For that, it should be injected at this main pages inside the block &lt;footer&gt; and its style is included at the `common-style.css`.

## Display Tickets (Injectable)
The ticket displayer contained in `display-tickets.html` is an injectable piece of code that, provided with a Ticket list, displays information about every ticket in a visual way so that the user can take a quick view to all of its tickets. It is thought to be injected inside any container, but it is recommended to inject it inside a `general-container` class container.

Each ticket entry is structured in three parts:
<ul>
  <li>Concert image: promotional or brand photo used for the concert.</li>
  <li>Concert name: title of the concert.</li>
  <li>Specific information: zone of the ticket, hour and place where the concert takes place.</li>
</ul>

__Mustache variables:__ 
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>ticketList</td>
      <td>List containing the tickets to be displayed</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>concert.name</td>
      <td>Name of the concert assigned to the ticket</td>
      <td>No</td>
    </tr>
    <tr>
      <td>info</td>
      <td>Ticket specific info (to be fully implemented)</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

__Use:__ The `display-tickets.html` injectable file makes is thought to be used inside a personal user page. The styles used are contained in `personal-pages-style.css`, be sure to link the file correctly.

## Profile page
The search page contained in `my-profile.html` is a dynamic piece of code that allows the user to change and search its settings, navigate through its favourite artists and check its bought tickets information. For that, the space is divided in two parts: personal panel and projector zones.

The personal panel is a simple an accesible place where the user's photo is shown, as well as a panel of buttons with links to navigate along its favourite artists, search for its tickets and know/modify its information saved at the database. In upcoming updates, this is the place where administrator buttons or settings should be located.

The projector zone is the place where all the previously mentiones content will be displayed. It almost has the screen size and depending on the values that the locking-variables receive, will show or hide information. The type of information depends on the specific settings screen where the user is. To summarize:
<ul>
  <li>My Artists: A big title and artist list is displayed in the projector zone.</li>
  <li>My Concerts: A big title and artist list is displayed in the projector zone.</li>
  <li>My Profile: Username, email and access to profile photo updating services is displayed.</li>
</ul>

__Mustache variables:__ 
<table>
  <thead>
    <th>Variable</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>showPersonalInfo</td>
      <td>When true, the display thought for "My Profile" is shown</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>showMyArtists</td>
      <td>When true, the display thought for "My Artists" is shown</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>showMyConcerts</td>
      <td>When true, the display thought for "My Concerts" is shown</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

Bear in mind the fact that the pieces of code injected might have other variables, check it in its specific documentation place.

__Use:__ The search page HTML is a base file that needs to have injected the: `header`, `footer`, `display-tickets` and `display-artists` in order to work correctly. Only one of the three content-locker variables should be true at the same time, otherwise, unexpected content might be shown. The styles used for this page are contained in `personal-panel-style.css` and `personal-pages-style.css`, be sure to link the file correctly.



## Classes (Entities) documentation.

As the following classes are thought to be `@Entity` using JPA, they are followed by their correspondent `JpaRepository` so that query methods are collected in the present document.

### Artist
The Artist class contains al the information regarding an artist. An artist recognizes its concerts, which means that the List atribute keeping all the concerts of one artist is tagged with a `@OneToMany(mappedBy = "artist")`. It also keeps the main links to its albums and music video so it can be upload on the page.

The artist class has three __constructors__:
<ul>
  <li>Empty one: used internally by JPA.</li>
  <li>Every attribute but image: used to create an image-less concert.</li>
  <li>Every attribute: used to create a complete concert.</li>
</ul>

On the next table are specified the __attributes__ of the class (all these attributes are specified with their getters and setter, except the id, which only has getter):
<table>
  <thead>
    <th>Attribute</th>
    <th>Type</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>id</td>
      <td>long</td>
      <td>Automatically generated ID when an Artist is created</td>
      <td>No</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>Artistic name of the artist</td>
      <td>No</td>
    </tr>
    <tr>
      <td>popularityIndex</td>
      <td>int</td>
      <td>Number of Spotify listeners that the artist has (it needs to be greater or equal than a static value and its used for filtering by popularity)</td>
      <td>No</td>
    </tr>
    <tr>
      <td>sessionCreated</td>
      <td>LocalDateTime</td>
      <td>Date and time on which the artist registered its data for the first time</td>
      <td>No</td>
    </tr>
    <tr>
      <td>photo</td>
      <td>Blob</td>
      <td>Photo of the artist</td>
      <td>No (Yes, but it will be a default photo)</td>
    </tr>
    <tr>
      <td>mainInfo</td>
      <td>String</td>
      <td>Main information about the artist</td>
      <td>No</td>
    </tr>
    <tr>
      <td>extendedInfo</td>
      <td>String</td>
      <td>Additional information about the artist</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>bestAlbumSpotifyLink</td>
      <td>String</td>
      <td>Link to the "best album" on Spotify</td>
      <td>No</td>
    </tr>
    <tr>
      <td>bestAlbumAppleLink</td>
      <td>String</td>
      <td>Link to the "best album" on Apple Music</td>
      <td>No</td>
    </tr>
    <tr>
      <td>latestAlbumSpotifyLink</td>
      <td>String</td>
      <td>Link to the "latest album" on Spotify</td>
      <td>No</td>
    </tr>
    <tr>
      <td>latestAlbumAppleLink</td>
      <td>String</td>
      <td>Link to the "latest album" on Apple Music</td>
      <td>No</td>
    </tr>
    <tr>
      <td>bestAlbumPhoto</td>
      <td>Blob</td>
      <td>Image of the best album cover</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>latestAlbumPhoto</td>
      <td>Blob</td>
      <td>Image of the latest album cover</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>videoLink</td>
      <td>String</td>
      <td>URL to the music video on YouTube (should be a Blob that keeps the video itself)</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

Whether the attributes are null or have the correct value, should be addressed by the service, the class only keeps the information, it does not operate with it.

### Concert
The Concert class defines contains all the data characteristics expected for an singing event. There can be only one artist giving the concert, that means that the artist attribute is tagged with a `@ManyToOne` related with the artist.

Also, it contains attributes to measure the quantity of tickets available if the different zones stablished, as well as specific information like date and place.

The concert class has three constructors:
<ul>
  <li>Empty one: used internally by JPA.</li>
  <li>Every attribute but image: used to create an image-less concert.</li>
  <li>Every attribute: used to create a complete concert.</li>
</ul>
Every attribute specified below is accompained with its getter and setter methods.

__Attributes:__
<table>
  <thead>
    <th>Attribute</th>
    <th>Type</th>
    <th>Description</th>
    <th>Could be false/null?</th>
  </thead>
  <tbody>
    <tr>
      <td>id</td>
      <td>long</td>
      <td>Automatically generated ID when a Concert is created</td>
      <td>No</td>
    </tr>
    <tr>
      <td>artist</td>
      <td>Artist</td>
      <td>Artist who gives the concert</td>
      <td>No</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>Name that the concert has, may be the same as the tour one</td>
      <td>No</td>
    </tr>
    <tr>
      <td>info</td>
      <td>String</td>
      <td>Brief description of the concert, including specific information of the artist and place</td>
      <td>No</td>
    </tr>
    <tr>
      <td>date</td>
      <td>LocalDateTime</td>
      <td>Specific date (day and hour) when the concert occurs</td>
      <td>No</td>
    </tr>
    <tr>
      <td>place</td>
      <td>String</td>
      <td>Name of the place where the concert takes place</td>
      <td>No</td>
    </tr>
    <tr>
      <td>price</td>
      <td>float</td>
      <td>General price of every ticket</td>
      <td>No</td>
    </tr>
    <tr>
      <td>westStandsNumber</td>
      <td>int</td>
      <td>Number of West Stands tickets available</td>
      <td>No</td>
    </tr>
    <tr>
      <td>eastStandsNumber</td>
      <td>int</td>
      <td>Number of East Stands tickets available</td>
      <td>No</td>
    </tr>
    <tr>
      <td>southStandsNumber</td>
      <td>int</td>
      <td>Number of South Stands tickets available</td>
      <td>No</td>
    </tr>
    <tr>
      <td>generalAdmissionNumber</td>
      <td>int</td>
      <td>Number of General Admission tickets available</td>
      <td>No</td>
    </tr>
    <tr>
      <td>image</td>
      <td>Blob</td>
      <td>Image of the concert's poster</td>
      <td>Yes</td>
    </tr>

  </tbody>
</table>

__Note:__ No comprovals are made over the attributes asigned, the Service should check whether the attributes are null or not, as well as assign default values (if it corresponds).

### UserEntity
This class will contain all the relevant information regarding a single user of the application. As a consequence of being an entity in a relational model, it has relation with the following entities:
<table>
  <thead>
    <th> Entity</th>
    <th> Cardinality</th>
    <th> Annotion</th>
  </thead>
  <tbody>
    <tr>
      <td>Artist</td>
      <td>1..N</td>
      <td>@OneToMany</td>
    </tr>
    <tr>
      <td>Tickets</td>
      <td>1..N</td>
      <td>@OneToMany</td>
    </tr>
  </tbody>
</table>
Furthermore, the class consist of 3 constructors and a series of attributes that help us store the user information previously mentioned.

**Constructors:**
<ol>
  <li>An empty constructor for exclusive use of JPA.</li>
  <li>A constructor that will initialize the entity with all of its attributes (except the profile picture).</li>
  <li>Will do the same function as the previous one, but this includes the profile picture.</li>
</ol>

**Note:** The non empty constructors, will verify that any of the attributes will not be empty.

**Attributes:**
<table>
  <thead>
    <th>Name</th>
    <th>Type</th>
    <th>Description</th>
    <th>Could be null?</th>
  </thead>
  <tbody>
    <tr>
      <td>id</td>
      <td>long</td>
      <td>Is the primary key of the entity. It will identify each user</td>
      <td>No</td>
    </tr>
    <tr>
      <td>userName</td>
      <td>String</td>
      <td>Name of the user account</td>
      <td>No</td>
    </tr>
    <tr>
      <td>password</td>
      <td>String</td>
      <td>Password of the user´s account</td>
      <td>No</td>
    </tr>
    <tr>
      <td>email</td>
      <td>String</td>
      <td>Email of the user</td>
      <td>No</td>
    </tr>
    <tr>
      <td>country</td>
      <td>String</td>
      <td>Country where the user is located, in order to filtrate the search by the nearest concert</td>
      <td>No</td>
    </tr>
    <tr>
      <td>profilePicture</td>
      <td>Blob</td>
      <td>A picture that the user will upload as his profile picture</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>artistList</td>
      <td>List<Artist></td>
      <td>Collection of all the artist the user is following</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>ticketList</td>
      <td>List<Tickets></td>
      <td>Collection of all the tickets the user has purchased</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

__Methods__: all the attributes of the class are defined with its getters and setters (except the setId because its authomatically generated and it is not going to change).

### Ticket
This class will contain all the relevant information regarding the tickets for the different concerts. As a consequence of being an entity in a relational model, it has relation with the following entities:

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

Furthermore, the class consist of 3 constructors and a series of attributes that help us store the ticket information previously mentioned.

<strong>Constructors:</strong>
<ol>
  <li>An empty constructor for exclusive use of JPA.</li>
  <li>A constructor that will initialize the entity with the place and the price of the ticket.</li>
  <li>Will do the same function as the previous one, but this one will add its user and concert.</li>
</ol>
<strong>The non empty constructors, will verify that any of the attributes will not be empty.</strong>

<strong>Attributes:</strong>
<table>
  <thead>
    <th>Name</th>
    <th>Type</th>
    <th>Description</th>
    <th>Could be null?</th>
  </thead>
  <tbody>
    <tr>
      <td>id</td>
      <td>long</td>
      <td>Is the identification number of the ticket. It is the primary key of this entity</td>
      <td>No</td>
    </tr>
    <tr>
      <td>ticketUser</td>
      <td>UserEntity</td>
      <td>Is the owner of the ticket (the user associated with a ticket)</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>concert</td>
      <td>Concert</td>
      <td>Is the concert where the tickets are available (the concert associated with a ticket)</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>zone</td>
      <td>String</td>
      <td>It is the section/zone of the stadium</td>
      <td>No</td>
    </tr>
    <tr>
      <td>price</td>
      <td>float</td>
      <td>It is the cost of the ticket.</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

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
    </tr>
    <tr>
      <td>isUserLogged</td>
      <td>boolean</td>
      <td>Returns whether the user is logged or not</td>
      <td>void</td>
    </tr>
  </tbody>
</table>

The attribute __activeUser__ also has its getter and setter defined.

## Services documentation.

The following classes are `@Service` which main function is to provide service to the `@Controller` for querys and specific specific logic.

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
      <td>List of Artist</td>
      <td>It returns a list to display on the main page with the top ten artist with the highest popularity rate</td>
      <td>None</td>
    </tr>
    <tr>
      <td>getArtistDisplayBySession</td>
      <td>List of Artist</td>
      <td>It returns a list to display on the main page with the top ten artist recent added artist</td>
      <td>None</td>
    </tr>
  </tbody>
</table>


### ConcertService
The ConcertService `@Service` contains all the methods that could be used by the different `@Controller`s in order to make database querys and access in a simple an modularizated way to the concert specific data.

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
      <td>getSearchBy</td>
      <td>List of Concert</td>
      <td>Searches a concert which name or artist name contains the parameter</td>
      <td>String search</td>
    </tr>
    <tr>
      <td>getConcertDisplay</td>
      <td>List of Concert</td>
      <td>It returns the concert display list to put on the main page (it changes whether the user is registered or not)</td>
      <td>boolean userLogged</td>
    </tr>
    <tr>
      <td>verifyAvailability</td>
      <td>boolean</td>
      <td>It will check if there are tickets available of a certain type</td>
      <td>long id, int number, String type</td>
    </tr>
  </tbody>
</table>

### UserService
This `@Service` will serve as an intermediate between the `UserController` and the `UserRepository` in order to follow the `Hexagonal Arquitecture`. This service will have all the operations the `@Controller`s will need in order to access the data on the database, in other words, the querys.

This are the methods that this `@Service` will have <strong><em>By the moment</em></strong>:

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
    <tr>
      <td>isLogged</td>
      <td>boolean</td>
      <td>Will determinate if the activeUser (@Autowired) is logged or not</td>
      <td>void</td>
    </tr>
  </tbody>
</table>

There will be an instance of the `UserRepository` from which all the querys will be made.

### TicketService
This `@Service` will serve as an intermediate between the `TicketController` and the `TicketRepository` in order to follow the `Hexagonal Arquitecture`. This service will have all the operations the `@Controller`s will need in order to access the data on the database, in other words, the querys.

This are the methods that this `@Service` will have <strong><em>By the moment</em></strong>:

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

### CardVerifyingService

The function of this `@Service` will be to verify that the information entered on the credit card is correct, that is, that it complies with the correct format. It will be used in the `TicketController` class.

This are the methods implemented on this `@Service`:

<table>
  <thead>
    <th>Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>verifyCardHolder</td>
      <td>boolean</td>
      <td>It will check if the name of the card`s holder is in the correct format</td>
      <td>String cardHolder</td>
    </tr>
    <tr>
      <td>verifyCreditCardNumber</td>
      <td>boolean</td>
      <td>It will verify if the credit card number is correct, and complies the correct format based on the Luhn`s Algorithm</td>
      <td>String number</td>
    </tr>
    <tr>
      <td>getType</td>
      <td>String</td>
      <td>It will obtain the credit card type, based on the format of its number</td>
      <td>String number</td>
    </tr>
    <tr>
      <td>verifyExpirationDate</td>
      <td>boolean</td>
      <td>It will verify if the entered expiration date its not a date prior to when the form is filled</td>
      <td>String date</td>
    </tr>
    <tr>
      <td>verifyCVV</td>
      <td>boolean</td>
      <td>It will verify if the credit card security number cumplies the correct format</td>
      <td>String cvv</td>
    </tr>
  </tbody>
</table>

## Repositories Documentation.

The following section will be dedicated to document all the methods and purposes of all the `@Repository` the application will have. Every one of them, will extend the `JpaRepositoy` interface.

### ArtistRepository
The ArtistRepository is an interface that controlls queries on the artist database.

It has the following methods:
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
      <td>List of Artist</td>
      <td>It returns the list of the top 10 artist with the highest popularity index</td>
      <td>None</td>
    </tr>
    <tr>
      <td>findTop10ByOrderBySessionCreatedDesc</td>
      <td>List of Artist</td>
      <td>It returns the list of the top 10 artist with most recent session created date</td>
      <td>None</td>
    </tr>
  </tbody>
</table>

### ConcertRepository
The ConcertRepository is an interface that controlls queries on the concert database.

It has the following methods:
<table>
  <thead>
    <th>Method Name</th>
    <th>Return Type</th>
    <th>Description</th>
    <th>Parameters</th>
  </thead>
  <tbody>
    <tr>
      <td>getConcertByPlace</td>
      <td>List of Concert</td>
      <td>It searches the concerts that are taking place at a specific country/ city given as a parameter</td>
      <td>String place</td>
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

### UserRepository
It will be in charge of save all the information regarding all the users the application could have.

<strong><em>By the moment</em></strong>, this are all the methods that this repository will have:

<table>
  <thead>
    <th>Name</th>
    <th>Return Type</th>
    <th>Parameters
    <th>Description</th>
  </thead>
  <tbody>
    <tr>
      <td>findByUserName</td>
      <td>UserEntity</td>
      <td>String userName</td>
      <td>It will look for a user in the database by his userName</td>
    </tr>
    <tr>
      <td>findByUserNameAndPassword</td>
      <td>UserEntity</td>
      <td>String userName, password</td>
      <td>It will look for a user in the database by his userName and password</td>
    </tr>
    <tr>
      <td>findByid</td>
      <td>UserEntity</td>
      <td>long id</td>
      <td>It will look for a user in the database by its id</td>
    </tr>
  </tbody>
</table>

### TicketRepository
It will be in charge of save all the information regarding all the tickets a concert could have.

<strong><em>By the moment</em></strong>, this are all the methods that this repository will have:

<table>
  <thead>
    <th>Name</th>
    <th>Return Type</th>
    <th>Parameters
    <th>Description</th>
  </thead>
  <tbody>
    <tr>
      <td>findTicketById</td>
      <td>Ticket</td>
      <td>long id</td>
      <td>It will obtain a ticket from the database</td>
    </tr>
  </tbody>
</table>

## Controllers Documentation.

The following section will be dedicated to document all the methods and purposes of all the `@Controller` the application will have.

### MainController
This `@Controller` will be in charge of managing the main page and the main search.

It contains the dependancies of the services of the main classes of the web and for the `@Component` activeUser. 

It has a method called __getMain__ which gets the mapping __"/"__ and it has two atribbutes: the model and what is searched on the searched bar (it is optional).

If the optional value of the searched bar is null, it will load up the main page. Before that it will stablished its attributes:
<ul>
  <li><strong>isLogged</strong>: using the UserService, will determine if the activeUser is logged or not</li>
  <li><strong>concertList</strong>: using the ConcertService, will determine the list of concerts to display at the main page (according on what user is active: anon or registered)</li>
  <li><strong>artistList</strong>: using the ArtistService, will determine the list of artist to display</li>
</ul>

### UserController

This `@Controller` will be in charge of managing <strong><em>TBD</em></strong>

<strong><em>By the moment</em></strong> this will be all the methods this `@Controller` will have:

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
      the database or an error occurs, the user will be norified</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `UserService` class, which will be under the annotation `@AutoWired`.

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

### TicketController

This `@Controller` will be in charge of managing everything related to the tickets for the different concerts that are registered in the application.

<strong><em>By the moment</em></strong> this will be all the methods this `@Controller` will have:

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
       otherwise, the purchase template will be shwon with an error message.</td>
    </tr>
  </tbody>
</table>

It will have an instance of the `CardVerifyingService `, `ConcertService` and `TicketService` classes, which will be under the annotation `@AutoWired`.

### ImageController
This `@Controller` will be in charge of all the images of the page. Essencially, it will download each respective image whenever is called.

Every method on this controller will return a ResponseEntity&lt;Object&gt; with the photo or the object indicating it has not been found:
<table>
  <thead>
    <th>Name</th>
    <th>Mapping Type</th>
    <th>URL</th>
    <th>Parameters</th>
    <th>Description</th>
  </thead>
  <tbody>
    <tr>
      <td>downloadArtistPhoto</td>
      <td>@GetMapping</td>
      <td>/artist/{id}/download-photo</td>
      <td>@PathVariable long id</td>
      <td>Downloads the artist's photo</td>
    </tr>
    <tr>
      <td>downloadLatestAlbumPhoto</td>
      <td>@GetMapping</td>
      <td>/artist/{id}/download-latestAlbum-photo</td>
      <td>@PathVariable long id</td>
      <td>Downloads the latest's album cover from the artist (id)</td>
    </tr>
    <tr>
      <td>downloadBestAlbumPhoto</td>
      <td>@GetMapping</td>
      <td>/artist/{id}/download-bestAlbum-photo</td>
      <td>@PathVariable long id</td>
      <td>Downloads the best's album cover from the artist (id)</td>
    </tr>
    <tr>
      <td>downloadProfilePicture</td>
      <td>@GetMapping</td>
      <td>/profile/{id}/download-profile-picture</td>
      <td>-</td>
      <td>Downloads the user's profile picture</td>
    </tr>
    <tr>
      <td>downloadConcertPoster</td>
      <td>@GetMapping</td>
      <td>/concert/{id}/download-poster</td>
      <td>@PathVariable long id</td>
      <td>Downloads the concert's poster</td>
    </tr>
  </tbody>
</table>
