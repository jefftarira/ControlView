<%@page import="usuarios.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  Usuario u;
  u = (Usuario) session.getAttribute("usuario");
  if (u != null) {
%>
<jsp:forward page="app/private/main.jsp"></jsp:forward>
<% } else {%>
<jsp:forward page="app/public/main.jsp"></jsp:forward>
<% }%>