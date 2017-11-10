<!DOCTYPE html public "-//w3c//dtd html 4.0 transitional//en">
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>JSP java in HTML</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <link  href="<c:url value="main.css"/>" rel="stylesheet">
    <script src="script.js"></script>
</head>

<body>

<table id="tasks" align="center" width="600">
</table>

<table id="formAdd" align="center" width="600">
    <form action="add" method="post">
        <tr>
            <td>
                Time <input type="text" name="timeField" value="" size=15 maxlength=20>
            </td>

            <td>
                Task <input type="text" name="taskField" value="" size=15 maxlength=20>
            </td>

            <td>
                <input name="submitForm" type="submit" value="Add task" onclick="return false"/>
            </td>

        </tr>
    </form>
</table>
</body>
</html>

