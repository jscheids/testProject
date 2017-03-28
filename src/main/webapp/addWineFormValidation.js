$().ready(function () {

    $("#addWineForm").validate({// initialize the plugin

        rules: {

            wineName: {
                required: true,
                minlength: 1,
                maxlength: 80

            },
            winePrice: {

                required: true,
                range: [1.00, 9999.99]

            },
            wineImgUrl: {
                required: true,
                minlength: 5,
                maxlength: 80
            }

        },

        messages: {
            wineName: "Provide a value for the name",
            winePrice: "Provide a value for price",
            wineImgUrl: "Provide the file name for the wine image. For example, winePic.png",

        },
        errorPlacement: function (error, element) {
            // This is the default behavior of the script for all fields
            error.insertAfter(element);

        },

    });

});




