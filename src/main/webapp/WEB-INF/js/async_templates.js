/**
 * Loads template asynchronously into the form based on selected value.
 */
$("#transactionTemplate").change(function () {
   var value = $(this).val();

   retrieveTemplate(value);
});

/**
 * Retrieve template from server by given identifier.
 *
 * @param id - template id
 * @returns {boolean}
 */
function retrieveTemplate(id) {
    if (!id) {
       clearForm();
       return false;
    }

    $.ajax({
        type: "GET",
        url: "/ib/templates/" + id,
        dataType: "json",
        success: function(data) {
            console.log(data);
            fillForm(data);
        },
        error: function(errMsg) {
            console.error(errMsg);
        }
    });
}

/**
 * Fill form with retrieved template.
 *
 * @param transactionTemplate - transaction template
 */
function fillForm(transactionTemplate) {
    for (var property in transactionTemplate) {
        if (transactionTemplate.hasOwnProperty(property)) {
            $("#" + property).val(transactionTemplate[property]);
        }
    }
}

/**
 * Clear form values.
 */
function clearForm() {
    $("form#newTransaction :input").each(function(){
        $(this).val('');
    });
}