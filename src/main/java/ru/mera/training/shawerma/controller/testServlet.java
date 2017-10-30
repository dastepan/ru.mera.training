package ru.mera.training.shawerma.controller;

import java.io.IOException;
import java.util.ArrayList;

@javax.servlet.annotation.WebServlet(name = "testServlet")
public class testServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("шурма");
        strings.add("ингредиент");
        strings.add("заказ");
        strings.add("ордер");
        strings.add("меню");

        request.setAttribute("message", strings);
        request.getRequestDispatcher("WEB-INF/jsp/test.jsp").forward(request, response);
    }
}
