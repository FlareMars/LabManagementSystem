<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $('#consumption-goods-usage-datagrid').datagrid({
    showToolbar: true,
    local: 'local',
    dataUrl: '<lms:path/>/consumption_goods/consumption_goods_usage_list?goodsId=<c:out value="${goodsId}"/>',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    showLinenumber: true,
    gridTitle: '<c:out value="${name}"/>消耗品使用情况详细列表',
    columns: [
      {
        name: 'type',
        label: '类型',
        align: 'center',
        width: 50,
        render: function(type){
          if(type==1){
            return "收入";
          } else {
            return "转出";
          }
        }
      },
      {
        name: 'quantity',
        label: '数量',
        align: 'center',
        width: 50
      },
      {
        name: 'detail',
        label: '详细信息',
        align: 'center'
      },
      {
        name: 'createTimeStr',
        label: '时间',
        align: 'center',
        width: 140
      }
    ],
    paging: false,
    linenumberAll: true,
    fullGrid:true
  })
</script>

<div class="bjui-pageContent tableContent" >
  <table id="consumption-goods-usage-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
  </table>
</div>


