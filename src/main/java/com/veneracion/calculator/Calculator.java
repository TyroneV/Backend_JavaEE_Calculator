package com.veneracion.calculator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class Calculator extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(
                "<html>"+(readInput(request.getParameter("input")))+"</html>"
        );
    }
    double compute(double n1 , double n2, String operator){
        double result = 0d;
        switch (operator){
            case "plus":
                result = n1 + n2;
                break;
            case "minus":
                result = n1 - n2;
                break;
            case "times":
                result = n1 * n2;
                break;
            case "div":
                result = n1 / n2;
                break;
            default:
                return result;
        }
        return result;

    }
    public String readInput(String syntax){
        String[] strNumbers = syntax.split("(?<!^)\\D+");
        String[] ops = syntax.split("\\d+|[.]|^-");
        List<String> strOperators = new ArrayList<>();

        boolean initialize = false;
        try {
            for (String s: ops){
                if(s.equals("times")||s.equals("div")||s.equals("minus")||s.equals("plus")){
                    strOperators.add(s);
                }
            }
            double holder = 0d;
            for (String strNumber : strNumbers) {
                if (!initialize) {
                    holder = Double.parseDouble(strNumber);
                    initialize = true;
                } else if (strOperators.size() > 0) {
                    holder = compute(holder, Double.parseDouble(strNumber), strOperators.get(0));
                    strOperators.remove(0);
                }
            };
            syntax = Double.toString(holder);
        } catch (Exception e){
            syntax = "Syntax Error";
        }
        return syntax;
    }
}