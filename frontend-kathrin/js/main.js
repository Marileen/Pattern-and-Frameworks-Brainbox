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

        $.ajax({
            type: 'GET',
            url: host + "/courses",
            success: function (data) {
                var coursesElement = $("#courses");

                $.each(data, function (index, element) {
                    // window.alert(element.courseName);
                    console.log(element, $("#courseID" + element.courseID));


                    var courseCard = $('<div class="col"/>').append(
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
                                    $('<p class="card-text">Hier überprüfst Du Dein Wissen zu aktuellen Patterns und Frameworks.</p>')
                                 .append(
                                     $('<button/>',
                                            {
                                                class: "show-topics btn btn-primary",
                                                type: "button",
                                                'data-toggle':"collapse",
                                                'data-target': '#topicsForCourse'+element.courseID,
                                                'aria-expanded':"false",
                                                'aria-controls':"collapseExample"})
                                            .text("Themen anzeigen"))
                                        .append(
                                            $('<div/>',
                                                {class: "topic-list collapse", id: 'topicsForCourse'+element.courseID}).append(
                                                $('<div class="card card-body"/>'
                                            ))

                                            /*
                                            // text mit getTopics ersetzen
                                            var topics = getTopics(element.courseName);

                                            $.each(topics, function (index, element) {
                                                    $("card-body").text(element.topicName);
                                            });
                                            */
                                        )
                                )
                            ) // ende card-body
                    ); // ende col

                    getTopics(courseCard, element.courseName);
                    coursesElement.append(courseCard); // ende der appends

                });

                $.each(data, function (index, element) {
                        // window.alert(element.courseName);
                       // console.log(element, $("#courseID" + element.courseID));
                        $("#courseID" + element.courseID).text(element.courseName);

                    });
                    //  window.alert(data);
            }
        }); // Ende ajax


    // holt topics
    function getTopics (courseCard, courseName) {
         $.ajax({
                type: 'GET',
                url: host + "/topics/" +courseName,
                success: function (data) {

                        $.each(data, function (index, element) {
                            console.log("elemente: " + element.topicName);
                            courseCard.find('.topic-list > .card-body').append($('<p/>').text(element.topicName));
                            // window.alert(element.topicName);
                        });


                }
            });
    }



    $(".collapse").click(function () {

            $(this).text("wurde geklickt");
            // von diesem geklickten Element brauche ich aus dem h5 die courseID
            var id = element.courseID;
            console.log("Id des Elements:"+id);
        }

    )


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


}); // Ende jQuery
