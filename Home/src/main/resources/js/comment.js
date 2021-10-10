const moveToCollapseElement = (e) => {
    const idCollapseDiv = e.parentElement.getAttribute("data-target").slice(1);
    const collapseDiv = document.getElementById(idCollapseDiv);
    collapseDiv.addEventListener("shown.bs.collapse", function () {
        $('html,body').animate({
            scrollTop: $('#' + idCollapseDiv).offset().top - 500
        }, 1000);
    });
}

const renderCommentLv1 = (result) => {
    var commentDiv = "<a class='pull-left' href='#'><img class='rounded-circle' src='" + result.data.avatar + "'></a>";
    commentDiv += "<div class='media-body d-flex flex-column'>";

    commentDiv += "<div class='col-12 d-flex align-items-center'>";
    commentDiv += "<h4 class='media-heading'>" + result.data.userName + "</h4>";
    commentDiv += "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Posted on " + result.data.postedDate + "</span>";
    commentDiv += "</div>";

    commentDiv += "<p>" + result.data.comment + "</p>";

    commentDiv += "<ul class='list-unstyled list-inline media-detail pull-left'>";
    commentDiv += "<li><i class='fa fa-thumbs-up'></i>0</li>";
    commentDiv += "<li class='like p-2 cursor action-collapse' data-toggle='collapse' aria-expanded='false' data-target='#collapse-" + result.data.commentId + "' aria-controls='collapse-" + result.data.commentId + "'>";
    commentDiv += "<a href='javascript:void(0)' onclick='moveToCollapseElement(this)'>Reply</a>"
    commentDiv += "</li>";
    commentDiv += "</ul>";

    commentDiv += "<div id='collapse-" + result.data.commentId + "' class='bg-light p-2 collapse' data-parent='#myGroup'>"
    commentDiv += "<form id='frmReplyComment-" + result.data.commentId +"'>";
    commentDiv += "<div class='d-flex flex-row align-items-start'>";
    commentDiv += "<img class='rounded-circle' src='" + result.data.avatar + "' width='40'>";
    commentDiv += "<textarea class='form-control ml-1 shadow-none textarea' name='comment' id='replyMessage-" + result.data.commentId +"'></textarea>";
    commentDiv += "</div>";
    commentDiv += "<div class='mt-2 text-right'>";
    commentDiv += "<button class='btn btn-sm shadow-none' type='button' onclick='insertComment(this)' data-submit='2' data-parentId='" + result.data.commentId + "'>Reply comment</button>";
    commentDiv += "<button class='btn btn-outline-primary btn-sm ml-1 shadow-none action-collapse' type='button' data-toggle='collapse' aria-expanded='false' data-target='#collapse-" + result.data.commentId + "' aria-controls='collapse-" + result.data.commentId + "'>Cancel</button>";
    commentDiv += "</div>";
    commentDiv += "</form>";
    commentDiv += "</div>"

    commentDiv += "</div>";

    const divNode = document.createElement("div");
    divNode.classList.add("media");
    divNode.innerHTML = commentDiv;

    const groupComment = document.getElementById("myGroup");
    const firstDivElement = document.querySelector("div.media");
    groupComment.insertBefore(divNode, firstDivElement);
}

const renderCommentLvn = (result) => {
    console.log('Render comment level n');
    var comment = "<a class='pr-3' href='#'><img class='rounded-circle' alt='Avatar member' src='" + result.data.avatar + "' /></a>"
    comment += "<div class='media-body d-flex flex-column'>";
        comment += "<div class='row'><div class='col-12 d-flex align-items-center'>" +
            "<h5>" + result.data.userName + "</h5>" +
            "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Posted on " + result.data.postedDate + "</span>"
        comment += "</div></div>"
        comment += "<p>" + result.data.comment + "</p>"
        comment += "<ul class='list-unstyled list-inline media-detail pull-left'>";
        comment += "<li><i class='fa fa-thumbs-up'></i>0</li>";
        comment += "<li class='like p-2 cursor action-collapse' data-toggle='collapse' aria-expanded='false' data-target='#collapse-" + result.data.parentLevel +"' aria-controls='collapse-" + result.data.parentLevel +"'><a href='javascript:void(0)' onclick='moveToCollapseElement(this)'>Reply</a></li>";
        comment += "</ul>";
    comment += "</div>";

    const divNode = document.createElement("div");
    divNode.classList.add("media");
    divNode.classList.add("mt-4");
    divNode.innerHTML = comment;

    const lastDivElement = document.getElementById("collapse-" + result.data.parentLevel);
    const parentElement = lastDivElement.parentElement;
    parentElement.insertBefore(divNode, lastDivElement);
}

const insertComment = (e) => {
    const parentCommentId = e.getAttribute("data-parentId");
    const idMessage = e.getAttribute("data-submit") === "1" ? "message" : "replyMessage-" + parentCommentId;
    if (document.getElementById(idMessage).value == "") {
        alert("Please fill out comment!!");
        return;
    }
    const idForm = e.getAttribute("data-submit") === "1" ? "frmInsertComment" : "frmReplyComment-" + parentCommentId;
    var data = $("#" + idForm).serializeObject();

    data['parentLevel'] = parentCommentId;
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/app/comments",
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
            $("body .wrapper").css('opacity', 0.3);
            $("#loader").show();
        },
        success: function (result) {
            if (result.statusCode == 200) {
                if (result.data.parentLevel == 0) {
                    renderCommentLv1(result);
                } else {
                    renderCommentLvn(result);
                }
            }
        },
        error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        },
        complete: function () {
            document.getElementById(idMessage).value = "";
            $("#loader").fadeOut("slow");
            $("body .wrapper").fadeIn(600);
            $("body .wrapper").css('opacity', 1);
        }
    });
}