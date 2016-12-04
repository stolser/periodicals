$(document).ready(function () {
    console.log("jQuery is ready!");

    $(".ajax-validated").focusout(function () {
        var $validatedElem = $(this);
        var paramName = $validatedElem.prop("name");
        var paramValue = $validatedElem.val();
        var entityOperationType = $("#entityOperationType").val();
        var entityId = $("#entityId").val();
        
        console.log("paramName = " + paramName + "; paramValue = " + paramValue + 
        "entityId = " + entityId + "; entityOperationType = " + entityOperationType);

        $.post("/Validation", {
            'paramName': paramName,
            'paramValue': paramValue,
            'entityId': entityId,
            'entityOperationType': entityOperationType
        }, function (data) {
            console.log("a response from the server is here!");

            // var json = JSON.parse(data);
            var json = data;
            console.log("json = " + json);

            var statusCode = json["statusCode"];
            var message = json["validationMessage"];

            console.log("statusCode = " + statusCode);
            console.log("message = " + message);

            var cssClass = (statusCode == 200) ? "success" : "error";
            console.log("cssClass = " + cssClass);

            if (cssClass == "success") {
                $validatedElem.parent(".form-group").removeClass("has-error");
                $validatedElem.parent(".form-group").addClass("has-success");
            } else if (cssClass == "error") {
                $validatedElem.parent(".form-group").removeClass("has-success");
                $validatedElem.parent(".form-group").addClass("has-error");
            }

            $validatedElem.next(".messages").remove();
            $validatedElem.after("<label class='messages " + cssClass + "'>" + message +
                "</label>");

            activateOrDisableSubmitBtn($validatedElem);

        }, 'json');
    });

    $("#loginform input").not(".ajax-validated").focusout(function () {
        $(this).next(".messages").remove();
        activateOrDisableSubmitBtn($(this));
    });

    function activateOrDisableSubmitBtn($thisInput) {
        console.log("$thisInput = " + $thisInput);
        var $thisForm = $thisInput.closest("form"); // the <form> element inside which this input resides;
        console.log("$thisForm = " + $thisForm);

        var $thisFormInputs = $thisForm.find("input");  // all the input in the current form;
        var $errorMsg = $thisFormInputs.nextAll(".messages.error"); // elements containing error-messages;

        var $submitBtn = $thisForm.find(":submit");
        console.log("$submitBtn = " + $submitBtn.html());

        if (($errorMsg.length == 0) // there is no error-messages;
            && ($thisFormInputs.filter(function () {
                // returns empty form-elements that reside inside elements with class='required';
                var $parent = $(this).closest(".required");
                console.log("$parent.length = " + $parent.length);
                
                return ($parent.length > 0) && ($(this).val() == "");
            }).length == 0)) {
            console.log("activating the submit btn...");

            $submitBtn.removeClass("disabled");
            $submitBtn.addClass("active");
            
        } else {
            console.log("Deactivating the submit btn...");

            $submitBtn.removeClass("active");
            $submitBtn.addClass("disabled");
        }
    }

    // $(document).on("keypress", ":input:not(textarea)", function(event) {
    //     if (event.keyCode == 13) {
    //         event.preventDefault();
    //     }
    // });

});