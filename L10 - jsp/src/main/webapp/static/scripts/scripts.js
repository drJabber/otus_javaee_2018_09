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

// var load_stats=function () {
//     $.ajax({
//         url: "/stats/report",
//         success: function (data) {
//             $('#main-stats-body').html(data);
//         },
//         error: function (xhr, err) {
//             $('#main-stats-body').html(xhr.responseText);
//
//         }
//     });
// }
//

var focusListener = function(event) {
    event.target.style.background = "#F9F0DA";
};
var blurListener = function(event) {
    event.target.style.background = "";
    checkLoginForm();
};

var ws_news;
var ws_currencies;
var ws_stats;
function ws_connect() {
    if (ws_news) {ws_news.close()};
    if (ws_currencies) {ws_currencies.close()};
    if (ws_stats) {ws_stats.close()};

    ws_news = new WebSocket("ws://localhost:8080/news");
    ws_currencies = new WebSocket("ws://localhost:8080/curr");
    ws_news.onmessage = onNewsMessage;
    ws_currencies.onmessage = onCurrenciesMessage;
    if ($('#stats-report-table').length){
        ws_stats=new WebSocket("ws://localhost:8080/stats");
        ws_stats.onmessage=onStatsMessage;
    }else{
        ws_stats=null;
    }

}

window.addEventListener("load", ws_connect, false);


var AjaxContent = function(){
    return {
        getContent : function(method, url){
            $.ajax({
                url: url,
                method:method,
                headers:{"x-rnk-client-time-header":(new Date()).toIsoString()},
                success: function (data) {
                    $(body).html(data);
                    ws_connect();
                },
                error: function (err) {
                    alert("fail");
                    ws_connect();
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


// var getCBCurrencies=function(){
//     jQuery.get("/cbcurr", function(data){
//         var items = [];
//         items.push("<table><tr><th>Валюта</th><th>Курс</th></tr>");
//         jQuery.each(data, function(key, val){
//             items.push("<tr>");
//             items.push("<td id =''"+key+"''>"+val.charCode+"</td>");
//             items.push("<td id =''"+key+"''>"+val.value+"</td>");
//             items.push("</tr>");
//         });
//         items.push("</table>");
//         $("<table/>",{html: items.join("")}).appendTo("#aside-currencies-table");
//     });
// }
//
// var getNews=function(){
//     jQuery.get("/news", function(data){
//         var items = [];
//         jQuery.each(data, function(key, val){
//             items.push("<tr>");
//             items.push("<td id =''"+key+"''>");
//             items.push("<a href='"+val.link+"'>"+val.text+"</a>");
//             items.push("</td>");
//             items.push("</tr>");
//         });
//         $("<table/>",{html: items.join("")}).appendTo("#aside-news-table");
//     });
// }
//

function onStatsMessage(evt) {
    var items = [];
    var index=0;
    jQuery.each(JSON.parse(evt.data), function(key, val){
        var rowclass='odd';
        if (index%2==0) {rowclass = 'even';}
        items.push("<tr class='"+rowclass+"'>");

        items.push("<td class='stats-report-column'>");
        items.push(val.urn);
        items.push("</td>");

        items.push("<td class='stats-report-column'>");
        items.push(val.user);
        items.push("</td>");

        items.push("<td class='stats-report-column'>");
        items.push(val.country);
        items.push("</td>");

        items.push("<td class='stats-report-column'>");
        items.push(val.ip);
        items.push("</td>");

        items.push("<td class='stats-report-column'>");
        items.push(val.searchedFor);
        items.push("</td>");

        items.push("</tr>");

        index+=1;
    });
    $("#stats-report-table tbody" ).empty();
    $("<tbody/>",{html: items.join("")}).appendTo("#stats-report-table");
}


function onNewsMessage(evt) {
    var items = [];
    jQuery.each(JSON.parse(evt.data), function(key, val){
        items.push("<tr>");
        items.push("<td id =''"+key+"''>");
        items.push("<a href='"+val.link+"'>"+val.text+"</a>");
        items.push("</td>");
        items.push("</tr>");
    });
    $("#aside-news-table").empty();
    $("<table/>",{html: items.join("")}).appendTo("#aside-news-table");
}

function onCurrenciesMessage(evt) {
    var items = [];
    items.push("<table><tr><th>Валюта</th><th>Курс</th></tr>");
    jQuery.each(JSON.parse(evt.data), function(key, val){
        items.push("<tr>");
        items.push("<td id =''"+key+"''>"+val.charCode+"</td>");
        items.push("<td id =''"+key+"''>"+val.value+"</td>");
        items.push("</tr>");
    });
    items.push("</table>");
    $("#aside-currencies-table").empty();
    $("<table/>",{html: items.join("")}).appendTo("#aside-currencies-table");
}


function snilsButtonClick(text, element){
    var xml=['<ns2:check xmlns:ns2="urn://rnk.l10.soap"><snils>', text, '</snils></ns2:check>'];
    $.soap({
        url: 'http://localhost:8080/snilschecker',
        namespaceQualifier: 'ns2',
        namespaceURL: 'urn://rnk.l10.soap',
        method:'check',
        async:false,
        // noPrefix:true,
        appendMethodToURL:false,
        params:xml.join(''),
        // params:{'snils':$('#snils-text').val()},
        // data:xml.join(''),
        error: function (soapResponse) {
            alert(soapResponse.httpText);
        },
        success: function(soapResponse){
            element.text(soapResponse.toJSON().Body.checkResponse.result=="true"?"верный СНИЛС":"неверный СНИЛС");
        }
    });
}

function computeSalaryButtonClick(elt_avg, elt_max, elt_fio){
    var data={
        url: 'http://localhost:8080/staffutils',
        namespaceQualifier: 'ns2',
        namespaceURL: 'urn://rnk.l10.soap',
        method:'',
        element:null,
        async:false,
        // noPrefix:true,
        appendMethodToURL:false,
        // params:{'snils':$('#snils-text').val()},
        // data:xml.join(''),
        error: function (soapResponse) {
            alert(soapResponse.httpText);
        },
        success: null
    }

    data.method='getAvgSalary';
    data.element=elt_avg;
    data.success=function(soapResponse){
            element.text(soapResponse.toJSON().Body.getAvgSalaryResponse.result);
        }
    $.soap(data);

    data.method='getMaxSalary';
    data.element=elt_max;
    data.success=function(soapResponse){
            element.text(soapResponse.toJSON().Body.getMaxSalaryResponse.result);
        }
    $.soap(data);

    data.method='getPersonWithMaxSalary';
    data.element=elt_fio;
    data.success=function(soapResponse){
            element.text(soapResponse.toJSON().Body.getPersonWithMaxSalaryResponse.result);
        }
    $.soap(data);
}
