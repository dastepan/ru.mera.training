

function replaceArr(arrFromServer){
    arr=arrFromServer;
    draw();
}

$(function(){

    function getIt() {
        $.ajax({
            url: "pizzamarket/menu",
            type: "GET",
            success: draw
        });
    }

    function draw(response) {

    }

    getIt();



});



