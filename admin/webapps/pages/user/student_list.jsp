<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#teachers-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/user/student_list',
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
                name: 'studentNumber',
                label: '工资号',
                align: 'center',
                width: 140
            },
            {
                name: 'studentPassword',
                label: '电子账号密码',
                align: 'center',
                width: 140
            },
            {
                name: 'current_class_id',
                label: '所在班级',
                align: 'center',
                width: 100,
                render: function(value) {
                    return value.name;
                }

            }
        ],
        editUrl: '<lms:path/>/worker/editdata',
        delUrl: '<lms:path/>/worker/deletedata',
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


