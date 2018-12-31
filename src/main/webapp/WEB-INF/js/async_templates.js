$("#transactionTemplate").change(function () {
   var value = $(this).val();

   retrieveTemplate(value);
});

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

function fillForm(transactionTemplate) {
    for (var property in transactionTemplate) {
        if (transactionTemplate.hasOwnProperty(property)) {
            $("#" + property).val(transactionTemplate[property]);
        }
    }
}

function clearForm() {
    $("form#newTransaction :input").each(function(){
        $(this).val('');
    });
}