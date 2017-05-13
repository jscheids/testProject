

/*
 *Javascript file which supports ajax page. 
 */

(function ($, window, document) {
    $(function () {

        // declare JQuery selectors and cache results
        var $btnAdd = $('#btnAdd');
        var $btnSearch = $('#btnSearch');
              
        var $btnDelete = $('#btnDelete');
 
        var $btnSave = $('#btnSave');
        var $wineId = $('#wineId');
        var $wineName = $('#name');
        var $winePrice = $('#price');
        var $wineDateAdded = $('#dateAdded');
   
        var $searchKey = $('#searchKey');
        var baseUrl = "AjaxWineController";

        findAll();
        $btnDelete.hide();

        $btnAdd.on('click', function () {
            clearForm();
            $btnDelete.hide();
            $wineName.focus();
            return;
        });

        $btnSave.click(function () {
            alert("saving wine")
            if ($wineId.val() === '') {
                addWine();
            } else {
                updateWine();
                alert($wineId.val())
            }
            return false;
        });

        $btnDelete.click(function () {
            deleteWine();
            return false;
        });

        function clearForm() {
            $wineId.val("");
            $wineName.val("");
            $winePrice.val("");
            $wineDateAdded.val("");
     
        }

        function findAll() {
            $.get(baseUrl + "?action=list").then(function (wines) {
                renderList(wines);
            }, handleError);
        }

        function renderList(wines) {
            $('#wineList li').remove();
            $.each(wines, function (index, wine) {
                $('#wineList').append('<li><a href="#" data-identity="'
                        + baseUrl + '?action=findone&wineId='
                        + wine.wineId + '">' + wine.name +'</a></li>');
            });
        }

        function handleError(xhr, status, error) {
            alert("Sorry, there was a problem: " + error);
        }

        $('#wineList').on('click', "a", function () {
            findById($(this).data('identity'));
        });

        function findById(self) {
            $.get(self).then(function (wine) {
                renderDetails(wine);
                $btnDelete.show();
            }, handleError);
            return false;
        }

        function renderDetails(wine) {
            if (wine.name === undefined) {
                $('#wineId').val(wine.wineId);
            } else {
                var id = wine.wineId;
                $('#wineId').val(id);
            }
            $('#name').val(wine.wineName);
            $('#price').val("55.55");
            $('#dateAdded').val(wine.dateAdded);
          
        }

        /*
         * The searchKey is any term that is part of a hotel name, city 
         * or zip.
         */
        $btnSearch.on('click', function () {
            var searchKey = $searchKey.val();
            searchKey = escapeHtml(searchKey.trim());
            var url = "WineController?action=search&searchKey=" + searchKey;
            $.get(url).then(function (wine) {
                renderDetails(wine);
                $btnDelete.show();
            }, handleError);
        });

        var htmlEscapeCodeMap = {
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': '&quot;',
            "'": '&#39;',
            "/": '&#x2F;'
        };

        function escapeHtml(string) {
            return String(string).replace(/[&<>"'\/]/g, function (s) {
                return htmlEscapeCodeMap[s];
            });
        }

        var addWine = function () {
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: baseUrl + "?action=update",
                dataType: "json",
                data: formToJSON()
               
            })
            .done(function () {
                findAll();
                $btnDelete.show();
                alert("Wine added successfully");
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Wine could not be added due to: " + errorThrown );
            });
           
        }


        var updateWine = function () {
            console.log('updateWine');
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: baseUrl + "?action=update",
                dataType: "json",
                data: formToJSON()
            })
            .done(function () {
                findAll();
                $btnDelete.show();
                alert("Wine updated successfully");
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
               
                alert("Wine could not be updated due to: " + errorThrown );
            });
        }

        var deleteWine = function () {
            console.log('deleteWine');
            $.ajax({
                type: 'POST',
                url: baseUrl + "?action=delete&wineId=" + $wineId.val()
            })
            .done(function () {
                findAll();
                clearForm();
                $btnDelete.hide();
                alert("Wine deleted successfully");
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Wine could not be deleted due to: " + errorThrown);
            });
        }

        function renderDetails(wine) {
            if (wine.name === undefined) {
                $('#wineId').val(wine.wineId);
            } else {
                var id = wine.wineId;
                $('#wineId').val(id);
            }
            $('#name').val(wine.name);
            $('#price').val(wine.price);
            $('#dateAdded').val(wine.dateAdded);
           
        }

// Helper function to serialize all the form fields into a JSON string
        function formToJSON() {
  
            return JSON.stringify({
                "wineId": $wineId.val(),
                "name": $wineName.val(),
                "price": $winePrice.val(),
                "dateAdded": $wineDateAdded.val(), 
               
            });
            
        }
    });

}(window.jQuery, window, document));


///around line 200