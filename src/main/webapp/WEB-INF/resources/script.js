

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

// вызов функции по завершению загрузки страницы
    $(document).ready(function() {
        // вызов функции после потери полем 'userName' фокуса
        $('#menu').click()(
            $('#menu').click()(function() {
            $.ajax({
                url : "pizzamarket/menu/get/all",     // URL - сервлет
                type: "GET",
                success : function(response) {
                    // обработка ответа от сервера
                    $('#ajaxUserServletResponse').text(response);
                }
            });
        });
    });