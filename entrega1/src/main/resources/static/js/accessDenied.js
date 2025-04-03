document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const errorCode = urlParams.get('error');
    const errorCodeElement = document.getElementById("error-code");
    const errorMessageElement = document.getElementById("error-message");

    if (errorCode === "404") {
        errorCodeElement.textContent = "404 Error";
        errorMessageElement.textContent = "¡Oops! It seems you are looking for the wrong page.";
    } 
    else if (errorCode === "403") {
        errorCodeElement.textContent = "403 Forbidden";
        errorMessageElement.textContent = "You don't have permission to access this page.";
    } 
    else {
        errorCodeElement.textContent = "Unexpected Error";
        errorMessageElement.textContent = "An unexpected error has occurred.";
    }
});