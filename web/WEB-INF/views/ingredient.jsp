<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
</head>

<script>
    var service = 'http://localhost:8080/ingredients';
    var RestPut = function(name, cost) {
        var JSONObject = {
            'name' : name,
            'cost' : cost
        };
        $.ajax({
            type: 'PUT',
            url: service + '/add',
            contentType: 'application/json;utf-8',
            data: JSON.stringify(JSONObject),
            dataType: 'json',
            async: false,
            success: function (result) {
                $('#response').html(JSON.stringify(result));
            },
          error: function (jqXHR,testStatus, errorThrown) {
              $('#response').html(JSON.stringify(jqXHR));
          }  
        });
    };
</script>

<body>
<h1>Admin menu</h1>

<table class="table">
    <tr>
        <th>Request type</th>
        <th>URL</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>
            Add Ingredient<code><strong>PUT</strong></code>
        </td>
        <td>
            <code>/ingredients/add</code>
        </td>
        <td>
            <form class="form-inline">
                name: <input type="text" id="putName" value="ingredientName">
                description: <input type="text" id="putCost" value="ingredientCost">
                <button type="button" onclick="RestPut($('#putName').val(),$('#putCost').val())">Try</button>
            </form>
        </td>
    </tr>
</table>

<div class="panel panel-default">
    <div class="panel-heading">
        <stong>RESPONSE</stong>
    </div>
    <div class="panel-body" id="response"></div>
</div>
</body>
</html>
