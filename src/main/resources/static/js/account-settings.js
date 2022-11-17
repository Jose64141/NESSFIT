function showInfo()
{
    document.getElementsByClassName("data-buttons")[0].className = "data-buttons";
    document.getElementsByClassName("password-buttons")[0].className = "password-buttons d-none";
}

function showPasswd()
{
    document.getElementsByClassName("data-buttons")[0].className = "data-buttons d-none";
    document.getElementsByClassName("password-buttons")[0].className = "password-buttons";
}
