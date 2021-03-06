<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#teachers-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/user/teacher_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
        columns: [
            {
                name: 'realName',
                label: '姓名',
                align: 'center',
                width: 100
            },
            {
                name: 'username',
                label: '用户名',
                align: 'center',
                width: 140
            },
            {
                name: 'password',
                label: '密码',
                align: 'center',
                width: 140
            },
            {
                name: 'teacherNumber',
                label: '工资号',
                align: 'center',
                width: 140
            },
            {
                name: 'teacherPassword',
                label: '电子账号密码',
                align: 'center',
                width: 140
            },
            {
                name: 'currentAcademy',
                label: '所在学院',
                align: 'center',
                width: 100,
                type: 'select',
                items: function(){
                    return $.getJSON('<lms:path/>/academy/list_all_academies');
                },
                render: function(value) {
                    return value.substring(0,value.indexOf('_'));
                }

            }
        ],
        editUrl: '<lms:path/>/user/editdata?type=teacher',
        delUrl: '<lms:path/>/user/deletedata?type=teacher',
        delPK: 'id',
        paging: false,
        linenumberAll: true,
        fullGrid:true,
        afterSave: function () {
            $(this).navtab('refresh')
        },
        afterDelete: function() {
            $(this).navtab('refresh')
        }
    })
</script>

<div class="bjui-pageContent tableContent" >
    <table id="teachers-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
    </table>
</div>


