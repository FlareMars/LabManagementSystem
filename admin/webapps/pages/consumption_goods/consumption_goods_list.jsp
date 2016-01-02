<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#consumption-goods-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/consumption_goods/consumption_goods_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
        columns: [
            {
                name: 'name',
                label: '名字',
                align: 'center',
                width: 100,
                render: function(value) {
                    var name = value.substring(0,value.indexOf('_'));
                    var id = value.substring(value.indexOf('_') + 1);
                    return "<a href='<lms:path/>/consumption_goods/usage_statement?goodsId=" + id + "'>" + name + "</a>"
                }
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
                edit: false,
                width: 50
            },
            {
                name: 'id',
                label: '转移消耗品',
                align: 'center',
                quicksort: false,
                width: 40,
                edit: false,
                add: false,
                render: function(id) {
                    var url = '<lms:path/>/pages/consumption_goods/modifyStock.jsp?goodsId=' + id;
                    return "<button data-toggle='dialog' data-id='modifyStockDialog' data-width='400' data-title='消耗品库存更改' data-mask='true' data-url='" + url +  "'>操作</button>";
                }
            }
        ],
        editUrl: '<lms:path/>/consumption_goods/editdata',
        delUrl: '<lms:path/>/consumption_goods/deletedata',
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


