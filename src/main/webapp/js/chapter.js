window.onscroll = function() {scrollFunction()};
function scrollFunction() {
    var chapterNavigation = document.getElementsByClassName("chapter-navigation")[0];
    var topButton = document.getElementById("top-button");
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        topButton.style.display = "block";
        topButton.style.bottom = "50px";
        chapterNavigation.style.position = "fixed";
        chapterNavigation.style.bottom = "0";
        chapterNavigation.style.width = "100%";
    } else {
        topButton.style.display = "none";
        chapterNavigation.style.position = "static";
    }
}
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

var modal = document.getElementById("myModal");
var btn = document.getElementById("editButton");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function manageImage(action, imageId, fileInput, chapterId) {
    var file = fileInput ? fileInput.files[0] : null;
    var formData = new FormData();
    if (file) {
        formData.append('image', file);
    }
    formData.append('action', action);
    if (imageId) {
        formData.append('id', imageId);
    }
    if (chapterId) {
        formData.append('chapterId', chapterId);
    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'chapter-img', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert(xhr.responseText);
            sessionStorage.setItem('modalOpen', 'true');
            location.reload();
        }
    };
    xhr.send(formData);
}

window.onload = function() {
    var modalOpen = sessionStorage.getItem('modalOpen');
    if (modalOpen === 'true') {
        var modal = document.getElementById("myModal");
        modal.style.display = "block";
        sessionStorage.removeItem('modalOpen'); // Xóa trạng thái modal khỏi sessionStorage
    }
}

document.querySelector('#add-image').addEventListener('click', function() {
    var fileInput = document.querySelector('#new-image-file');
    var chapterId = this.getAttribute('data-chapter-id');
    manageImage('add', null, fileInput, chapterId);
});

document.querySelectorAll('.edit-image').forEach(function(button) {
    button.addEventListener('click', function() {
        var imageId = this.getAttribute('imgId');
        var fileInput = document.querySelector('#edit-image-file-' + imageId);
        manageImage('edit', imageId, fileInput);
    });
});

document.querySelectorAll('.delete-image').forEach(function(button) {
    button.addEventListener('click', function() {
        var imageId = this.getAttribute('imgId')
        manageImage('delete', imageId, null);
    });
});

function toggleReplyForm(commentId) {
    var replyForm = document.getElementById('reply-form-' + commentId);
    if (replyForm.style.display === 'none') {
        replyForm.style.display = 'block';
    } else {
        replyForm.style.display = 'none';
    }
}