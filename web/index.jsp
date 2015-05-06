<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gruszek
  Date: 02.05.15
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<style>
    #wrapper {
        width: 1200px;
        border: 1px solid black;
    }
    #first {
        width: 600px;
        float:left; /* add this */
        border: 1px solid red;
    }
    #second {
        border: 1px solid green;
        overflow: hidden; /* if you don't want #second to wrap below #first */
    }
</style>
<head>
    <title></title>
</head>
<body>
<br/>
<center>
<div id="wrapper">

<form action="helden" method="post">
    <select name="left">
        <c:forEach items="${heldenLeft}" var="heldenLeft">
            <option value="${heldenLeft}">${heldenLeft}</option>
        </c:forEach>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <select name="right">
        <c:forEach items="${heldenRight}" var="heldenRight">
            <option value="${heldenRight}">${heldenRight}</option>
        </c:forEach>
    </select>
    <br />
    <br />
    <input type="Submit" value="Nowe porównanie">
    <br />
    <br />
    <br />
    <br />
    <%
        String factor= (String)request.getAttribute("factor");
        String right= (String)request.getAttribute("right");

        String left= (String)request.getAttribute("left");

        if(factor!=null){
            out.println("Prawdopodobieństwo znajomości pomiędzy "+left+", a "+right+" wynosi: "+factor+"%");
        }
    %>
</form>

    <div id="first"><%
        String leftContent= (String)request.getAttribute("leftContent");
        if(left!=null){
            out.println(leftContent);
        }
    %>
    </div>
    <div id="second"><%
        String rightContent= (String)request.getAttribute("rightContent");
        if(left!=null){
            out.println(rightContent);
        }
    %>
    </div>
</div>

<br />
</center>
</body>
</html>
