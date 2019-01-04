$(function() {
    console.log("INIT");

    var host = "http://localhost:8050";
    var user = {};


    $(".login").hide();
    $(".registration").hide();

    $("#home").click(function () {
        $(".login").hide();
        $(".registration").hide();
        console.log("auf Startseite geklickt");
    })


    // prevents reload of the page caused by the form elements default behaviour
    $("form").submit(function(e) { // war vorher ohne das .login
        console.log("form submit default behavior prevented by submit callback.");
        e.preventDefault(e);
    });


    renderCoursesMain();
    testRenderQuestions();


    // COURSES
    // rendering of the main container on home
    function renderCoursesMain () {
        $.ajax({
            method: 'GET',
            url: host + "/courses",
            success: function (data) {
                var coursesElement = $("#courses");

                $.each(data, function (index, element) {
                    console.log(element, $("#courseID" + element.courseID));

                    // ggf. noch in Funktion auslagern
                    // hier wird die CourseCard zs.gebaut, die danach zs. mit dem Course in der
                    // fetchTopics verwurstet wird
                    var courseCard =
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
                                        $('<p class="card-text">Hier überprüfst Du Dein Wissen zu aktuellen Patterns und Frameworks.</p>')
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

                                                /*
                                                // text mit fetchTopics ersetzen
                                                var topics = fetchTopics(element.courseName);
                                                $.each(topics, function (index, element) {
                                                        $("card-body").text(element.topicName);
                                                });
                                                */
                                            )
                                    )
                                ) // end card-body
                        ); // end col

                    coursesElement.append(courseCard); // ende der appends

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


    // QUESTIONS
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


    // TEST Rendering questions
    function testRenderQuestions() {
        // ajax request with JWT
        var jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1hcmlsZWVuLnN0YW1lckBzdHVkLmZoLWx1ZWJlY2suZGUiLCJwYXNzd29yZCI6IjEyMyJ9.coLj8ci8mvDdToBUtrWlJYO-aND7yZR4ok79Gn4-6bo";
        console.log("neue Abfrage ueber testRenderQuestions");

        $.ajax({
            method: 'GET',
            beforeSend : function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Authorization", "Bearer "+jwt);
            },

            url: host + "/questions", // holt erstmal alle Questions
            success: function (data) {
                var testQuestionsElement = $("#testQuestions"); // Aequivalent zu coursesElement
                console.log("xhr-Request laeuft");

                $.each(data, function (index, element) {
                    console.log("each "+element.questionID);


                    var questionBox = // Aequivalent zu courseCard
                        $('<div id="testQuestions"/>').append($('<div class="accordion" id="accordionExample"/>') // end div #accordionExample
                                .append($('<div class="card"/>')
                                    .append(
                                        $('<div/>',
                                        {
                                            id: "question-box"+element.questionID,

                                            class: "card-header"
                                        })
                                        .append($('<h2 class="mb-0"/>')
                                            .append($('<button class="btn btn-link col collapsed" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne"/>')
                                                .append($('<div class="ls-icon" style="width: 18rem;"/>')
                                                    .append($('<img src="img/icon_3.jpg"/>')
                                                        .append($('<p>noch nicht</p>'))
                                                    )).append($('<h3>neues Patterns and Frameworks</h3>'))
                                                .append($('<h5>Thema: JWT</h5>'))
                                                .append($('<p/>',
                                                    {
                                                    class: "question-body"
                                                    })
                                                    .text(element.question)
                                                )
                                            ) // end button                                        ) // end append h2
                                    )) // end div card-header
                                    .append($('<div id="collapseOne" class="collapse" aria-labelledby="question-box1" data-parent="#accordionExample"/>')
                                        .append($('<div/>',
                                            {
                                                class: "card-body"
                                            })
                                                .text(element.answer))
                                                .append($('<div id="ev-b" class="btn-group ls-icon" role="group" aria-label="Basic example"/>')
                                                    .append($('<button type="button" id="left-b" class="btn btn-secondary standard-button">kann ich</button>'))
                                                    .append($('<button type="button" class="btn btn-secondary standard-button">geht so</button>'))
                                                    .append($('<button type="button" class="btn btn-secondary standard-button">noch nicht</button>'))
                                                )
                                                .append($('<p id="evaluation-text">Aktueller Lernstatus</p>'))
                                    ) // end append collapseOne
                                     // end appends #question-box
                                ) // end appends card
                        ) // end appends accordion

                    testQuestionsElement.append(questionBox);
                    fetchQuestions(questionBox, element.topic_topicID);

                }) // end each
            } // end success function
        })// ende ajax
    } // end function testRenderQuestions


    // QUESTIONS
    // fetches and renders all questions of all courses - jwt protected
    function fetchAllQuestions () {
        console.log("JWT ist: "+user.jsonWebToken); // das erzeugt: undefined
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


                    $.each(data, function (index, element) {
                        // window.alert(element.questionID);
                        console.log("das ist die ID des Elements: "+element.questionId);

                        var questionCard = $('<h2 class="margin">Alle Fragen</h2>').append(
                            $('<div class="col"/>')).append(
                            $('<h4/>').text(element.question))
                    });
                }
        })
    }



    /* rendering of the all questions page
    function renderAllQuestions(){
        // aktuell einfach in der fetchAllQuestions drin
    } */

    // displays all questions through click on Alle Fragen button
    $("#nav-questions").click(function() {
        //$("#questions").text("neu");
        fetchAllQuestions();
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

    // LOGIN AND REGISTRATION
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


    // Login
    $('#login-button').click( function() {
       // var json = {"name": $('#username').val(), "password": b64_sha256($('#password').val())+"="};
       // TODO: aus Form holen
        var json = {
            "email": $('#email').val(),
            // "password": b64_sha256($('#password').val())     // gehashed mit b64_sha256
            // "password": hex_sha256($('#password').val())        // gehashed mit hex_sha256
            "password": $('#password').val()
        };
        console.log("das ist das json mit den Benutzernamen: "+ JSON.stringify(json));

        var jsonTest = {
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

    // Registration
    $('#initial-registr-button').click( function() {

        // var regJson = $('.registration form').serializeArray();
        var regJson = {
            "email": $('#reg-email').val(),
            "firstname" : $('#firstname').val(),
            "lastname" : $('#lastname').val(),
            "password" : $('#reg-pw').val()
        };

        console.log("das ist das json mit dem Benutzernamen: "+ JSON.stringify(regJson));

        $.ajax({
            url: host + "/user/register", //http://localhost:8050/user/register
            method: "POST",
            crossDomain: true,
            data: JSON.stringify(regJson),
            contentType: "application/json",
            success: function(data) {
                console.log("registrierung erfolgreich");
                if (typeof data == 'undefined') {
                    $('.registration .failureMessage').html("Registrierung fehlgeschlagen!");
                    return;
                }
                user = data;
                var jwt = user.jsonWebToken;
                console.log("Das JWT dieses Users lautet: "+jwt);
                window.alert(("Sie haben sich erfolgreich registriert."))
            },
            error: function() {
                console.log("registration error", arguments);
            }
        });
    });


}); // Ende jQuery


