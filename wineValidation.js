$(document).ready(function () {
    
     $('#form').validate({ // initialize the plugin

        rules: {
          
            name: {
                alpha: true,
                required: true,
                minlength: 1,
                maxlength: 50

            },
            lName: {
                alpha: true,
                required: true,
                minlength: 1,
                maxlength: 50

            },
        
            state: {
                isstate: true,
                required: true,  
  
            },
            city: {
                required: true
            },
            zip: {
                required: true,
               number: true, 
                minlength: 5,
                maxlength: 5
            },
            address: {
                required: true,
                minlength: 5,
                maxlength: 100

            },

          
           

        },
        
        messages: {
            name: "Please enter your first name (for example, Maurice)",
            lName: "Please Enter your last name (for example, Moss)",
            state: "Please enter a vaild State!",
            zip: "Please enter a 5 digit zip code",
            address: "Please enter your street address!",
            city: "You must live in a city of sorts....",
          
            
        },
        errorPlacement: function (error, element) {
             // This is the default behavior of the script for all fields
                error.insertAfter(element);
            
        },
      
    });
   
});
    
    
    
    



