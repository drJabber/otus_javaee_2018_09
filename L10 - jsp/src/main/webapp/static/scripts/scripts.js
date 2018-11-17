Date.prototype.toIsoString = function() {
    var tzo = -this.getTimezoneOffset(),
        dif = tzo >= 0 ? '+' : '-',
        pad = function(num) {
            var norm = Math.floor(Math.abs(num));
            return (norm < 10 ? '0' : '') + norm;
        };
    return this.getFullYear() +
        '-' + pad(this.getMonth() + 1) +
        '-' + pad(this.getDate()) +
        'T' + pad(this.getHours()) +
        ':' + pad(this.getMinutes()) +
        ':' + pad(this.getSeconds()) +
        dif + pad(tzo / 60) +
        ':' + pad(tzo % 60);
};


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
    jQuery.get("/cbcurr", function(data){
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

var getNews=function(){
    jQuery.get("/news", function(data){
        var items = [];
        jQuery.each(data, function(key, val){
            items.push("<tr>");
            items.push("<td id =''"+key+"''>");
            items.push("<a href='"+val.link+"'>"+val.text+"</a>");
            items.push("</td>");
            items.push("</tr>");
        });
        $("<table/>",{html: items.join("")}).appendTo("#aside-news-table");
    });
}

var load_stats=function () {
    $.ajax({
        url: "/stats/report",
        success: function (data) {
            $('#main-stats-body').html(data);
        },
        error: function (xhr, err) {
            $('#main-stats-body').html(xhr.responseText);

        }
    });
}

var focusListener = function(event) {
    event.target.style.background = "#F9F0DA";
};
var blurListener = function(event) {
    event.target.style.background = "";
    checkLoginForm();
};

var AjaxContent = function(){
    return {
        getContent : function(method, url){
            $.ajax({
                url: url,
                method:method,
                headers:{"x-rnk-client-time-header":(new Date()).toIsoString()},
                success: function (data) {
                    $(body).html(data);
                },
                error: function (err) {
                    alert("fail");
                    
                }
            })
        },
        ajaxify_links: function(elements){
            $(elements).click(function(){
                AjaxContent.getContent('get',this.href);
                return false; //prevents the link from beign followed
            });
        }//,
        // ajaxify_forms: function(elements){
        //     $(elements).submit(function(event){
        //         alert(this.action);
        //         event.preventDefault();
        //         AjaxContent.getContent('post',this.action, this.data);
        //         return false; //prevents the link from beign followed
        //     });
        // }
    }
}();
