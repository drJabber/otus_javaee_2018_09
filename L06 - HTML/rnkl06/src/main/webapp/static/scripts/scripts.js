var updateLoginButton= function(){
    if ( ("status" in sessionStorage) && (sessionStorage.getItem("status")==="true")){
        document.getElementById("login-logout-button").innerText="Выход";
        document.getElementById("login-logout-button").href="logout";
    }else
    {
        document.getElementById("login-logout-button").innerText="Вход";
        document.getElementById("login-logout-button").href="admin.html";
    }
}

var checkLoginForm = function() {
    var checked = document.getElementById('login').value != ''
        && document.getElementById('password').value != '';
    if (checked) {
        document.getElementById('submit').removeAttribute('disabled');
    }
    else {
        document.getElementById('submit').setAttribute('disabled', 'disabled')
    }
};

var focusListener = function(event) {
    event.target.style.background = "#F9F0DA";
};
var blurListener = function(event) {
    event.target.style.background = "";
    checkLoginForm();
};
