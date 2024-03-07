function toggleReplyForm(commentId) {
    var form = document.getElementById('reply-form-' + commentId);
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

function toggleEditForm(commentId) {
    var form = document.getElementById('edit-form-' + commentId);
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}
$(document).ready(function() {
    function highlightItems(color) {
        $(".btn-publisher_year, .btn-language").removeClass("selected-item");
        $(".btn-publisher_year, .btn-language").filter(function() {
            return $(this).css("background-color") === color;
        }).addClass("selected-item");
    }

    $(document).on("click", ".btn-publisher_year, .btn-language", function() {
        highlightItems($(this).css("background-color"));
    });

    $(".btn-publisher_year:first, .btn-language:first").click();

    $("#borrowForm").on("submit", function() {
        $(this).find("input[type='hidden']").remove();

        $(".selected-item").each(function() {
            var isbn = $(this).data("isbn");
            $("#borrowForm").append('<input type="hidden" name="ISBN" value="' + isbn + '">');
        });
    });
});



function fetchBookEditions(publisherId, bookId) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("bookEditions").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "bookEdition?publisherId=" + publisherId + "&bookId=" + bookId +  "&action=view", true);
    xhttp.send();
}

function fetchBookEditionsUpdate(publisherId, bookId) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("bookEditions").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "bookEdition?publisherId=" + publisherId + "&bookId=" + bookId + "&action=edit", true);
    xhttp.send();
}

    document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('myModal');
    const addButton = document.getElementById('add-edition-button');
    const closeBtn = document.getElementsByClassName("close")[0];
    const submitButton = document.getElementById('submit-button');

    addButton.addEventListener('click', function () {
    modal.style.display = "block";
});

    closeBtn.addEventListener('click', function () {
    modal.style.display = "none";
});

    submitButton.addEventListener('click', function () {

    modal.style.display = "none";
});

    window.addEventListener('click', function (event) {
    if (event.target == modal) {
    modal.style.display = "none";
}
});
});

$(document).ready(function() {
    // Lắng nghe sự kiện click trên các nút nhà xuất bản
    $(".btn-publisher").on("click", function() {
        // Xóa class 'selected-publisher' khỏi tất cả các nút nhà xuất bản
        $(".btn-publisher").removeClass("selected-publisher");

        // Thêm class 'selected-publisher' vào nút nhà xuất bản đã được nhấn
        $(this).addClass("selected-publisher");
    });
});




