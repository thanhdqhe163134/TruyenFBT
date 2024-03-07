function searchAccounts() {
    let query = document.getElementById('search').value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'searchAccounts?query=' + query, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementsByClassName('list')[0].innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}