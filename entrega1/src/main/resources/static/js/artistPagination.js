let page = 0; // Initial page
let totalPages = Infinity;  // Variable to safe the total amount of pages found
let searchTerm = new URLSearchParams(window.location.search).get('search') || '';  // We get the search made (if it exists)

document.addEventListener("DOMContentLoaded", function () {
	const loadMoreButton = document.getElementById('load-more-artists');
	const url = `/api/artists?page=${page}&search=${searchTerm}`;

	//We load the first page of artists
	loadMoreArtists();

	// The "Load More" button is associated to the loadMoreArtists function
	loadMoreButton.addEventListener('click', loadMoreArtists);
});

function loadMoreArtists() {
	const url = `/api/artists?page=${page}&search=${searchTerm}`; // Basic URL to launch JSON queries

	fetch(url)
		//We handle the initial response to find if there was any error
		.then(response => {
			//If the query was no ok (200), we throw an error for it to be handled by the error block
			if (!response.ok) {
				throw new Error(`HTTP Error: ${response.status}`);
			}
			return response.json();
		})
		.then(data => {

			const container = document.getElementById('more-artists');

			/**
			 * Since data of the query of an artist page has the next structure:
			 * {
			 * 	"content": [Array of 10 ArtistDTO]
			 * 	...
			 * 	"totalPages": [Number of total pages]
			 * 	...
			 *  "empty": [Wether the page is empty or not]
			 * }
			 * 
			 * We can use that information to check the actual status and load the artists
			*/

			if (!data.content || data.content.length === 0 || data.empty) { // If there is no data, the "Load More" button is hidden
				document.getElementById('load-more-artists').style.display = 'none';
				//If it was the first page search and there were no artists found, we display a "There are no artists to show" message
				if (page === 0) {
					container.innerHTML += '<p>There are no artists to show</p>';
				}
			}

			// We load the number of remaining pages by its indexes, so that we can map availability
			totalPages = data.totalPages - 1;

			// We insert the artists into the container using their Mustache-evaluable template
			data.content.forEach(artist => {
				const template = `
				<div class="artist-container">
					{{#photo}}
					<div class="artist-photo">
						<img src="/artist/{{id}}/download-photo" class="image-in-container image-rounded">
					</div>
					{{/photo}}
					{{^photo}}
					<div class="artist-photo">
						<img src="/media/no-photo-artist.png" class="image-in-container image-rounded">
					</div>
					{{/photo}}
					<div class="name-container">
						<p>{{name}}</p>
					</div>
					<div class="button-container">
						{{^modifyArtist}} 
						<form action="/artist/{{id}}">
						<input type="submit" value="More Info">
						</form>
						{{/modifyArtist}}
						{{#modifyArtist}} 
						<form action="/admin/artist/{{id}}/modify" method="get">
							<input type="submit" value="Modify">
						</form>
						{{/modifyArtist}}
					</div>
				</div>
				`;

				// We use the mustache render to evaluate variables both from the HTML model and here
				container.innerHTML += Mustache.render(template, {
					id: artist.id,
					name: artist.name,
					photo: artist.photoLink
				});
			});

			// If there are remaining pages to explore, the "Load More" button is shown
			if (page < totalPages) {
				document.getElementById('load-more-artists').style.display = 'block';
			} else { // In other case, the button is hidden
				document.getElementById('load-more-artists').style.display = 'none';
			}

			page++;  // We increase the number of explored pages
		})
		//We show every error by an error log in the console
		.catch(error => {
			console.error('There was an error obtaining the artist page ${page}:', error);
		});
}