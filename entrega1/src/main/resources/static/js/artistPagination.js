let page = new URLSearchParams(window.location.search).get('page') || 1;; // Initial page
let totalPages = 0;  // Variable to safe the total amount of pages found
let searchTerm = new URLSearchParams(window.location.search).get('search') || '';  // We get the search made (if it exists)

document.addEventListener("DOMContentLoaded", function () {
	const loadMoreButton = document.getElementById('load-more-artists');
	const url = `/api/artists?page=${page}&search=${searchTerm}`;

	fetch(url)
		.then(response => {
			if (!response.ok) {
				throw new Error(`HTTP Error: ${response.status}`);
			}
			return response.json();
		})
		.then(data => {
			if (!data.content || data.content.length === 0) { // If there are no data, the data button is hidden
				loadMoreButton.style.display = "none"
			} else { // If data exists, the data button is shown
				loadMoreButton.style.display = "block"
			}
		})

	// The "Load More" button is associated to the loadMoreArtists function
	loadMoreButton.addEventListener('click', loadMoreArtists);
});

function loadMoreArtists() {
	const url = `/api/artists?page=${page}&search=${searchTerm}`; // Basic URL to launch JSON queries

	fetch(url)
		.then(response => {
			if (!response.ok) {
				throw new Error(`HTTP Error: ${response.status}`);
			}
			return response.json();
		})
		.then(data => {

			if (!data.content || data.content.length === 0) { // If there is no data, the "Load More" button is hidden
				document.getElementById('load-more-artists').style.display = 'none';
				return;
			}

			// We load the number of remaining pages by its indexes, so that we can map availability
			totalPages = data.totalPages - 1;

			const container = document.getElementById('more-artists');

			// We insert the artists into the container using their template
			/* NOTE: Until the image politics is decided, the photo variable DO NOT WORK. Until then, photo will
			* be evaluated to false and the default photo of new inserted artists will be shown.
			*/
			data.content.forEach(artist => {
				const template = `<div class="artist-container">
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

				container.innerHTML += Mustache.render(template, { // The mustache render evaluate the variables
					id: artist.id,
					name: artist.name,
					photo: artist.mainPhoto
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
}