let nextPageGeneral = 0;
let nextPageUser = 0;
const size = 10;

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

async function loadConcertsGeneral() {
    loadGeneral.style.display = "flex";
    await new Promise(resolve => setTimeout(resolve, 1000));
    try {
        let result = await fetch(`/api/concerts/?page=${nextPageGeneral}&size=${size}`);
        const data = await result.json();

        if (data.content.length === 0 && nextPageGeneral === 0) {
            displayGeneral.innerHTML = "<h1>No concert found.</h1>";
            loadButtonGeneral.style.display = "none";
            loadGeneral.style.display = "none";
            return;
        }
        insertConcerts(data.content,1);

        // They are the rest of the data (there are no more)
        if (data.content.length < size) {
            loadButtonGeneral.style.display = "none";

        }
        nextPageGeneral++;

    } catch (error) {
        console.log("There has been an error loading the concerts: ", error);
    }
    loadGeneral.style.display = "none";
}

async function loadConcertsUser() {
    await new Promise(resolve => setTimeout(resolve, 1000));
    loadUser.style.display = "flex";
    try {
        let result = await fetch(`/api/concerts/near-user?country=${country}&page=${nextPageUser}&size=${size}`);
        const data = await result.json();

        if (data.content.length === 0 && nextPageUser === 0) {
            displayUser.innerHTML = "<h1>No concert found.</h1>";
            loadButtonUser.style.display = "none";
            loadUser.style.display = "none";
            return;
        }
        insertConcerts(data.content,2);

        // They are the rest of the data (there are no more)
        if (data.content.length < size) {
            loadButtonUser.style.display = "none";
        }
        nextPageUser++;

    } catch (error) {
        console.log("There has been an error loading the concerts: ", error);
    }
    loadUser.style.display = "none";
}

function insertConcerts(concerts,mode) {
    // Variables to calculate each label id
    let offset;
    let times;
    if (mode === 1){
        offset = size * nextPageGeneral;
        times = 1;
    } else {
        offset = size * nextPageUser + 1; //it starts on 1 (-1), leaving 0 to the General Concerts
        times = -1;
    }

    for (let i = 0; i < concerts.length; i++) {
        let date = new Date(concerts[i].date);
        const template = `
        <div class="concert-container">
            <div class="main-container">
                <div class="concert-photo-container">
                    <img src="/concert/${concerts[i].id}/download-poster" class="image-in-container" style="object-fit: contain;">
                </div>
                <div class="horizontal-separator"></div>
                <div class="concert-date-container">
                    <div class="date-day-container">
                        <p>${date.getDate()}</p> 
                    </div>
                    <div class="date-month-container">
                        <p>${months[date.getMonth()]}</p>
                    </div>
                    <div class="date-year-container">
                        <p>${date.getFullYear()}</p>
                    </div>
                </div>
                <div class="horizontal-separator"></div>
                <div class="concert-info-container">
                    <div class="artist-name">
                        <p>${concerts[i].artist.name}</p> 
                    </div>
                    <div class="concert-info">
                        ${concerts[i].place} &middot; ${concerts[i].formattedTime}
                        <br>
                        ${concerts[i].name}
                    </div>
                </div>
                <div class="horizontal-separator"></div>
                <div class="buttons-container">
                    <button class="concert-button" onclick="window.location.href='/concert/${concerts[i].id}'">Get Tickets</button>
                    <button class="concert-button" onclick="window.location.href='/artist/${concerts[i].artist.id}'">Artist Page</button>
                    <label for="more-info-${(offset + i)*times}" class="concert-button">More info</label>
                </div>
            </div>
            <input type="checkbox" id="more-info-${(offset + i)*times}" class="more-info-toggle">
            <div class="more-info-container">
                <p>${concerts[i].info}</p>
            </div>
        </div>
        `;
        if (mode === 1){
            displayGeneral.innerHTML += template;
        } else {
            displayUser.innerHTML += template;
        }
    };
}

// General elements
let displayGeneral = document.getElementById("general-concert-container");
let loadButtonGeneral = document.getElementById("load-more-2");
let loadGeneral = document.getElementById("load-container-general");
loadButtonGeneral.addEventListener("click", loadConcertsGeneral);
window.addEventListener("load", loadConcertsGeneral);

// User elements
let logged = document.getElementById("logged").value;
let displayUser;
let loadButtonUser;
let loadUser;
let country;
if (logged === "true"){
    displayUser = document.getElementById("personal-concert-container");
    loadButtonUser = document.getElementById("load-more-1");
    loadUser = document.getElementById("load-container-user");
    country = document.getElementById("country").value;
    loadButtonUser.addEventListener("click",loadConcertsUser);
    window.addEventListener("load", loadConcertsUser);
}
