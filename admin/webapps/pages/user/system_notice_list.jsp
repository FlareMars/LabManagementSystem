<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#system_notices-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/user/system_notice_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
        columns: [
            {
                name: 'title',
                label: '公告标题',
                align: 'center',
                width: 100
            },
            {
                name: 'content',
                label: '公告内容',
                align: 'center',
                width: 140
            },
            {
                name: 'operator',
                label: '操作员',
                align: 'center',
                width: 140,
                render: function(value){
                    return value.name;
                }
            }
        ],
        editUrl: '<lms:path/>/user/editdata?type=system_notice',
        delUrl: '<lms:path/>/user/deletedata?type=system_notice',
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
    <table id="system_notices-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
    </table>
</div>


