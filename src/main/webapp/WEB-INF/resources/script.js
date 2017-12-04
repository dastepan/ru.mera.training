

function replaceArr(arrFromServer){
    arr=arrFromServer;
    draw();
}



function orderButton() {
    $.ajax({
        url: "pizzamarket/order/get/all",
        type: "GET",
        success: draw()
    });
}
function pizzaButton() {
    $.ajax({
        url: "pizzamarket/pizza/get/all",
        type: "GET",
        success: draw()
    });
}
function ingredientsButton() {
    $.ajax({
        url: "pizzamarket/ingredients/get/all",
        type: "GET",
        success: draw()
    });
}

function draw(response) {
    var result=response;
    var div = document.getElementById("info");

}


    $(document).ready(function() {
        $.getJSON("pizzamarket/menu/get/all",
            function(data){
                $("#info").html(data);
            });
    });