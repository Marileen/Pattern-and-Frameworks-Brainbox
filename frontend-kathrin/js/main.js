$(function() {
    console.log("INIT");

    var host = "http://localhost:8050";
    var user = {};



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

    $(".login").hide();
    $(".registration").hide();
    $("form").submit(function(e) {
        console.log("form submit default behavior prevented by submit callback.");
        e.preventDefault(e);
    });

        $.ajax({
            method: 'GET',
            url: host + "/courses",
            success: function (data) {
                var coursesElement = $("#courses");

                $.each(data, function (index, element) {
                    // window.alert(element.courseName);
                    console.log(element, $("#courseID" + element.courseID));

                    // in Funktion auslagern
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
                                            // text mit fetchTopics ersetzen
                                            var topics = fetchTopics(element.courseName);

                                            $.each(topics, function (index, element) {
                                                    $("card-body").text(element.topicName);
                                            });
                                            */
                                        )
                                )
                            ) // ende card-body
                    ); // ende col

                    coursesElement.append(courseCard); // ende der appends

                    fetchTopics(courseCard, element.courseName);

                });
            }
        }); // Ende ajax


    // fetches topics
    function fetchTopics (courseCard, courseName) {
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


    // fetches all questions of all courses - jwt protected
    function fetchQuestions () {
        console.log("JWT ist: "+user.jsonWebToken);
        // Versuch mit vorgegebenem jwt
        var jwt = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hcmlsZWVuLnN0YW1lckBzdHVkLmZoLWx1ZWJlY2suZGUiLCJ1c2VySUQiOjEsImZpcnN0bmFtZSI6Ik1hcmlsZWVuIiwibGFzdG5hbWUiOiJTdGFtZXIiLCJwYXNzd29yZCI6IjEyMyIsImFkbWluIjp0cnVlfQ.6l0Zc-Dt5SL6lOg5IseJwh4r_7BNErzTa6NRMINLQd4";
        $.ajax({
                method: 'GET',
                beforeSend : function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                    xhr.setRequestHeader("Authorization", "Bearer "+jwt);
                },
                url: host + "/questions/", // Bsp. questions/BWL
                success: function (data) {

                    var questionsElement = $("#questions");
                    console.log("Daten erfolgreich abgeholt");
                    $("#questions").html(data[0].question);
                    $("#questions").html(data[0].answer);
                    /*
                    $.each(data, function (index, element) {
                        // window.alert(element.courseName);
                        console.log(element, $("#questionID: " + element.questionID));
                    }); */

                }
        })
    }


    // displays all questions
    $("#nav-questions").click(function() {
        //$("#questions").text("neu");
        fetchQuestions();
    });


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

    // Registrierung
    $('#register-button').click( function () {
        $(".login").hide();
        $(".registration").fadeTo(400, 1);
    });

    /*$( "#clickme" ).click(function() {
      $( "#book" ).fadeOut( "slow", function() {
        // Animation complete.
      });
    }); */

    // shows Login form
    $('#login-b').click( function() {
      $(".login").show();
      // $(".main").hide();
      // window.alert("funktioniert");
      // $(".footer").hide();
    });

    // TODO
    // retrieving user credentials from form




    // Login


    $('#login-button').click( function() {
       // var json = {"name": $('#username').val(), "password": b64_sha256($('#password').val())+"="};
       // TODO: aus Form holen
        var json = {
            email : "marileen.stamer@stud.fh-luebeck.de",
            password :"123"
        };

        $.ajax({
            url: host + "/user/login", //localhost:8050/user/login
            method: "POST",
            crossDomain: true,
            data: JSON.stringify(json),
            contentType: "application/json",
            success: function(data) {
                console.log("login erfolgreich");
                if (typeof data == 'undefined') {
                    $('.login .failureMessage').html("Login fehlgeschlagen!");
                    return;
                }
                user = data;
                var jwt = user.jsonWebToken;
                console.log("Das JWT dieses Users lautet: "+jwt);

                //$('.login page').hide();
                //$('.main').show();
                //alert("login geklappt");
            },
            error: function() {
              console.log("login error", arguments);
            }
        });
    });

    $("#home").click(function () {
        $(".login").hide();
        $(".registration").hide();
        console.log("auf Startseite geklickt");
    })


}); // Ende jQuery


