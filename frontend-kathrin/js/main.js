$(function() {
    console.log("INIT");

    var host = "http://localhost:8050";
    var user = {};


    $(".login").hide();
    $(".registration").hide();

    $("form").submit(function(e) { // war vorher ohne das .login
        console.log("form submit default behavior prevented by submit callback.");
        e.preventDefault(e);
    });

    renderCoursesMain();

    // rendering of the main container on home
    function renderCoursesMain () {
        $.ajax({
            method: 'GET',
            url: host + "/courses",
            success: function (data) {
                var coursesElement = $("#courses");

                $.each(data, function (index, element) {
                    // window.alert(element.courseName);
                    console.log(element, $("#courseID" + element.courseID));

                    // ggf. noch in Funktion auslagern
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
    }



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


    // fetches all questions of all courses - jwt protected
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


                    /* Original Bootstrap
                    <div class="media">
                      <div class="media-body">
                        <h5 class="mt-0 mb-1">Media object</h5>
                        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                      </div>
                      <img src="..." class="ml-3" alt="...">
                    </div>

                     */
                }
        })
    }



    /* rendering of the all questions page
    function renderAllQuestions(){
        // aktuell einfach in der fetchAllQuestions drin
    } */

    // displays all questions
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



    $("#home").click(function () {
        $(".login").hide();
        $(".registration").hide();
        console.log("auf Startseite geklickt");
    })


}); // Ende jQuery


