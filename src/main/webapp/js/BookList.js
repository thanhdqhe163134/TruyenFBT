function searchBooks() {
    let query = document.getElementById('search-bar').value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'searchBook?query=' + query, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementsByClassName('book-container')[0].innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

document.getElementById('deleteButton').addEventListener('click', function () {
    var bookId = this.getAttribute('data-book-id');
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/books', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            if (xhr.responseText === "success") {
                alert('Book deleted successfully');
            } else {
                alert('Failed to delete book');
            }
        } else if (xhr.readyState == 4) {
            alert('Error occurred while deleting book');
        }
    };
    xhr.send('action=delete&book_id=' + bookId);
});
