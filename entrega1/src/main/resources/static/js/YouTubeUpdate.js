/**
 * The YouTubeUpdate.js script is incharged of the handle and update of live-working YouTube iframes
 * using embed YouTube links.
 * 
 * Its main function is to provide the admins the possibility to visualize the content they have inserted inside the links of an artist. When an URI
 * is typed, this script changes the URI to an embed one and displays its visualizator.
 */

let videoPreview = document.getElementById("video-display"); //We get the display
let videoInput = document.getElementById("videoLink"); //We get the link input
let videoPreviewContainer = document.getElementById("video-display-container")

videoInput.addEventListener("input", function () { /*When an input is heard*/
    let videoId = extractYouTubeID(videoInput.value); /*Using lambda expressions and pattern matching we get the embed link*/

    if (videoId) { /*If it exists an embed link, it is displayed*/
        videoPreview.src = `https://www.youtube.com/embed/${videoId}`;
        videoPreviewContainer.style.display = "block";
        videoInput.value = videoPreview.src;
    } else { /*In other case, it is hidden and cleared*/
        videoPreview.src = "";
        videoPreviewContainer.style.display = "none";
    }
});

/*Function used to extract the embed YouTube URL from the input one, using pattern matching and lambda expression*/
function extractYouTubeID(url) {
    let match = url.match(/(?:youtube\.com\/(?:.*[?&]v=|embed\/|v\/)|youtu\.be\/)([^"&?\/\s]{11})/);
    return match ? match[1] : null;
}