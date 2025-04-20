let nextPage = 0;
const size = 10;

async function loadConcerts() {
    try {
        let result;
        if (context === "main") {
            result = await fetch(`/api/concerts/near-user?country=${country}&page=${nextPage}&size=${size}`);
        } else if (context === "admin") {
            result = await fetch(`/api/concerts/?page=${nextPage}&size=${size}`);
        } else {
            // context will have the id of the artist whose concerts will be shown
            result = await fetch(`/api/concerts/artists/${context}?page=${nextPage}&size=${size}`); 
        }
        const data = await result.json();

        if (data.content.length === 0 && nextPage === 0) {
            display.innerHTML = "<p>There are no concerts soon</p>";
            loadButton.style.display = "none";
            return;
        }

        if (context === "admin") {
            insertConcertsAdmin(data.content);
        } else {
            insertConcerts(data.content);
        }
        // They are the rest of the data (there are no more)
        if (data.content.length < size) {
            loadButton.style.display = "none";
        }
        nextPage++;

    } catch (error) {
        console.log("There has been an error loading the concerts: ", error);
    }
}

function insertConcerts(concerts) {
    for (let i = 0; i < concerts.length; i++) {
        const template = `
        <div class="concert-container">
            <div class="poster-container">
                <img src="/concert/${concerts[i].id}/download-poster" class="poster-photo" title="${concerts[i].name} - ${concerts[i].artist.name}">
            </div>
            <div class="date-container">
                <p>${concerts[i].place} <br> ${concerts[i].date} </p>
            </div>
            <form action="/concert/${concerts[i].id}" method="get">
                <input type="submit" value="Buy Tickets">
            </form>
        </div>
        `;
        display.innerHTML += template;
    };
}

function insertConcertsAdmin(concerts) {
    for (let i = 0; i < concerts.length; i++) {
        const template = `
        <div class="concert-container">
            <div class="poster-container">
                <img src="/concert/${concerts[i].id}/download-poster" class="poster-photo" title="${concerts[i].name} - ${concerts[i].artist.name}">
            </div>
            <div class="date-container">
                <p>${concerts[i].place} <br> ${concerts[i].date} </p>
            </div>
            <form action="/admin/concert/${concerts[i].id}/modify" method="get">
                <input type="submit" value="Modify Concert">
                <input type="hidden" name="_csrf" value="{{token}}"/>
            </form>
        </div>
        `;
        display.innerHTML += template;
    };
}

let display = document.getElementById("concert-display");
let loadButton = document.getElementById("load-more");
let country = document.getElementById("country").value;
let context = document.getElementById("context").value;

//Add event listener to the load button
loadButton.addEventListener("click", loadConcerts);

//Add the first page to the display when the page loads
window.addEventListener("load", loadConcerts);
