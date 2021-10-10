$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
});

const formatDate = (date, format) => {
    const d = date.split("-");
    var res = "";
    if (format == "MM-yyyy") {
        res = d[1] + "-" + d[0];
    }
    return res;
}

const removeHtmlElement = (arr) => {
    for (var i = arr.length - 1; i >= 0; --i) {
        arr[i].remove();
    }
}

const replaceDotToApostrophe = data => {
    const result = data.replace(/\./g, '');
    return result;
}

const getColorForChart = (i) => {
    var color;
    if (i == "0") {
        color = 'rgb(255, 99, 132)';
    } else if (i == "1") {
        color = 'rgb(54, 162, 235)';
    } else if (i == "2") {
        color = 'rgb(75, 192, 192)';
    }
    return color;
}

// Wait for window load
$(window).on("load", function() {
    console.log("window loading");
    // Animate loader off screen
    $("#loader").fadeOut("slow");
    $("body .wrapper").fadeIn(600);
});