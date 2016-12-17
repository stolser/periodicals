var makeUnclickable = function () {
    event.preventDefault();
};

$(document).ready(function () {
    highlightSelectedMenu();
    activateUserAccountTabs();

    $(document).on("keypress", ":input:not(textarea)", function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    });

    $(".ajax-validated").focusout(function () {
        var $validatedElem = $(this);
        var paramName = $validatedElem.prop("name");
        var paramValue = $validatedElem.val();
        var periodicalOperationType = $("#periodicalOperationType").val();
        var entityId = $("#entityId").val();

        $.post("/backend/validation", {
            'paramName': paramName,
            'paramValue': paramValue,
            'entityId': entityId,
            'periodicalOperationType': periodicalOperationType
        }, function (data) {

            var json = data;
            var statusCode = json["statusCode"];
            var message = json["validationMessage"];

            var cssClass = (statusCode == 200) ? "success" : "error";

            if (cssClass == "success") {
                $validatedElem.closest(".form-group").removeClass("has-error");
                $validatedElem.closest(".form-group").addClass("has-success");
            } else if (cssClass == "error") {
                $validatedElem.closest(".form-group").removeClass("has-success");
                $validatedElem.closest(".form-group").addClass("has-error");
            }

            $validatedElem.next(".messages").remove();
            $validatedElem.after("<label class='messages " + cssClass + "'>" + message +
                "</label>");

            activateOrDisableSubmitBtn($validatedElem);

        }, 'json');
    });

    $("input, textarea").not(".ajax-validated").focusout(function () {
        $(this).next(".messages").remove();
        activateOrDisableSubmitBtn($(this));
    });

    $("form select").change(function () {
        activateOrDisableSubmitBtn($(this));
    });

    $(".disabled").click(makeUnclickable);

});

function activateOrDisableSubmitBtn($thisInput) {
    var $thisForm = $thisInput.closest("form"); // the <form> element inside which this input resides;
    var $thisFormInputs = $thisForm.find("input");  // all the input in the current form;
    var $errorMsg = $thisFormInputs.nextAll(".messages.error"); // elements containing error-messages;
    var $submitBtn = $thisForm.find(":submit");

    if (($errorMsg.length == 0) // there is no error-messages;
        && ($thisFormInputs.filter(function () {
            // returns empty form-elements that reside inside elements with class='required';
            var $closest = $(this).closest(".required");

            return ($closest.length > 0) && ($(this).val() == "");
        }).length == 0)) {
        activateElement($submitBtn);

    } else {
        disActivateElement($submitBtn);
    }
}

function activateElement(element) {
    element.removeClass("disabled");
    element.addClass("active");
    element.unbind('click', makeUnclickable);
}

function disActivateElement(element) {
    element.removeClass("active");
    element.addClass("disabled");
    element.click(makeUnclickable);
}

function highlightSelectedMenu() {
    var pathname = $(location).attr('pathname');
    var $menuLinks = $(".navbar li > a");

    $menuLinks.removeClass('active');

    $menuLinks.filter(function () {
        return ($(this).attr('href') == pathname);
    }).parent("li").addClass('active');
}

function activateUserAccountTabs() {
    $(".userAccountTabs .nav-tabs > .active").removeClass('active');
    $(".userAccountTabs .nav-tabs > li").first().addClass('active');
    
    $(".userAccountTabs .tab-content .active").removeClass('active');
    $(".userAccountTabs .tab-content .tab-pane").first().addClass('active');
}
