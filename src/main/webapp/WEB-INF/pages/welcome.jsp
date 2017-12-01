<!DOCTYPE html public "-//w3c//dtd html 4.0 transitional//en">
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>JSP java in HTML</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <%--загрузка jquery из google--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <%--подключам скрипты--%>
    <script type="text/javascript"><%@include file="/WEB-INF/resources/script.js"%></script>
    <%--подключаем CSS--%>
    <style><%@include file="/WEB-INF/resources/mainStyle.css"%></style>
    <style><%@include file="/WEB-INF/resources/button.css"%></style>
</head>

<body class="bg">
    <div class="mainDiv">
        <h1>Don Pizzalini</h1>
    </div>


    <div class="mainDiv">
        <div id="contents" class="centerer">
            <a href="#menu" class="button" onclick="menuButton(); return false">Menu</a>
        </div><br/>
        <div class="centerer">
            <a href="#order" class="button" onclick="orderButton(); return false">Order</a>
        </div><br/>
        <div class="centerer">
            <a href="#pizza" class="button" onclick="pizzaButton(); return false">Pizza</a>
        </div><br/>
        <div class="centerer">
            <a href="#ingredients" class="button" onclick="ingredientsButton(); return false">Ingredients</a>
        </div><br/>
    </div>


    <h2 id="menu">Menu</h2>
    <div id="info"></div>
    <div id="menuBall4" class="menuBall">
        <a href="#contents" class="ball greenball">
            <div class="menuText">
                UP
            </div>
        </a>
    </div>

    <h2 id="order">Order</h2>
    <p>Изначально локатор URL был разработан как система для максимально естественного указания на местонахождения ресурсов в сети. Локатор должен был быть легко расширяемым и использовать лишь ограниченный набор ASCII‐символов (к примеру, пробел никогда не применяется в URL). В связи с этим, возникла следующая традиционная форма записи URL:</p>
    <p>&lt;схема&gt;://&lt;логин&gt;:&lt;пароль&gt;@&lt;хост&gt;:&lt;порт&gt;/&lt;URL-путь&gt;?&lt;параметры&gt;#&lt;якорь&gt;</p>
    <div id="menuBall1" class="menuBall">
        <a href="#contents" class="ball greenball">
            <div class="menuText">
                UP
            </div>
        </a>
    </div>

    <h2 id="pizza" >pizza</h2>
    <p>Появление адресов URL стало существенным нововведением в Интернете. Однако с момента его изобретения и по сей день стандарт URL обладает серьёзным недостатком — в нём можно использовать только ограниченный набор символов, даже меньший, нежели в ASCII: латинские буквы, цифры и лишь некоторые знаки препинания. Если мы захотим использовать в URL символы кириллицы, или иероглифы, или, скажем, специфические символы французского языка, то нужные нам символы должны быть перекодированы особым образом.</p>
    <div id="menuBall2" class="menuBall">
        <a href="#contents" class="ball greenball">
            <div class="menuText">
                UP
            </div>
        </a>
    </div>

    <h2 id="ingredients">ingredients</h2>
    <p>Появление адресов URL стало существенным нововведением в Интернете. Однако с момента его изобретения и по сей день стандарт URL обладает серьёзным недостатком — в нём можно использовать только ограниченный набор символов, даже меньший, нежели в ASCII: латинские буквы, цифры и лишь некоторые знаки препинания. Если мы захотим использовать в URL символы кириллицы, или иероглифы, или, скажем, специфические символы французского языка, то нужные нам символы должны быть перекодированы особым образом.</p>
    <div id="menuBall3" class="menuBall">
        <a href="#contents" class="ball greenball">
            <div class="menuText">
                UP
            </div>
        </a>
    </div>
</body>
</html>

