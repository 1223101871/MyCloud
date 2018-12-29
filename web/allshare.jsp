<%--
  Created by IntelliJ IDEA.
  User: 夏晨阳
  Date: 2018/12/19
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="Bookmark" href="/favicon.ico">
    <link rel="Shortcut Icon" href="/favicon.ico"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="h-ui/lib/html5shiv.js"></script>
    <script type="text/javascript" src="h-ui/lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="h-ui/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css"/>
    <!--[if IE 6]>
    <script type="text/javascript" src="h-ui/lib/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>共享列表</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 所有分享
    <span class="c-gray en">&gt;</span> 共享列表
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
       href="javascript:location.reload(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <form action="BaseServlet" method="post" class="text-c"> 日期范围：
        <input type="hidden" name="method" value="search">
        <input type="hidden" name="action" value="allshare">
        <input type="hidden" name="page" value=${page.currentPage}>
        <input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin"
               class="input-text Wdate" style="width:120px;">
        -
        <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax"
               class="input-text Wdate" style="width:120px;">
        <input type="text" class="input-text" style="width:250px" placeholder="输入文件名称" id="" name="filename">
        <button type="submit" class="btn btn-success" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="doDelete()" class="btn btn-danger radius">
                <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
        </span>
    </div>


    <%--<button class="btn btn-danger radius" onclick="doDelete()">批量删除</button>--%>

    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <th scope="col" colspan="9">共享列表</th>
        </tr>
        <tr class="text-c">
            <th width="25"><input type="checkbox" name="allCheck" id="allCheck" onclick="CheckAll(allCheck)"></th>
            <th width="40">ID</th>
            <th width="150">文件名</th>
            <th width="90">大小</th>
            <th width="150">修改日期</th>
            <th width="130">下载次数</th>
            <th width="100">下载</th>
        </tr>
        </thead>

        <tbody>
        <form id="myform" class="cl pd-5 bg-1 bk-gray mt-20" action="BaseServlet" method="post">
            <input type="hidden" name="method" value="delete">
            <input type="hidden" name="action" value="allshare">
            <input type="hidden" name="page" value=${page.currentPage}>

            <c:forEach items="${filelist}" var="userFile">
                <tr class="text-c">
                    <th scope="row"><input type="checkbox" name="ids" id="ids" value="${userFile.id }"></th>
                        <%--<td><input type="checkbox" value="${userFile.id}" name="fileIds"></td>--%>
                    <td width="40">${userFile.id}</td>
                    <td width="150">${userFile.filename }</td>
                    <td width="90">${userFile.fileSize }</td>
                    <td width="150">${userFile.createTime }</td>
                    <td width="130">${userFile.count }</td>
                    <td width="100"><a class="btn btn-primary" href="/DownLoadServlet?id=${userFile.id }"><i
                            class="fa fa-download"></i>下载</a></td>
                </tr>
            </c:forEach>
        </form>

        </tbody>
    </table>
    <br>
    <div align="center">

        <c:if test="${page.currentPage>1 }">
            <a href="/BaseServlet?method=page&action=allshare&page=${page.currentPage-1}">上一页</a>
        </c:if>
        <a href="/BaseServlet?method=page&action=allshare&page=${1}">首页</a>
        <c:forEach begin="1" end="${page.totalPage }" step="1" var="i">
            <c:if test="${page.currentPage==i }">
                <a href="/BaseServlet?method=page&action=allshare&page=${i}"><font color="#ff0000">${i}</font></a>
            </c:if>
            <c:if test="${page.currentPage!=i }">
                <a href="/BaseServlet?method=page&action=allshare&page=${i}">${i}</a>
            </c:if>
        </c:forEach>
        <a href="/BaseServlet?method=page&action=allshare&page=${page.totalPage}">末页</a>
        <c:if test="${page.currentPage< page.totalPage }">
            <a href="/BaseServlet?method=page&action=allshare&page=${page.currentPage+1}">下一页</a>
        </c:if>
    </div>


</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="h-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="h-ui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="h-ui/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="h-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="h-ui/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">


    function doDelete() {
        var inputs = document.getElementsByName("ids");
        var myform = document.getElementById("myform");
        var flag = false;
        var ids = "";
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].checked) {
                flag = true;
            }
        }
        if (!flag) {
            alert("Please select the data you want to delete");
            return;
        }
        if (!confirm("Are you confirm to delete?")) return;
        myform.submit();
    }

    function CheckAll(allCheck) {
        var ids = document.getElementById("ids");
        if (allCheck.checked) {
            for (i = 0; i < ids.length; i++) {
                ids[i].checked = true;
            }
        }
        else {
            for (i = 0; i < ids.length; i++) {
                ids[i].checked = false;
            }
        }

    }

</script>
</body>
</html>