/**
 *
 * This file includes the main frontend jQuery code
 *
 * @author Kathrin Koehler <kathrin.koehler@stud.th-luebeck.de>
 *
 */

$(function() {

    // console.log("INIT");

    var host = "http://localhost:8050";
    var user = {};

    var warning = '<div class="alert alert-warning" role="alert">';
    var info = '<div class="alert alert-info" role="alert">';


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
            var message = warning+ "Um diese Inhalte zu sehen musst Du Dich erst einloggen."+ '</div>';
            loginFirst(message);
        }else{
            $("#logreg").text("Logout");
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
                    });
                }
            });
        } // end fetchTopics


    // QUESTIONS

    // QUESTIONS PAGE
    // Rendering questions
    function renderQuestions() {
        // ajax request with JWT
        $("#courses").hide();

        var jwt = user.jsonWebToken;
        console.log("neue Abfrage ueber renderQuestions");

        // tests if jwt is available
        if(jwt==null) {
            $(".failureMessage").text("Um diese Inhalte zu sehen musst Du Dich erst einloggen.");
            $("#courses").show();
        }else{
            $("#logreg").text("Logout");
            $("#questions").show();
            $(".failureMessage").hide();
        }

        // ajax request with JWT
        var jwt = user.jsonWebToken;
        var userID = user.userID;
        var learningStateID;


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
                                            id: "question-box"+element.questionID,
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
                                                .append($('<div class="col-6 ls-icon" style="width: 18rem;"/>')
                                                   // .append($('<img src="img/icon_3.jpg"/>')
                                                        .append($('<p>Noch kein Lernstatus festgelegt</p>'))
                                                    )
                                                .append($('<div class="ls-text col-6">')
                                                    .append($('<h3/>').text(element.topic.course.courseName))
                                                    .append($('<h5/>').text("Thema: "+element.topic.topicName))
                                                    .append($('<p/>',
                                                    {
                                                    class: "question-body"
                                                    })
                                                    .html(element.question)
                                                ))
                                            ) // end button
                                              ) // end append h2
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
                                                            learningStateID = 1;

                                                            var clickedID = "question-box"+element.questionID; // question-box1
                                                            console.log("userID: "+userID);
                                                            // console.log("clickedID: "+clickedID);

                                                            fetchLSImage(learningStateID, clickedID, jwt);
                                                            console.log("fetchLSImage geht noch");
                                                            setLearningState(userID, element.questionID, learningStateID, jwt);

                                                        })
                                                    )
                                                    .append($('<button name="ls2-button" type="button" class="btn btn-secondary standard-button ls2-button">geht so</button>')
                                                        .click(function() {
                                                            console.log("Clicked on middle button of question", element.questionID, "element: ", this);
                                                            learningStateID = 2;

                                                            var clickedID = "question-box"+element.questionID; // question-box2

                                                            // console.log("clickedID: "+clickedID);

                                                            fetchLSImage(learningStateID, clickedID, jwt);
                                                            setLearningState(userID, element.questionID, learningStateID, jwt);
                                                    }))
                                                    .append($('<button name="ls3-button" type="button" class="btn btn-secondary standard-button ls3-button">noch nicht</button>')
                                                        .click(function() {
                                                            // console.log("Clicked on right button of question", element.questionID, "element: ", this);
                                                            learningStateID = 3;

                                                            var clickedID = "question-box"+element.questionID; // question-box3

                                                            // console.log("clickedID: "+clickedID);

                                                            fetchLSImage(learningStateID, clickedID, jwt);
                                                            setLearningState(userID, element.questionID, learningStateID, jwt);
                                                     }))
                                                )

                                                .append($('<p id="evaluation-text">Aktueller Lernstatus</p>'))
                                    ) // end append collapseOne
                                     // end appends #question-box
                               // ) // end appends card
                        ); // end appends accordion

                    testQuestionsElement.append(questionBox);

                }) // end each
            } // end success function
        })// ende ajax
    } // end function renderQuestions


function fetchLSImage(learningStateID, clickedID, jwt) {

    $.ajax({
        method: 'GET',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + jwt);
        },

        url: host + "/state/"+ learningStateID + "/image",
        dataType: 'binary',
        headers: {"accept": "image/jpeg"},
        success: function (data) {
            console.log("Learning State");
            console.log("data "+data);
            var image = data;

            console.log("clickedID: "+clickedID);


            //$("#question-box1").find('.ls-icon').html($('<p>anderer Text</p>')); // funktioniert
            $("#"+clickedID).find('.ls-icon').html($("<img>", {src: window.URL.createObjectURL(image)}));

            //console.log("questionID ist "+ questionID);
        },
        error: function() {
            console.log("LS error", arguments);
        }
    })
} // end fetchLSImage


    // set LearningState
    function setLearningState (userID, questionID, learningStateID, jwt) {
        $(".failureMessage").hide();
        console.log("setLearningState gestartet");

        var setState = {
            "user" : {
                "userID" : userID
            },
            "question" : {
                "questionID" : questionID
            },
            "learningState" : {
                "learningStateID" : learningStateID
            }
        };

        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Authorization", "Bearer " + jwt);
            },
            // host + /user/1/state/set
            url: host + "/user/"+ userID +"/state/set",
            method: "POST",
            crossDomain: true,
            data: JSON.stringify(setState),
            contentType: "application/json",
            success: function(data) {

                if (typeof data == 'undefined') {
                    console.log("setLearningState fehlgeschlagen!");
                    return;
                }
            },
            error: function() {
                console.log("setLearningState error", arguments);
            }
        }) // end ajax
    }; // end setState


    // LOGIN AND REGISTRATION
    // Registrierung
    $('#register-button').click( function () {
        $(".login").hide();
        $(".registration").fadeTo(400, 1);
        $(".main").hide();
    });


    // shows Login form (again)
    $('#logreg').click( function() {
      $(".login").show();
      $(".failureMessage").hide();
      $('#loginForm').trigger("reset");
    });


    // Login
    $('#login-button').click( function() {

        var json = {
            "email": $('#email').val(),
            "password": b64_sha256($('#password').val())+"="
            // "password": $('#password').val()
        };
        var pwVal = $('#password').val();

        if (json.email.length==0 && pwVal==0) {
            var message = warning + "Bitte fülle beide Felder aus."+ '</div>';
           // var message = '<div class="alert alert-warning" role="alert">'+ "Bitte fülle beide Felder aus."+ '</div>';
            loginFailureMessage(message);

        }else if (json.email.length==0) {
            var message = warning + "Bitte trage eine gültige Mailadresse ein."+ '</div>';
            loginFailureMessage(message);

        }else if (pwVal.length==0) {
            var message = warning + "Bitte trage ein Passwort ein."+ '</div>';
            loginFailureMessage(message);

        }else{
            console.log("das ist das json mit den Benutzernamen: "+ JSON.stringify(json));

            $.ajax({
                url: host + "/user/login",
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

                  var message = warning+ "Login fehlgeschlagen."+ '</div>';
                  loginFailureMessage(message);
                }
            }) // end ajax request
        }
    });

    function loginFirst(message) {
        $(".failureMessage").fadeTo(100, 1, function(){ $(this).html(message).fadeOut(2000); });
    }

    function loginFailureMessage(message) {
        $(".login .failureMessage").fadeTo(100, 1, function(){ $(this).html(message).fadeOut(2000); });
    }

    function registrationFailureMessage(message) {
        $(".registration .failureMessage").fadeTo(100, 1, function(){ $(this).html(message).fadeOut(2000); });
    }

    function loginSuccessful() {
        var loginMessage = info + "Du hast Dich erfolgreich eingeloggt."+ '</div>';

        $(".main .failureMessage").removeClass("alert-warning")
            .fadeTo(100, 1, function(){ $(this).html(loginMessage).fadeOut(2000); });
        $("#logreg").text("Logout");
        $(".login").hide();
        $(".main").show();
        $("#courses").show();
    }

    function registrationSuccessful() {
        var registrationMessage = info + "Du hast Dich erfolgreich registriert."+ '</div>';

        $(".main .failureMessage").fadeTo(100, 1, function(){ $(this).html(registrationMessage).fadeOut(2000); });
        $("#logreg").text("Logout");
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
        var pw = b64_sha256($('#reg-pw').val())+"=";

        var regJson = {
            "email": mail,
            "firstname" : fn,
            "lastname" : ln,
            "password" : pw
        };

        // console.log("das ist das json mit dem Benutzernamen: "+ JSON.stringify(regJson));

        $.ajax({
            url: host + "/user/register",
            method: "POST",
            crossDomain: true,
            data: JSON.stringify(regJson),
            contentType: "application/json",
            success: function(data) {

                if (typeof data == 'undefined') {
                    $('.registration .failureMessage').html("Registrierung fehlgeschlagen!");
                    return;
                }
                user = data;
                var jwt = user.jsonWebToken;
                console.log("Das JWT dieses Users lautet: "+jwt);

                registrationSuccessful();
            },
            error: function() {
                console.log("registration error", arguments);
                var message = '<div class="alert alert-warning" role="alert">'+ "Registrierung fehlgeschlagen."+ '</div>';
                registrationFailureMessage(message);
            }
        }) // end ajax
    });

    /* noch irgendwo einbauen?
    $("#testQuestions").hover(
        function(){$("#testQuestions").animate ({color: blue, opacity: 0.6}, 1000);},
        function () {$("#testQuestions").show("scale", {percent: 200, direction: 'vertical' }, 2000)}
    ); */



    //
    $(".card").hover( function (e) {
        $(this).toggleClass('card', e.type === 'mouseenter');
    })

});// Ende jQuery



