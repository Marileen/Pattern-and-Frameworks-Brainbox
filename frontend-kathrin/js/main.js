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

    /* Kurse anzeigen
    $.ajax({
        type: 'GET',
        url: host + "/courses",
        success: function (data) {
            $(".card-title").html(data[0].courseName);
        }
    });
    */

    // nimmt Kursnamen aus Array
// hier weiter machen: loopt durch und schreibt 3. Kurs = Computergrafik rein

        $.ajax({
            type: 'GET',
            url: host + "/courses",
            success: function (data) {
                var coursesElement = $("#courses");

                $.each(data, function (index, element) {
                    // window.alert(element.courseName);
                    console.log(element, $("#courseID" + element.courseID));


                    // den ganzen Spass mit .html statt mit append machen?

                    coursesElement.append(
                        $('<div class="col"/>').append(
                            $('<div class="card" style="width: 18rem;"/>')
                                .append($('<img>', {
                                    class: "card-img-top",
                                    alt: "Card image cap",
                                    src: "img/card_" + element.courseID + ".jpg",
                                }))
                                .append($('<div class="card-body"/>')
                                        .append(
                                            $('<h5 class="card-title show-courses"/>')
                                                .text(element.courseName))
                                        .append(
                                            $('<p class="card-text">Hier überprüfst Du Dein Wissen zu aktuellen Patterns und Frameworks.</p>').append(
                                                $('<a id="c5-t8" class="show-topics btn btn-primary">Themen anzeigen</a>')
                                            )
                                        )
                                    )
                              //  )
                            )
                        ); // ende der appends
                    $(".collapse").click(function () {

                        $(this).text("wurde geklickt");
                        // von diesem geklickten Element brauche ich aus dem h5 die courseID
                        var id = element.courseID;
                        console.log("Id des Elements:"+id);
                        }

                    )
                });

                $.each(data, function (index, element) {
                        // window.alert(element.courseName);
                       // console.log(element, $("#courseID" + element.courseID));
                        $("#courseID" + element.courseID).text(element.courseName);

                    });
                    //  window.alert(data);
            }
        });
                // id="#courseID3"

               /*
                $(".card-title").html(
                    $.each(data, function (index, element) {
                        $("#courseID"+element.courseID).html(element.courseName);
                    }))                 */

/*
    $("#cog-t-button").click(function () {
        $.ajax({
            type: 'GET',
            url: host + "/topics/computergrafik",
            success: function (data) {
                $("#cog-t1").html(data[0].topicName);
            }
        });
    });
*/

// gerade nicht genutzt
    $(".show-topics").click(function () { // wenn ich auf knopf themen anzeigen klicke, dann
        $(this).text("dieses Element wurde geklickt");
        // von diesem geklickten Element brauche ich aus dem h5 die courseID
        var id = $(this).attr('id');
        console.log("Id des Elements:"+id);

        $.ajax({
            type: 'GET',
            url: host + "/topics/Patterns and Frameworks",
            success: function (data) {
                // window.alert(data);
               // $("#topics-paf").html(data[0].topicName);
                $.each(data, function (index, element) {
                    $("courseID5-topic1").text(element.topicName);
                    }
                    
                )
            }
        });
    });



/*
    $(".show-topics").click(function () { // wenn man auf den Themen anzeigen-Knopf klickt, dann...
        $.ajax({
            type: 'GET',
            url: host + "/topics/DB",
            success: function (data) {
                // window.alert(data);
               // $(".topics-list").html(data[1].topicName);
                var cardRow = 1;
                $.each(data, function (index, element) {
                        $("#cog-t"+cardRow).html(element.topicName);
                        cardRow++;
                    }

                )
            }
        });
    });
    */

    // vorläufiger Login
    $('#login-button').on('click tap', function() {
        $(".main").toggle();
        $(".login").hide();
        $(".logout").show();
    });


    // Login
    $('#login-button').on('click tap', function() {
       // var json = {"name": $('#username').val(), "password": b64_sha256($('#password').val())+"="};
        var json = {
            "email": "marileen.stamer@stud.fh-luebeck.de",
            "password":"123"
        };
        $.ajax({
            url: host + "/user/login", //localhost:8050/user/login
            method: "POST",
            data: JSON.stringify(json),
            contentType: "application/json",
            success: function(data) {
                if (typeof data == 'undefined') {
                    $('.failureMessage').html("Login fehlgeschlagen!");
                    return;
                }
                user = data;

                //$('.login page').hide();
                //$('.main').show();
                //alert("login geklappt");
            }
        });
    });

});
