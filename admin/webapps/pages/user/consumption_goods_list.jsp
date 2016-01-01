<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#consumption-goods-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/user/consumption_goods_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
        columns: [
            {
                name: 'name',
                label: '名字',
                align: 'center',
                width: 100
            },
            {
                name: 'model',
                label: '型号',
                align: 'center',
                width: 140
            },
            {
                name: 'information',
                label: '额外信息',
                align: 'center',
                width: 140
            },
            {
                name: 'totalStock',
                label: '库存总量',
                align: 'center',
                width: 140
            },
            {
                name: 'usageList',
                label: '库存情况列表',
                align: 'center',
                width: 140,
                render: function(value){
                    return value.name;
                }
            }
        ],
        editUrl: '<lms:path/>/user/editdata?type=consumption_goods',
        delUrl: '<lms:path/>/user/deletedata?type=consumption_goods',
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
    <table id="consumption-goods-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
    </table>
</div>


