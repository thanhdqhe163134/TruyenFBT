function addGenre() {
    var newGenreList = document.getElementById('newGenreList');
    var newGenre = document.getElementById('newGenre');
    if (newGenre.value.trim() === '') return;

    // Create a new anchor element
    var genreElement = document.createElement('a');
    genreElement.textContent = newGenre.value;

    // Create a new hidden input element
    var hiddenInput = document.createElement('input');
    hiddenInput.type = 'hidden';
    hiddenInput.name = 'newGenres';  // This name should match the parameter you want to retrieve on the server side
    hiddenInput.value = newGenre.value;

    // Append elements
    newGenreList.appendChild(genreElement);
    newGenreList.appendChild(hiddenInput);

    // Clear the input field
    newGenre.value = '';
}


function searchInstantly() {
    let query = document.getElementById('search-title').value;
    let resultsContainer = document.querySelector('.search-results');
    if (query === "") {
        resultsContainer.style.display = "none";
        return;
    }
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'searchBook?query=' + query + "&action=title", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            resultsContainer.style.display = "block";
            resultsContainer.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

function searchOnEnter() {
    let title = document.getElementById('search-title').value;

    let form = document.createElement('form');
    form.method = 'GET';
    form.action = '/library/book';

    let inputTitle = document.createElement('input');
    inputTitle.type = 'hidden';
    inputTitle.name = 'title';
    inputTitle.value = title;

    let inputAction = document.createElement('input');
    inputAction.type = 'hidden';
    inputAction.name = 'action';
    inputAction.value = 'add';

    form.appendChild(inputTitle);
    form.appendChild(inputAction);

    document.body.appendChild(form);

    form.submit();
}



