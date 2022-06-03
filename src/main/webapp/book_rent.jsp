<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="keywords"  content = "图书 java jsp"/>
    <meta http-equiv="author" content="phenix"/>
    <link rel="stylesheet" type="text/css" href="./Style/skin.css" />
    <script src="Js/jquery-3.3.1.min.js"></script>
    <script>

        function getCurrentDate(){
            var dateObj = new Date();
            var year = dateObj.getFullYear();
            var month = dateObj.getMonth() + 1;
            var date = dateObj.getDate();
            var dateStr = year +"-"+ (month>=10?month:"0"+month)+"-"+(date>=10?date:"0"+date);
            return dateStr;
        }

        function getBackDate(count){
            var dateObj = new Date();
            var mills = dateObj.getMilliseconds();
            mills += count*24*60*60*1000;
            dateObj.setMilliseconds(mills);

            var year = dateObj.getFullYear();
            var month = dateObj.getMonth()+1;
            var date = dateObj.getDate();
            var dateStr = year+"-"+(month>=10?month:"0"+month)+"-"+(date>=10?date:"0"+date);
            return dateStr;
        }


        $(function (){
            $("#btnQueryBook").prop("disabled","disabled");
            $("#btnSubmit").prop("disabled","disabled");

            var member = null;

            $("#btnQuery").click(function (){
                var content = $("#memberId").val();
                if(content==null){
                 alert("输入用户号！");
                 return;
                }
                var url = "member.let?type=doajax&idn=" + content;
                $.get(url,function (data,status){
                    console.log(data);
                    member = JSON.parse(data);
                    console.log(member.name);
                    $("#name").val(member.name);
                    $("#type").val(member.type.name);
                    $("#amount").val(member.type.amount);
                    $("#balance").val(member.balance);
                });
                $(this).prop("disabled");
                $("#btnQueryBook").removeAttr("disabled");
            });

            var bookNameList = new Array();
            $("#btnQueryBook").click(function (){
                var name = $("#bookContent").val();
                var url = "book.let?type=doajax&name=" + name;
                $.get(url,function (data,status){
                    if(data === "{}"){
                        alert("当前书籍不存在，查找失败");
                        return;
                    }
                    if(bookNameList.indexOf(name)>=0)
                    {
                        alert("书名被添加了，添加失败");
                        return;
                    }
                    bookNameList.push(name);
                        // <tr align="center" class="d">
                        //     <td><input class="ck" type="checkbox" value="" /></td>
                        //     <td>罗小黑战记</td>
                        //     <td>2010-10-01</td>
                        //     <td>2010-10-31</td>
                        //     <td>北京联合出版社</td>
                        //     <td>东区-01-02</td>
                        //     <td>39.9</td>
                        // </tr>
                        var book = JSON.parse(data);

                        var tr = $("<tr align=\"center\" class=\"d\">") ;

                        var tdCheck = $("<td><input class=\"ck\" type=\"checkbox\" value=\""+ book.id +"\" checked/></td>");
                        var tdName = $("<td>"+ book.name +"</td>");
                        var tdRentDate = $("<td>"+getCurrentDate()+"</td>");
                        var tdBackDate = $("<td>"+getBackDate(member.type.keepDay)+"</td>");
                        var tdPublish = $("<td>"+ book.publish +"</td>");
                        var tdAddress = $("<td>"+ book.address +"</td>");
                        var tdPrice = $("<td>"+ book.price +"</td>");
                        var end = $("</tr>");

                        tr.append(tdCheck);
                        tr.append(tdName);
                        tr.append(tdRentDate);
                        tr.append(tdBackDate);
                        tr.append(tdPublish);
                        tr.append(tdAddress);
                        tr.append(tdPrice);
                        tr.append(end);

                        $("#tdBook").append(tr);
                        $("#bookContent").val("");
                        $("#btnSubmit").removeAttr("disabled");

                        //全选
                        $("#ckAll").click(function (){
                            $(".ck").prop("checked", $(this).prop("checked"));
                        })

                    $("#btnSubmit").click(function (){
                        var count = 0;
                        var ids = new Array();
                        $(".ck").each(function (){
                            if($(this).prop("checked")){
                                ids.push($(this).val());
                                count ++;
                            }
                        });
                        if(count==0){
                            alert("请选择借阅书籍");
                            return;
                        }
                        if(count>member.type.amount){
                            alert("选择书籍超限");
                            return;
                        }
                        location.href="record.let?type=add&mid="+member.id+"&ids="+ids.join("_");

                    })



                })
            })

        });
    </script>

</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- 头部开始 -->
    <tr>
        <td width="17" valign="top" background="./Images/mail_left_bg.gif">
            <img src="./Images/left_top_right.gif" width="17" height="29" />
        </td>
        <td valign="top" background="./Images/content_bg.gif">

        </td>
        <td width="16" valign="top" background="./Images/mail_right_bg.gif"><img src="./Images/nav_right_bg.gif" width="16" height="29" /></td>
    </tr>
    <!-- 中间部分开始 -->
    <tr>
        <!--第一行左边框-->
        <td valign="middle" background="./Images/mail_left_bg.gif">&nbsp;</td>
        <!--第一行中间内容-->
        <td valign="top" bgcolor="#F7F8F9">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <!-- 空白行-->
                <tr><td colspan="2" valign="top">&nbsp;</td><td>&nbsp;</td><td valign="top">&nbsp;</td></tr>
                <tr>
                    <td colspan="4">
                        <table>
                            <tr>
                                <td width="100" align="center"><img src="./Images/mime.gif" /></td>
                                <td valign="bottom"><h3 style="letter-spacing:1px;">常用功能 > 图书借阅 </h3></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <!-- 一条线 -->
                <tr>
                    <td height="20" colspan="4">
                        <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                            <tr><td></td></tr>
                        </table>
                    </td>
                </tr>
                <!-- 会员信息开始 -->
                <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="96%">
                        <fieldset>
                            <legend>查询会员</legend>
                            <table width="100%" border="0" class="cont"  >
                                <tr>
                                    <td width="8%" class="run-right"> 会员编号</td>
                                    <td colspan="7"><input class="text" type="text" id="memberId" name="memberId"/>
                                        <input type="button" id="btnQuery" value="确定" style="width: 80px;"/></td>

                                    </td>

                                </tr>
                                <tr>
                                    <td width="8%" class="run-right">会员名称</td>
                                    <td width="17%"><input class="text" type="text"  id="name" disabled/></td>
                                    <td width="8%" class="run-right">会员类型:</td>
                                    <td width="17%"><input class="text" type="text"  id="type" disabled/></td>
                                    <td width="8%" class="run-right">可借数量</td>
                                    <td width="17%"><input class="text" type="text"  id="amount" disabled/></td>
                                    <td width="8%" class="run-right">账户余额</td>
                                    <td width="17%"><input class="text" type="text"  id="balance" disabled/></td>
                                </tr>
                            </table>
                        </fieldset>
                    </td>
                    <td width="2%">&nbsp;</td>
                </tr>

                <!--空行-->
                <tr>
                    <td height="40" colspan="3">
                    </td>
                </tr>

                <!--图书搜索条-->
                <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="96%">
                        <fieldset>
                            <legend>查询图书</legend>
                            <table width="100%" border="0" class="cont"  >
                                <tr>
                                    <td colspan="8">

                                        请输入:&nbsp;&nbsp;<input class="text" type="text" id="bookContent" name="bookContent" placeholder="输入条形码/书名"/>
                                        <input type="button" id="btnQueryBook" value="确定" style="width: 80px;"/>
                                        <input type="button" id="btnSubmit" value="完成借阅" style="width: 80px;"/>
                                    </td>

                                </tr>

                            </table>
                        </fieldset>
                    </td>
                    <td width="2%">&nbsp;</td>
                </tr>
                <tr><td height="20" colspan="3"></td></tr>
                <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="96%">
                        <table width="100%">
                            <tr>
                                <td colspan="2">
                                    <form action="" method="">
                                        <table width="100%"  class="cont tr_color" id="tdBook">
                                            <tr>
                                                <th><input id="ckAll" type="checkbox" value="" checked/>全选/全不选</th>
                                                <th>书籍名</th>
                                                <th>借阅时间</th>
                                                <th>应还时间</th>
                                                <th>出版社</th>
                                                <th>书架</th>
                                                <th>定价(元)</th>
                                            </tr>



                                        </table>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="2%">&nbsp;</td>
                </tr>
                <!-- 产品列表结束 -->
                <tr>
                    <td height="40" colspan="4">
                        <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                            <tr><td></td></tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="51%" class="left_txt">
                        <img src="./Images/icon_mail.gif" width="16" height="11"> 客户服务邮箱：2087924818@qq.com<br />
                    </td>
                    <td>&nbsp;</td><td>&nbsp;</td>
                </tr>
            </table>
        </td>
        <td background="./Images/mail_right_bg.gif">&nbsp;</td>
    </tr>
    <!-- 底部部分 -->
    <tr>
        <td valign="bottom" background="./Images/mail_left_bg.gif">
            <img src="./Images/buttom_left.gif" width="17" height="17" />
        </td>
        <td background="./Images/buttom_bgs.gif">
            <img src="./Images/buttom_bgs.gif" width="17" height="17">
        </td>
        <td valign="bottom" background="./Images/mail_right_bg.gif">
            <img src="./Images/buttom_right.gif" width="16" height="17" />
        </td>
    </tr>
</table>
</body>
</html>