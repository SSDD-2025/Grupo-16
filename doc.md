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
The main page is composed by two distincted parts: display a list of concerts and display a list of artists. First, it displays a list of concerts (which changes if the user is logged or not). If the user is not logged, it will show a finite number of concerts, one besides the other (in a row) that are sorted by artist's fame. If the user is logged, it will show a list of concerts being held at the same country/ continent as the user. In both cases, for each concert it will show the image of the promotional poster and, below it, a button connecting to the purchase page of that concert. The concert's list it is indicated by the variable `concert-list` and the boolean variable to know if the user is logged is called `is-logged`.

For the artist's list, it is an injectable HTML called artist-list.html (see its information later on this page).

Important notes on element styling is that the `div` elements containing the photo and the button of each concert it has the display:inline-block that lets a finite number of concerts being on the same row (depending on the dimensions of the page on the computer). The same style is applied to the `div` inside the injectable HTML artist-list.html.

## Artist-List Injectable
It is a HTML document specifically to be injected into a main HTML document. Displays the list of artist (which is passed through a variable) by placing the photo of each artist in a row and, below each one, a button connecting to the artist's page. This variable list is called `artist-list` and, if it is null, it will show the message "There are no artist to show".

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
The header will be present almos everywhere in the webpage. It's main function is to provide the user with a direct, simple an accesible way to search for artists, concerts or visit it's personal-based pages. It is structured in three distinct parts, each one with a diferent function.

Logo zone: presential and brand purposes place where the logo is displayed.

Searching zone: contains the searching bar and search button. From here, searches can be made as far as artists and concerts are concerned.

Right zone: user based zone, that grants accessibility to the user.
<ul>
  <li>If the user is not registered or logged, shows the Login and Register buttons.</li>
  <li>If the user is logged, displays a button with it's profile photo and a dropdown menu with direct access to: My profile, My concerts and My artists.</li>
</ul>

__Mustache variables:__ `is-logged`: determines if the user is logged. In that case, shows the profile personal buttons. Otherwise, displays the Login and Register buttons.

__Use:__ To include the header into an HTML page, it should be injected using `{{>header}}` between a `<header></header>` block, located inside the `body` of the file. The styles used for the header are located at the `header-style.css`, be sure to link the file correctly.