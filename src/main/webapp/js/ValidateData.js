function validateEmail() {
    var text = document.getElementById("email").value;
    const validateEmailRegex = /^\S+@\S+\.\S+$/;
    if (!validateEmailRegex.test(text)) {
        document.getElementById("mess").innerHTML = "Invalid email";
        document.getElementById("email").style.borderColor = "#ff0000";
        document.getElementById("email").style.color = "#ff0000";
        document.getElementById("submit").style.cursor = "not-allowed";
        document.getElementById("submit").disabled = true;
    } else {
        document.getElementById("mess").style.display = "none";
        document.getElementById("email").style.borderColor = "#000000";
        document.getElementById("email").style.color = "#000000";
        document.getElementById("submit").style.cursor = "auto";
        document.getElementById("submit").disabled = false;
    }
}
