function searchTotalPriceClient(e) {
    var data = $("#frmSearchClientTotal").serializeObject();
    data["pageNumber"] = e.getAttribute("data-current_page");
    $.ajax({
        url: "/app/client/total",
        cache: true,
        contentType: "application/json",
        dataType: "json",
        type: "GET",
        data: data,
        beforeSend: function () {
            $("body .wrapper").css('opacity', 0.3);
            $("#loader").show();
        },
        success: function (result) {
            const priceListTable = document.getElementById("priceListTable");
            const oldTrTag = priceListTable.getElementsByTagName("tr");
            removeHtmlElement(oldTrTag);

            const priceList = result.priceList;
            for (const key in priceList) {
                const newTrTag = document.createElement("tr");
                const status = priceList[key].status ? "Đã nộp" : "Chưa nộp";
                var html = "<td>Tháng <span>" + formatDate(priceList[key].createdAt, "MM-yyyy") + "</span></td>";
                html += "<td><span>" + priceList[key].roomPrice + "</span></td>";
                html += "<td><span>" + priceList[key].electricPrice + "</span></td>";
                html += "<td><span>" + priceList[key].waterPrice + "</span></td>";
                html += "<td><span>" + priceList[key].internetPrice + "</span></td>";
                html += "<td><span>" + priceList[key].garbagePrice + "</span></td>";
                html += "<td><span>" + priceList[key].total + "</span></td>";
                html += "<td><span>" + status + "</span></td>";
                html += "<td><span>" + priceList[key].note + "</span></td>";
                if (priceList[key].depositedAt != null) {
                    html += "<td><span>" + formatDate(priceList[key].depositedAt, "MM-yyyy") + "</span></td>";
                }
                newTrTag.innerHTML = html;
                priceListTable.appendChild(newTrTag);
            }

            const ulPaginations = document.getElementById("pagination");
            const oldLiTags = ulPaginations.getElementsByTagName("li");
            removeHtmlElement(oldLiTags);
            if (result.totalPage > 1) {
                var html = "";
                if (result.currentPage != 1) {
                    html += "<li class='page-item'><a class='page-link' href='#' onclick='searchTotalPriceClient(this)' data-current_page='" + (result.currentPage - 1) + "'>Previous</a></li>";
                }
                for (let i = 1; i <= result.totalPage; i++) {
                    const activeClass = (i === result.currentPage) ? 'active' : '';
                    html += "<li class='page-item " + activeClass + "'><a class='page-link' href='#' onclick='searchTotalPriceClient(this)' data-current_page='" + i + "'>" + i + "</a></li>";
                }
                if (result.currentPage < result.totalPage) {
                    html += "<li class='page-item'><a class='page-link' href='#' onclick='searchTotalPriceClient(this)' data-current_page='" + (result.currentPage + 1) + "'>Next</a></li>";
                }
                ulPaginations.innerHTML = html;
            }
        },
        error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        },
        complete: function () {
            $("#loader").fadeOut("slow");
            $("body .wrapper").fadeIn(600);
            $("body .wrapper").css('opacity', 1);
        }
    });
}