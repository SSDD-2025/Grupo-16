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

This file can be found with the name `sign-in.html` and the styles that were used in the `style-sign-in.css` file.

## Purchase Confirmation
This page will only display a message telling the user that the ticket purchase was succesfull. Furthermore, this will help the web application to follow the principles of accessibility, more specifically, robustness, showing a feedback to the user that everything went correctly.

## Header (Injectable)
The header will be present almost everywhere in the webpage. It's main function is to provide the user with a direct, simple an accesible way to search for artists, concerts or visit it's personal-based pages. It is structured in three distinct parts, each one with a diferent function.

Logo zone: presential and brand purposes place where the logo is displayed.

Searching zone: contains the searching bar and search button. From here, searches can be made as far as artists and concerts are concerned.

Right zone: user based zone, that grants accessibility to the user.
<ul>
  <li>If the user is not registered or logged, shows the Login and Register buttons.</li>
  <li>If the user is logged, displays a button with it's profile photo and a dropdown menu with direct access to: My profile, My concerts and My artists.</li>
</ul>

__Mustache variables:__ `is-logged`: determines if the user is logged. In that case, shows the profile personal buttons. Otherwise, displays the Login and Register buttons.

__Use:__ To include the header into an HTML page, it should be injected using `{{>header}}` between a `<header></header>` block, located inside the `body` of the file. The styles used for the header are located at the `header-style.css`, be sure to link the file correctly.
