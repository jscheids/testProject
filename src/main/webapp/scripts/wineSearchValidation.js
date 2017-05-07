$().ready(function () {
jQuery.validator.addMethod("specialChars", function( value, element ) {
        var regex = new RegExp("^[a-zA-Z0-9]*$");
        var key = value;

        if (!regex.test(key)) {
           return false;
        }
        return true;
    }, "please use only alphanumeric or alphabetic characters");
    $("#formWineSearch").validate({// initialize the plugin

        rules: {

            wineSearchName: {
              specialChars: true,
                minlength: 1,
                maxlength: 80

            },
           wineSearchMinPrice: {

             
                range: [1.00, 9999.99]

            },
         
    wineSearchMaxPrice: {

          
                range: [1.00, 9999.99]

            },
        },

        messages: {
            wineSearchName: "Provide a value for the name",
            wineSearchMinPrice: "Provide a value for price",
            wineSearchMaxPrice: "Provide a value for price",

        },
        errorPlacement: function (error, element) {
            // This is the default behavior of the script for all fields
            error.insertAfter(element);

        },

    });

});


