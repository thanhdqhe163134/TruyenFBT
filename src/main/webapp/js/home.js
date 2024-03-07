function searchBooks1() {
    let query = document.getElementById('search-bar').value;
    let resultsContainer = document.querySelector('.search-results2');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'search?query=' + encodeURIComponent(query), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            resultsContainer.style.display = "block";
            resultsContainer.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}



