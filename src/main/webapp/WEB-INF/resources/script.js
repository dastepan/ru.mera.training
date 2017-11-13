
var arr=[];

function replaceArr(arrFromServer){
    arr=arrFromServer;
    draw();
}

$(function(){

    function getIt() {
        $.ajax({
            url: "menu",
            type: "GET",
            success: draw
        });
    }

    function draw(response) {
        if(response!==undefined)
            arr=response;
        $("#tasks").empty();
        $("<tr><th>Time</th><th>Task</th></tr>").appendTo("#tasks");
        arr.forEach(function(item,i,arr){
            $("<tr><td>"+item+"</td><td>"+item+"</td></tr>").appendTo("#tasks");
        });
    }

    getIt();



});



