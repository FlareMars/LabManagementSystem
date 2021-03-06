<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#students-datagrid').datagrid({
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
                label: '学号',
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
                name: 'currentClassName',
                label: '所在班级',
                align: 'center',
                width: 100,
                type: 'select',
                items: function() {
                    return $.getJSON('<lms:path/>/classes/list_all_classes');
                },
                render: function(value) {
                    return value.substring(0,value.indexOf('_'));
                }

            }
        ],
        editUrl: '<lms:path/>/user/editdata?type=student',
        delUrl: '<lms:path/>/user/deletedata?type=student',
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
    <table id="students-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
    </table>
</div>


