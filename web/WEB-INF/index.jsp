<%--
  Created by IntelliJ IDEA.
  User: voron
  Date: 18.05.2019
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Entity.User"%>
<%@ page import="Entity.DataMessage"%>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
<head>
    <title>Chat</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="../style.css">
    <script src="https://cdn.rawgit.com/JDMcKinstry/JavaScriptDateFormat/master/Date.format.min.js"></script>
</head>

<body onload="chatUnit.init()">
<div class="container">

    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4 id="username">${sessionScope.user.getUsername()}</h4>
                    </div>
                    <form class="srch_bar" method="post">
                        <div class="stylish-input-group">
                            <input type="text" class="search-bar"  placeholder="Search" >
                            <span class="input-group-addon">
                                <button type="submit" name="Search" value="SearchBtn"> <i class="fa fa-search" aria-hidden="true"></i> </button>
                            </span>
                        </div>
                    </form>
                </div>
                <div class="inbox_chat">
                    <c:forEach items="${contacts}" var="contact">
                    <div class="chat_list">
                        <div class="chat_people">
                            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                            <div class="chat_ib">
                                <h5>${contact.getFirstname()} ${contact.getLastname()} <span class="chat_date">Last Date</span></h5>
                                <p><span class="contact-username">@${contact.getUsername()}</span></p>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="mesgs">


                    <div class="msg_history">
                        <c:forEach items="${contacts}" var="contact">
                            <div class="ChatWindows">
                                <c:forEach items="${messages}" var="mes">
                                        <c:choose>
                                            <c:when test="${mes.getReceiver().getId() == sessionScope.user.getId() && mes.getSender().getId() == contact.getId()}">
                                            <div class="incoming_msg">
                                                <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                                                <div class="received_msg">
                                                    <div class="received_withd_msg">
                                                        <p>${mes.getText()}</p>
                                                        <span class="time_date">${mes.getDate()}</span></div>
                                                </div>
                                            </div>
                                            </c:when>
                                            <c:when test="${mes.getSender().getId() == sessionScope.user.getId() && mes.getReceiver().getId() == contact.getId()}">
                                                <div class="outgoing_msg">
                                                    <div class="sent_msg">
                                                        <p>${mes.getText()}</p>
                                                        <span class="time_date">${mes.getDate()}</span> </div>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                </c:forEach>
                            </div>
                        </c:forEach>

                    </div>


                    <div class="type_msg">
                        <div class="input_msg_write">

                            <input type="text" class="write_msg" placeholder="Type a message" name="message" />
                            <button class="msg_send_btn" type="submit" onclick="toServer()"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                        </div>
                    </div>

            </div>
        </div>

    </div><p id="userId" style="display: none">${user.getId()}</p></div>
<script src="../script.js"></script>
</body>
</html>
