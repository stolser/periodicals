$(document).ready(function () {
    console.log("jQuery is ready!");

    $(".ajax-validated").focusout(function () {
        var $validatedElem = $(this);
        var paramName = $validatedElem.prop("name");
        var paramValue = $validatedElem.val();
        console.log("paramName = " + paramName + "; paramValue = " + paramValue);

        $.post("/Validation", {
            'paramName': paramName,
            'paramValue': paramValue
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

            activateOrDisableSubmitBtn();

        }, 'json');
    });

    $("#loginform input").not(".ajax-validated").focusout(function () {
        $(this).next(".messages").remove();
        activateOrDisableSubmitBtn();
    });

    function activateOrDisableSubmitBtn() {
        var $loginInputs = $("#loginform input");
        var $errorMsg = $loginInputs.nextAll(".messages.error");

        var $submitBtn = $("#loginform :submit");
        if ($errorMsg.length == 0
            && $loginInputs.filter(function () {
                return $(this).val() == "";
            }).length == 0) {
            $submitBtn.removeClass("disabled");
            $submitBtn.addClass("active");
        } else {
            $submitBtn.removeClass("active");
            $submitBtn.addClass("disabled");
        }
    }

});