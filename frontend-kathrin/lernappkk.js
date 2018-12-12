$(function() {

    var host = "http://localhost:8050";
    var user = {};
    //var products = new Map();
    //var basket = [];

    // Switch pane
    $('.nav a').on('click tap', function() {
        $('.nav a').removeClass("active");
        $(this).addClass("active");
        $('.pane').hide();
        var target = $(this).attr('href');
        $(target).show();
    });

    $('#basket').hide();
    $('#orders').hide();

    // Login http://localhost:8050/user/login
    $('.login button').on('click tap', function() {
        var json = {"name": $('#username').val(), "password": b64_sha256($('#password').val())+"="};
        $.ajax({
            url: host + "/user/login", // url: host + "/customers/auth",
            method: "POST",
            data: JSON.stringify(json),
            contentType: "application/json",
            success: function(data) {
                if (typeof data == 'undefined') {
                    $('.failureMessage').html("Login fehlgeschlagen!");
                    return;
                }
                user = data;

                $('.login').hide();
                $('.main').show();
            }
        });
    });

    /* TO DO: umbauen in question catalogue -> alle, alle Fragen anzeigen
    // Product catalogue
    function getCatalogue() {
        $.ajax({
            url: host + "/products",
            success: function(data) {

                // Iterate over products in catalogue
                $(data).each(function() {
                    var product = this;
                    products.set(product.id, product);

                    var img = '<div id="img-' + product.id + '" class="product-image"></div>';
                    var title = '<div class="bold">' + product.title + '</div>';
                    var price = '<div>' + product.currentPrice + ' MEUR</div>';
                    var btn = product.inStock ? '<div class="btn addToBasket green-button">In den Warenkorb</div>' : '<div class="btn orange-button">Nicht verfügbar</div>';
                    $('#catalogue').append('<div class="product" id="' + product.id + '">' + img + title + price + btn + '</div>');

                    // Load product image
                    $.ajax({
                        url: host + "/products/" + product.id,
                        dataType: 'binary',
                        headers: {"accept": "application/octet-stream"},
                        success: function(data) {
                            product.img = data;
                            $('#img-' + product.id).html($("<img>", {src: window.URL.createObjectURL(data)}));
                        }
                    });
                });

                // Add product to basket
                $('.addToBasket').on('click tap', function() {
                    var newItem = true;
                    var basketItem = {};
                    var id = Number($(this).parent().attr('id'));
                    basketItem.product = products.get(id);
                    $(basket).each(function() {
                        if (this.product.id == basketItem.product.id) {
                            this.quantity++;
                            newItem = false;
                        }
                    });
                    if (newItem) {
                        basketItem.quantity = 1;
                        basket.push(basketItem);
                    }
                    refreshBasket();
                });
            }
        });
    }
    */


});
