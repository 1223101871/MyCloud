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
    <title>我的网盘</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 图片管理
    <span class="c-gray en">&gt;</span> 图片列表
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
       href="javascript:location.reload(location.href);" title="刷新">
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="page-container">
    <form action="BaseServlet" method="post" class="text-c"> 日期范围：
        <input type="hidden" name="method" value="search">
        <input type="hidden" name="action" value="mydisk">
        <input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin"
               class="input-text Wdate" style="width:120px;">
        -
        <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax"
               class="input-text Wdate" style="width:120px;">
        <input type="text" class="input-text" style="width:250px" placeholder="输入文件名称" name="filename">
        <button type="submit" class="btn btn-success" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="doDelete()" class="btn btn-danger radius">
                <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
            <a href="javascript:;" onclick="doUpload()" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 上传图片</a>
        </span>
    </div>

    <table class="table table-border table-bordered table-bg table-hover table-sort">
        <thead>
        <tr class="text-c">
            <th width="40"><input name="" type="checkbox" value=""></th>
            <th width="80">ID</th>
            <th width="300">封面</th>
            <th>图片名称</th>
            <th width="150">图片大小</th>
            <th width="150">更新时间</th>
            <th width="60">分享状态</th>
            <th width="100">下载</th>
        </tr>
        </thead>
        <form class="cl pd-5 bg-1 bk-gray mt-20" action="BaseServlet" method="post" id="myform">
            <input type="hidden" name="method" value="delete">
            <input type="hidden" name="action" value="mydisk">
            <tbody>
            <c:if test="${empty piclist}">
                <tr>
                    <td colspan="9" align="center">没有图片</td>
                </tr>
            </c:if>
            <c:forEach items="${piclist}" var="picture">
                <tr class="text-c">
                    <th scope="row"><input type="checkbox" name="ids" id="ids" value="${picture.id }"></th>
                        <%--<td><input type="checkbox" value="${userFile.id}" name="fileIds"></td>--%>
                    <td width="40">${picture.id}</td>
                    <td><a href="javascript:;"><img width="210" class="picture-thumb" src=${picture.path}></a>
                    </td>
                    <td width="150">${picture.picname }</td>
                    <td width="90">${picture.fileSize }</td>
                    <td width="150">${picture.createTime }</td>
                    <td>
                        <c:choose>
                            <c:when test="${userFile.isShared==1 }">
                                已分享
                            </c:when>
                            <c:otherwise>
                                <input type="button" value="分享" class="btn btn-primary"
                                       onclick="share(${userFile.id })"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                        <%--<td width="100"><a class="btn btn-primary" href="icloud/download?id=${userFile.id }"><i class="fa fa-download"></i>下载</a></td>--%>
                    <td width="100"><a class="btn btn-primary" href="/DownLoadServlet?id=${userFile.id }"><i
                            class="fa fa-download"></i>下载</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </form>
    </table>


</div>

<!--上传  模态框（Modal） -->
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <form action="UploadServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="from" value="admin">
        <input type="hidden" name="action" value="pic">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">上传文件</h4>
                </div>
                <div class="modal-body">
                    <input type="file" name="file" value="上传文件">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        关闭
                    </button>
                    <input type="submit" class="btn btn-primary" value="确定上传"/>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </form>
</div>
<!-- /.modal -->

<div class="modal fade" id="shareModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    确认
                </h4>
            </div>
            <div class="modal-body">
                确定要分享？
            </div>
            <div class="modal-footer">
                <form action="ShareServlet" id="share_form" method="post">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <input class="hidden" value="share" name="method">
                    <input class="hidden" value="takeshare" name="action">
                    <input class="hidden" id="share_id" name="id">
                    <input type="submit" class="btn btn-primary" value="确定"/>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
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

    function doUpload() {
        $("#uploadModal").modal('show');
    }

    function share(id) {
        document.getElementById("share_id").value = id;
        $("#shareModal").modal('show');
    }
</script>
</body>
</html>