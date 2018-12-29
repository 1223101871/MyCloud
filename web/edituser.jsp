<%--
  Created by IntelliJ IDEA.
  User: 夏晨阳
  Date: 2018/12/19
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>

<head>
    <title>edit</title>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="static/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
    <link href="static/css/style.css" rel='stylesheet' type='text/css' />
    <link href="static/css/font-awesome.css" rel="stylesheet">
    <script src="static/js/jquery.min.js"></script>
    <link href="static/css/custom.css" rel="stylesheet">
    <link href="static/css/templatemo_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        function submit_form(){
            document.forms["search_form"].submit();
        }
    </script>
</head>

<body>
<div id="wrapper">
    <div class="">
        <div class="col-md-12">
            <h1 class="margin-bottom-15">密码修改</h1>
            <form class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form" action="<%=basePath%>icloud?method=admin&action=edit" method="post">
                <input type="hidden" name="action" value="editSubmit">
                <div class="form-group">
                    <div class="col-xs-12">
                        <div class="control-wrapper">
                            <label for="password" class="control-label fa-label"><i class="fa fa-lock fa-medium"></i></label>
                            <input type="text" required="true" class="form-control" id="password" name="password" placeholder="原密码">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <div class="control-wrapper">
                            <label for="password1" class="control-label fa-label"><i class="fa fa-lock fa-medium"></i></label>
                            <input type="password1" required="true" class="form-control" id="password1" name="password1" placeholder="新密码">
                        </div>
                    </div>
                </div>
                <div class="form-group">

                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <div class="control-wrapper">
                            <div class="text-center">
                                <input type="submit" value="确认修改" class="btn btn-info">
                            </div>
                        </div>
                        <div class="text-center">
                            <font color="green">${msgSuccess }</font>
                            <font color="red">${msgFail }</font>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->
</body>
</html>
