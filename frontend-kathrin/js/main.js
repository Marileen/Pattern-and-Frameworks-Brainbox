$(function() {

    var host = "http://localhost:8050";
    var user = {};
    var products = new Map();
    var basket = [];

    /*$("#login-button").click(function () {
        $(".container").hide();
    }) */

    /* Switch pane
    $('.nav a').on('click tap', function() {
        $('.nav a').removeClass("active");
        $(this).addClass("active");
        $('.pane').hide();
        var target = $(this).attr('href');
        $(target).show();
    }); */

    // Kurse anzeigen
    $.ajax({
        type: 'GET',
        url: host + "/courses",
        success: function (data) {
            // window.alert(data);
            $(".paf").html(data[0].courseName);
        }
    });

    $("#cog-t-button").click(function () {
        $.ajax({
            type: 'GET',
            url: host + "/topics/computergrafik",
            success: function (data) {
                $("#cog-t1").html(data[0].topicName);
            }
        });
    });



    $(".paf").click(function () { // wenn ich auf paf klicke, dann
        $.ajax({
            type: 'GET',
            url: host + "/topics/Patterns and Frameworks",
            success: function (data) {
                // window.alert(data);
                $("#topics-paf").html(data[0].topicName);
            }
        });
    })

    $.ajax({
        type: 'GET',
        url: host + "/courses",
        success: function (data) {
            // window.alert(data);
            $(".db").html(data[1].courseName);
        }
    });



    // Login
    $('#login-button').on('click tap', function() {
        var json = {"name": $('#username').val(), "password": b64_sha256($('#password').val())+"="};
        $.ajax({
            url: host + "/user/login",
            method: "POST",
            data: JSON.stringify(json),
            contentType: "application/json",
            success: function(data) {
                if (typeof data == 'undefined') {
                    $('.failureMessage').html("Login fehlgeschlagen!");
                    return;
                }
                user = data;

                $('.login page').hide();
                $('.main').show();
                alert("login geklappt");
            }
        });
    });

});
