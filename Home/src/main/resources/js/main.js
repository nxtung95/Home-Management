$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
});

// Wait for window load
$(window).on("load", function() {
    console.log("window loading");
    // Animate loader off screen
    $("#loader").fadeOut("slow");
    $("body .wrapper").fadeIn(600);
});