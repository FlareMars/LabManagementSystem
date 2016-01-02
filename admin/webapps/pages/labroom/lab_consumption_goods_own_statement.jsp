<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $('#lab_consumption_goods_usage-datagrid').datagrid({
    showToolbar: true,
    gridTitle: '<c:out value="${title}"/>',
    local: 'local',
    dataUrl: '<lms:path/>/labroom/lab_consumption_goods_own_statement?labRoomId=<c:out value="${labRoomId}"/>',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    showLinenumber: true,
    columns: [
      {
        name: 'consumptionGoods',
        label: '消耗品名字',
        align: 'center',
        width: 100,
          render: function(value){
              return value.name;
          }
      },
      {
        name: 'consumptionGoods',
        label: '消耗品型号',
        align: 'center',
        width: 140,
          render: function(value){
              return value.model;
          }
      },
      {
        name: 'consumptionGoods',
        label: '额外信息',
        align: 'center',
        width: 140,
          render: function(value){
              return value.information;
          }
      },
      {
        name: 'quantity',
        label: '库存',
        align: 'center',
        width: 140
      },
      {
        name: 'id',
        label: ' ',
        align: 'center',
        width: 60,
        render: function(id){
            var url = '<lms:path/>/labroom/modifyConsumptionGoodsStock?middleId=' + id ;
          return "<button data-toggle='dialog' data-id='modifyConsumptionGoodsStockDialog' data-width='400' data-title='消费品纳入' data-mask='true' class='editBtn btn btn-green' data-url='"+ url+"'>消费品纳入</button>";
        }
      }
    ],
    paging: false,
    linenumberAll: true,
    fullGrid:true
  })
</script>

<div class="bjui-pageContent tableContent" >
    <table id="lab_consumption_goods_usage-datagrid" data-width="100%" data-height="90%" class="table table-bordered table-hover table-striped table-top">
    </table>
    <button type="button" class="btn btn-blue" style="width: 100%;" data-toggle="dialog"
            data-id="addConsumptionGoodsDialog" data-url="<lms:path/>/consumption_goods/goodsList?labRoomId=<c:out value="${labRoomId}"/>"
            data-title="选择需要添加的种类" data-mask="true"  data-height="460" data-width="1000">添加消耗品种类</button>
</div>


