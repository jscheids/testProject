/**
 * Custom JavaScript used in conjunction with https://akottr.github.io/dragtable/
 * to add "draggable" functionaliy on wineList table columns. Only one method here currently utlized. 5/13/2017
 * 
 */
$(document).ready(function () {
    $("table tbody").sortable({
        update: function (event, ui) {
            $(this).children().each(function (index) {
                $(this).find('td').last().html(index + 1)
            });
        }
    });

    $('.defaultTable').dragtable();

    $('#footerTable').dragtable({excludeFooter: true});


    $('#persistTable').dragtable({persistState: '/someAjaxUrl'});

    $('#handlerTable').dragtable({dragHandle: '.some-handle'});

    $('#constrainTable').dragtable({dragaccept: '.accept'});

    $('#localStorageTable').dragtable({
        persistState: function (table) {
            if (!window.sessionStorage)
                return;
            var ss = window.sessionStorage;
            table.el.find('th').each(function (i) {
                if (this.id !== '') {
                    table.sortOrder[this.id] = i;
                }
            });
            ss.setItem('tableorder', JSON.stringify(table.sortOrder));
        },
        restoreState: eval('(' + window.sessionStorage.getItem('tableorder') + ')')
    });

});


