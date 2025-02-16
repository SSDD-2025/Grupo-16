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
The only mustache variable used in this file was `user.existUser`. This is a boolean variable belonging to the `User` class, which indicates whether the user already exists in the database (has an account) or not, and depending on this, it will show one layout or another.

This file can be found with the name `sign-in.html` and the styles that were used in the `sign-in-style.css` file.

## Purchase Confirmation
This page will only display a message telling the user that the ticket purchase was succesfull. Furthermore, this will help the web application to follow the principles of accessibility, more specifically, robustness, showing a feedback to the user that everything went correctly.

This file can be found with the name `purchase-confirmation.html` and the styles that were used in the `purchase-confirmation-style.css` file.

## Header (Injectable)
The header in file `header.html` will be present almost everywhere in the webpage. It's main function is to provide the user with a direct, simple an accesible way to search for artists, concerts or visit it's personal-based pages. It is structured in three distinct parts, each one with a diferent function.

Logo zone: presential and brand purposes place where the logo is displayed.

Searching zone: contains the searching bar and search button. From here, searches can be made as far as artists and concerts are concerned.

Right zone: user based zone, that grants accessibility to the user.
<ul>
  <li>If the user is not registered or logged, shows the Login and Register buttons.</li>
  <li>If the user is logged, displays a button with it's profile photo and a dropdown menu with direct access to: My profile, My concerts and My artists.</li>
</ul>

__Mustache variables:__ `is-logged`: determines if the user is logged. In that case, shows the profile personal buttons. Otherwise, displays the Login and Register buttons.

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
      <td>coverImage</td>
      <td>Concert main image or promotional cover</td>
      <td>No</td>
    </tr>
    <tr>
      <td>hour</td>
      <td>Hour when the concert takes place</td>
      <td>No</td>
    </tr>
    <tr>
      <td>place</td>
      <td>Place where the concert takes place</td>
      <td>No</td>
    </tr>
    <tr>
      <td>description</td>
      <td>Concert brief general description (no more than one line)</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>longDescription</td>
      <td>Concert detailed description</td>
      <td>No</td>
    </tr>
    <tr>
      <td>-index</td>
      <td>Used to implement the More Info button</td>
      <td>No</td>
    </tr>
    <tr>
      <td>link.to.ticket.sale</td>
      <td>To be implemented shortly</td>
      <td>-</td>
    </tr>
    <tr>
      <td>link.to.artist.page</td>
      <td>To be implemented shortly</td>
      <td>-</td>
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
      <td>user.hasProfilePicture</td>
      <td>Indicates if the user has a profile picture</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>concert.name</td>
      <td>Is the name of the selected concert</td>
      <td>No</td>
    </tr>
    <tr>
      <td>concert.place</td>
      <td>Is the name of the stadium where the concert will be held</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

### Profile Photo Management
In the `profile.html` file, it gives the user the option to upload the photo he wants as a profile picture. This photo will be saved in the database along with all the information related to a specific user, and this is the reason for the `user.hasProfilePicture`variable usage, if there is an image loaded in the database (`user.hasProfilePicture = true`) said image will be displayed, otherwise, a default image will be displayed.

This file can be found with the name `ticket-selling.html` and the styles that were used in the `ticket-selling-style.css` file.

## Purchase Page
The main purpose of this page is to confirm the selected tickets and enter the information of the user's credit card to complete the tickets purchase.

At first it shows a gray block with the ticket information. Everything revolves around the variable `ticket`, which contains the tickets that are being purchased. If null, it will show an error message and a link to go back to the main page. If the ticket is an object it will show its information using the variables `name` (concert name), `date` (concert date), `number` (number of tickets purchase), `type` (type of the tickets) and `price` (price of the tickets per unit). Besides the information, it shows the concert poster using the variable `concert-poster` (which is the URL to the image). Below the information there is a button that lets the user to cancel the purchase and takes him back either to the main page or to the select ticket page (TO BE DISCUSSED).

Below this block there is a small block in a darker shade that shows the total pricing of the tickets (multiply ticket price by the number of tickets).

At last, there is another gray block with a form. This form is for the user to write down the information of his/ her credit card so the purchase can be completed. For now, this information will not be saved (as an complement to this proyect, the user can save their credit card information and it will show at this form as default information). This form is composed of three input text (cardholder name, card id and cvv), a select to choose from different card types and a month input to enter the expiration date. Below everything there is a submit button that takes you to the purchase confirmation page.

All the styles of this page are specified in the `purchase-style.css` document.

## Footer (Injectable)
The footer.html is a static injectable document that consists of a black div containing the name, phone number and email of the company that runs the webpage. It is shown at all the main pages as the footer of the page. For that, it should be injected at this main pages inside the block &lt;footer&gt; and its style is included at the `common-style.css`.