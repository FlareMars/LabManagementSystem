<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#system_notices-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/system_notice/system_notice_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
        columns: [
            {
                name:'id',
                hide: true
            },
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
                type: 'textarea',
                width: 140
            },
            {
                name: 'operatorName',
                label: '操作员',
                align: 'center',
                width: 140,
                type: 'select',
                items: function(){
                    return $.getJSON('<lms:path/>/roommanager/list_all_managers')
                },
                render: function(value) {
                    return value.substring(0,value.indexOf('_'));
                }
            }
        ],
        editUrl: '<lms:path/>/system_notice/editdata',
        delUrl: '<lms:path/>/system_notice/deletedata',
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


