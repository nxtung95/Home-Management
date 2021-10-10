$(document).ready(function () {
    setActiveClassSideBar();
});

function setActiveClassSideBar() {
    const navContainer = document.getElementById("sidebar");
    const ulElement = navContainer.querySelector("div ~ ul");
    const liElements = ulElement.querySelectorAll(":scope > li");

    for (var i = 0; i < liElements.length; i++) {
        liElements[i].addEventListener("click", function () {
            const currentLi = document.getElementsByClassName("active");
            currentLi[0].className = currentLi[0].className.replace("active", "");
            this.className += "active";
        });
    }
}