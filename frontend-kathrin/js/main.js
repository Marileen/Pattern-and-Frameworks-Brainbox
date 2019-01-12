$(function() {
    console.log("INIT");

    var host = "http://localhost:8050";
    var user = {};
    var clickedLS;


    $(".login").hide();
    $(".registration").hide();

    $(".home").click(function (e) {
        $(".login").hide();
        $(".registration").hide();
        $(".main").show();
        $("#questions").hide();
        console.log("Auf Home geklickt");
        renderCoursesMain();
        $("#courses").show();
        e.preventDefault(e);
    });

    // prevents reload of the page caused by the form elements default behaviour
    $("form").submit(function(e) { // war vorher ohne das .login
        console.log("form submit default behavior prevented by submit callback.");
        e.preventDefault(e);
    });


    // LEARNING STATE


    renderCoursesMain();

    // displays all questions through click on Alle Fragen button
    $("#nav-questions").click(function () {
        if(jwt==null) {
            $(".failureMessage").addClass('alert alert-warning').text("Um diese Inhalte zu sehen musst Du Dich erst einloggen.");

        }else{
            $("#login-b").text("Logout");
        }
        var jwt = user.jsonWebToken;
        renderQuestions();

    });


    // COURSES
    // rendering of the courses on home
    function renderCoursesMain () {
        var coursesElement = $("#courses");
        coursesElement.html("");

        $.ajax({
            method: 'GET',
            url: host + "/courses",
            success: function (data) {


                $.each(data, function (index, element) {
                    console.log(element, $("#courseID" + element.courseID));

                    // TODO: Bild aus DB holen - siehe Bsp-Code Ehlers
                    /* Load product image
                    $.ajax({
                        url: host + "/products/" + product.id,
                        dataType: 'binary',
                        headers: {"accept": "application/octet-stream"},
                        success: function(data) {
                            product.img = data;
                            $('#img-' + product.id).html($("<img>", {src: window.URL.createObjectURL(data)}));
                        }
                    }); */

                    // ggf. noch in Funktion auslagern
                    // hier wird die CourseCard zs.gebaut, die danach zs. mit dem Course in der
                    // fetchTopics verwurstet wird
                    var courseCard =
                        $('<div class="col scale"/>').append(
                            $('<div class="card scale" />')
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
                                        $('<p/>',
                                            {class: "card-text"}).text("Hier überprüfst Du Dein Wissen im Bereich "+element.courseName)
                                            .append(
                                                $('<button/>',
                                                    {
                                                        class: "show-topics btn btn-primary standard-button",
                                                        type: "button",
                                                        'data-toggle':"collapse",
                                                        'data-target': '#topicsForCourse'+element.courseID,
                                                        'aria-expanded':"false",
                                                        'aria-controls':"collapseExample"
                                                    })
                                                    .text("Themen anzeigen"))
                                            .append(
                                                $('<div/>',
                                                    {class: "topic-list collapse", id: 'topicsForCourse'+element.courseID}).append(
                                                    $('<div class="card card-body"/>'
                                                    ))
                                            )
                                    )
                                ) // end card-body
                        ); // end col

                    coursesElement.append(courseCard); // end of all appends

                    fetchTopics(courseCard, element.courseName);

                });
            }
        }); // Ende ajax
    } // end function renderCoursesMain

    // TOPICS
        // fetches topics for a certain course
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

        /* funktioniert nicht --> ggf. ersetzen durch Hinweis auf Login
        $(".topic-list > .card-body").click(function(){
            // tests if jwt is available
            if(jwt==null) {
                $(".login").show();
                console.log("weiterleitung müsste hier stattfinden");
            }else{
                renderQuestions();
            }
        }); */


    // QUESTIONS

    // FUNKTIONIERT NOCH NICHT
    // fetches questions for a certain course
    function fetchQuestions (questionBox, courseName) {
        $.ajax({
            type: 'GET',
            url: host + "/questions/" +courseName,
            success: function (data) {

                $.each(data, function (index, element) {
                    console.log("fragen: " + element.question);
                    questionBox.find('.question-body').append($('<p/>').text(element.topicName));
                    // window.alert(element.topicName);
                });
            }
        });
    }


    // Rendering questions
    function renderQuestions() {
        // ajax request with JWT
        $("#courses").hide();

        var jwt = user.jsonWebToken;
        console.log("neue Abfrage ueber renderQuestions");

        // tests if jwt is available
        if(jwt==null) {
            $(".failureMessage").text("Um diese Inhalte zu sehen musst Du Dich erst einloggen.");
        }else{
            $("#login-b").text("Logout");
        }


        $.ajax({
            method: 'GET',
            beforeSend : function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Authorization", "Bearer "+jwt);
            },

            url: host + "/questions", // holt erstmal alle Questions
            success: function (data) {
                var testQuestionsElement = $("#questions"); // Aequivalent zu coursesElement
                console.log("xhr-Request laeuft");

                $.each(data, function (index, element) {
                    console.log("each "+element.questionID);


                    var questionBox = // Aequivalent zu courseCard
                        $('<div class="row" id="testQuestions"/>').append($('<div class="col accordion" id="questionsAccordion"/>') // end div #accordionExample
                                .append($('<div class="card"/>')
                                    .append(
                                        $('<div/>',
                                        {
                                            id: "question-box"+element.questionID, // bootstrap: headingOne

                                            class: "card-header"
                                        })
                                        .append($('<h2 class="mb-0"/>')
                                            .append($('<button/>',
                                                {
                                                    class: "btn btn-link col collapsed",
                                                    type: "button",
                                                    'data-toggle':"collapse",
                                                    'data-target':"#collapse"+element.questionID,
                                                    'aria-expanded': "true",
                                                    'aria-controls':"collapse"+element.questionID
                                                })
                                                .append($('<div class="ls-icon" style="width: 18rem;"/>')
                                                    .append($('<img src="img/icon_3.jpg"/>')
                                                        .append($('<p>noch nicht</p>'))
                                                    )).append($('<h3>neues Patterns and Frameworks</h3>'))
                                                .append($('<h5>Thema: JWT</h5>'))
                                                .append($('<p/>',
                                                    {
                                                    class: "question-body"
                                                    })
                                                    .html(element.question)
                                                )
                                            ) // end button                                        ) // end append h2
                                    )) // end div card-header
                                    .append($('<div/>',
                                        {
                                            id: "collapse"+element.questionID,
                                            class: "collapse",
                                            'aria-labelledby': "question-box"+element.questionID,
                                            'data-parent': "#questionsAccordion"
                                        })
                                        .append($('<div/>',
                                            {
                                                class: "card-body"
                                            })
                                                .html(element.answer))
                                                .append($('<div name="ev-b" class="btn-group ls-icon ev-b" role="group" aria-label="Basic example"/>')
                                                    .append($('<button name="ls1-button" type="button" class="btn btn-secondary standard-button ls1-button">kann ich</button>')
                                                        .click(function() {
                                                            console.log("Clicked on left button of question", element.questionID, "element: ", this);
                                                        })
                                                    )
                                                    .append($('<button name="ls2-button" type="button" class="btn btn-secondary standard-button ls2-button">geht so</button>').click(function() {
                                                        console.log("Clicked on middle button of question", element.questionID, "element: ", this);

                                                    }))
                                                    .append($('<button name="ls3-button" type="button" class="btn btn-secondary standard-button ls3-button">noch nicht</button>').click(function() {
                                                        console.log("Clicked on right button of question", element.questionID, "element: ", this);

                                                     }))
                                                )
                                                /* .click(function () {
                                                    var clicked = $( this ).attr('name');
                                                    console.log("geklickt wurde: "+ clicked);
                                                 }) */

                                                .append($('<p id="evaluation-text">Aktueller Lernstatus</p>'))
                                    ) // end append collapseOne
                                     // end appends #question-box
                                ) // end appends card
                        ) // end appends accordion



                    testQuestionsElement.append(questionBox);

                    //console.log("genau diese Anfrage funktioniert nicht:");
                   // fetchQuestions(questionBox, element.topic_topicID);

                }) // end each
            } // end success function
        })// ende ajax
    } // end function renderQuestions


    // TOPICS
        // gerade nicht genutzt
    // für was wollte ich das nochmal?
    // könnte man nehmen um alle, alle Themen anzeigen zu lassen
    $(".show-topics").click(function () { // wenn ich auf knopf Alle Themen klicke, dann
        $(this).text("dieses Element wurde geklickt");
        // von diesem geklickten Element brauche ich aus dem h5 die courseID
        var id = $(this).attr('id');
        console.log("Id des Elements:"+id);

        $.ajax({
            type: 'GET',
            url: host + "/topics/",
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

    // LOGIN AND REGISTRATION
    // Registrierung
    $('#register-button').click( function () {
        $(".login").hide();
        $(".registration").fadeTo(400, 1);
        $(".main").hide();


    });

    /*$( "#clickme" ).click(function() {
      $( "#book" ).fadeOut( "slow", function() {
        // Animation complete.
      });
    }); */

    // shows Login form
    $('#login-b').click( function() {
      $(".login").show();
      $(".main").hide();
        $(".failureMessage").hide();
    });


    // Login
    $('#login-button').click( function() {

       // var json = {"name": $('#username').val(), "password": b64_sha256($('#password').val())+"="};
        var json = {
            "email": $('#email').val(),
            "password": b64_sha256($('#password').val())+"="   // gehashed mit b64_sha256
            // "password": $('#password').val()
        };
        var pwVal = $('#password').val();

        if (json.email.length==0 && pwVal==0) {
            var message = '<div class="alert alert-warning" role="alert">'+ "Bitte fülle beide Felder aus."+ '</div>';
            loginFailureMessage(message);

        }else if (json.email.length==0) {
            var message = '<div class="alert alert-warning" role="alert">'+ "Bitte trage eine gültige Mailadresse ein."+ '</div>';
            loginFailureMessage(message);

        }else if (pwVal.length==0) {
            var message = '<div class="alert alert-warning" role="alert">'+ "Bitte trage ein Passwort ein."+ '</div>';
            loginFailureMessage(message);

        }else{
            console.log("das ist das json mit den Benutzernamen: "+ JSON.stringify(json));


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

                    loginSuccessful();
                },
                error: function() {
                  console.log("login error", arguments);
                  $('.login page').hide();

                }
            }); // end ajax request
        }
    });

    function loginFailureMessage(message) {
        $(".login .failureMessage").fadeTo(100, 1, function(){ $(this).html(message).fadeOut(4000); });
    }

    function registrationFailureMessage(message) {
        $(".registration .failureMessage").fadeTo(100, 1, function(){ $(this).html(message).fadeOut(4000); });
    }


    function loginSuccessful() {
        var loginMessage = '<div class="alert alert-info" role="alert">'+ "Du hast Dich erfolgreich eingeloggt."+ '</div>';

        $(".main .failureMessage").fadeTo(100, 1, function(){ $(this).html(loginMessage).fadeOut(4000); });
        $("#login-b").text("Logout");
        $(".login").hide();
        $(".main").show();
        $("#courses").show();
    }

    function registrationSuccessful() {
        var registrationMessage = '<div class="alert alert-info" role="alert">'+ "Du hast Dich erfolgreich registriert."+ '</div>';

        $(".main .failureMessage").fadeTo(100, 1, function(){ $(this).html(registrationMessage).fadeOut(4000); });
        $("#login-b").text("Logout");
        $(".registration").hide();
        $(".main").show();
        renderCoursesMain();
    }

    // Registration
    $('#initial-registr-button').click( function() {
        $(".failureMessage").hide();

        var mail= $("#reg-email").val();
        var fn = $('#firstname').val();
        var ln = $('#lastname').val();
        var pw = b64_sha256($('#reg-pw').val())+"="; //$('#reg-pw').val();

        // var regJson = $('.registration form').serializeArray();
        var regJson = {
            "email": mail,
            "firstname" : fn,
            "lastname" : ln,
            "password" : pw
        };
        // ToDo: Felder validieren wie bei Login

        console.log("das ist das json mit dem Benutzernamen: "+ JSON.stringify(regJson));

        $.ajax({
            url: host + "/user/register", //http://localhost:8050/user/register
            method: "POST",
            crossDomain: true,
            data: JSON.stringify(regJson),
            contentType: "application/json",
            success: function(data) {
                // ToDo: warum funktioniert das hier nicht?
                console.log("Registrierung erfolgreich");
                if (typeof data == 'undefined') {
                    $('.registration .failureMessage').html("Registrierung fehlgeschlagen!");
                    return;
                }
                user = data;
                var jwt = user.jsonWebToken;
                console.log("Das JWT dieses Users lautet: "+jwt);
                //window.alert(("Sie haben sich erfolgreich registriert."))
                registrationSuccessful();
            },
            error: function() {
                console.log("registration error", arguments);
            }
        });

    });

    /* noch irgendwo einbauen?
    $("#testQuestions").hover(
        function(){$("#testQuestions").animate ({color: blue, opacity: 0.6}, 1000);},
        function () {$("#testQuestions").show("scale", {percent: 200, direction: 'vertical' }, 2000)}
    ); */

    //
    $(".card").hover( function (e) {
        $(this).toggleClass('card', e.type === 'mouseenter');
    });



}); // Ende jQuery



