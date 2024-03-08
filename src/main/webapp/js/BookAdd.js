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


function validateForm() {
    var checkboxes = document.querySelectorAll('input[name="genres"]:checked');
    var newGenreList = document.getElementById('newGenreList');
    var newGenreElements = newGenreList.getElementsByTagName('input');
    if (checkboxes.length == 0 && newGenreElements.length == 0) {
        alert("Please select at least one genre or add a new one.");
        return false;
    }
    return true;
}


