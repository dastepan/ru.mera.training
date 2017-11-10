
var arr=[{time:"21:00",task:"Go home!"}];

function replaceArr(arrFromServer){
    arr=arrFromServer;
    draw();
}

$(function(){ //обернуть в DOM

    function postIt(timeS,taskS) {
        $.ajax({
            url: "mainPage/add",
            contentType: "application/json; charset=utf-8",
            type: "POST",
            data: JSON.stringify({time:timeS,task:taskS}),
            success: draw
        });
    }

    function getIt() {
        $.ajax({
            url: "mainPage/get",
            type: "GET",
            success: draw
        });
    }

    function draw(response) {
        if(response!==undefined)
            arr=response.tasks;
        $("#tasks").empty();
        $("<tr><th>Time</th><th>Task</th></tr>").appendTo("#tasks");
        arr.forEach(function(item,i,arr){
            $("<tr><td>"+item.time+"</td><td>"+item.task+"</td></tr>").appendTo("#tasks");
        });
    }

    getIt();//выполниться при загрузке страницы


    $(document).on("click", "input[name='submitForm']", function () {
        var timeS = $("input[name='timeField']").val();
        var taskS = $("input[name='taskField']").val();
        postIt(timeS,taskS);
    });

});



