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

var getCBCurrencies=function(){
    jQuery.get("cbcurr", function(data){
        var items = [];
        items.push("<table><tr><th>Валюта</th><th>Курс</th></tr>");
        jQuery.each(data, function(key, val){
            items.push("<tr>");
            items.push("<td id =''"+key+"''>"+val.charCode+"</td>");
            items.push("<td id =''"+key+"''>"+val.value+"</td>");
            items.push("</tr>");
        });
        items.push("</table>");
        $("<table/>",{html: items.join("")}).appendTo("#aside-currencies-table");
    });
}
var focusListener = function(event) {
    event.target.style.background = "#F9F0DA";
};
var blurListener = function(event) {
    event.target.style.background = "";
    checkLoginForm();
};
