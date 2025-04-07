/**
 * The SpotifyUpdate.js script is incharged of the handle and update of live-working Spotify iframes
 * using the official Spotify iFrame API, which can be found: https://developer.spotify.com/documentation/embeds/tutorials/using-the-iframe-api
 * 
 * Its main function is to provide the admins the possibility to visualize the content they have inserted inside the links of an artist. When an URI
 * is typed, this script changes the URI to an embed one and displays its visualizator.
 */

// Best album variables
let bestAlbumUri = "";
let best = document.getElementById("bestAlbumSpotifyLink");
let bestPreview = document.getElementById("bestAlbumSpotifyLink-preview-container");

// Listener to the best album input
best.addEventListener("input", function () {
    let uri = extractSpotifyURI(best.value);
    if (uri) { // If the URL is correct, it updates the iframe and display, calling the SpotifyEmbed updater
        bestAlbumUri = uri;
        updateSpotifyEmbed();
        bestPreview.style.display = "block";
    } else { // If the URL is not correct, it hides the display and re-stablishes the iframe div to the initial value
        bestPreview.style.display = "none";
        bestPreview.innerHTML = '<div id="embed-iframe-best-album"></div>'
    }
});

// Latest album variables
let latestAlbumUri = "";
let latest = document.getElementById("latestAlbumSpotifyLink");
let latestPreview = document.getElementById("latestAlbumSpotifyLink-preview-container");

// Listener to the latest album input
latest.addEventListener("input", function () {
    let uri = extractSpotifyURI(latest.value);
    if (uri) { // If the URL is correct, it updates the iframe and display, calling the SpotifyEmbed updater
        latestAlbumUri = uri;
        updateSpotifyEmbed();
        latestPreview.style.display = "block";
    } else { // If the URL is not correct, it hides the display and re-stablishes the iframe div to the initial value
        latestPreview.style.display = "none";
        latestPreview.innerHTML = '<div id="embed-iframe-latest-album"></div>'
    }
});

/**
 * Function that transforms any Spotify link into an embed one, which can be used by the Spotify iFrame API to load content.
 * In case the link does not match any of the patterns, the url is updated to null.
 * 
 * @param {*} url any Spotify url to be transformed.
 * @returns the Sptify embed link in case everything went OK, null in any other case.
 */
function extractSpotifyURI(url) {
    const regex = /spotify\.com\/(?:intl-[a-z]{2}\/)?(?:embed\/)?(track|album|playlist)\/([a-zA-Z0-9]+)(?:\?|$)/;
    const match = url.match(regex);
    return match ? `spotify:${match[1]}:${match[2]}` : null;
}

/**
 * Function that updates the content of the iFrame by using the official SpotifyIframeApi
 */
function updateSpotifyEmbed() {
    if (window.onSpotifyIframeApiReadyCalled && window.SpotifyIframeApi) {

        const bestEl = document.getElementById("embed-iframe-best-album");
        const latestEl = document.getElementById("embed-iframe-latest-album");

        if (bestAlbumUri && bestEl) { // In case there is a correct best album url and its insertion-iframe exist.
            SpotifyIframeApi.createController(bestEl, {
                uri: bestAlbumUri,
                height: 80,
                width: '100%'
            }, () => { });
        }

        if (latestAlbumUri && latestEl) { // In case there is a correct latest album url and its insertion-iframe exist.
            SpotifyIframeApi.createController(latestEl, {
                uri: latestAlbumUri,
                height: 80,
                width: '100%'
            }, () => { });
        }
    }
}

/**
 * Listener of an event provided by the SpotifyIframeApi to stablish the IframeApi of the window.
 * 
 * @param {*} IFrameAPI 
 */
window.onSpotifyIframeApiReady = (IFrameAPI) => {
    window.SpotifyIframeApi = IFrameAPI;
    window.onSpotifyIframeApiReadyCalled = true;
    updateSpotifyEmbed(); // try loading if fields already have values
};