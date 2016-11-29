$(document).ready(function () {
    console.log("jQuery ready!");

    $(".validated > input").focusout(function () {
        var $selectedInput = $(this);
        var paramName = $selectedInput.attr("name");
        var paramValue = $selectedInput.val();
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

            var $validationLabel = $selectedInput.next(".validationMessage");
            $validationLabel.removeClass("success error").addClass(cssClass).html(message);
        }, 'json');
    });
});