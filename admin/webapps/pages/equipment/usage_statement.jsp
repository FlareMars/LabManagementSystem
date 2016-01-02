<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $('#consumption-goods-usage-datagrid').datagrid({
    showToolbar: true,
    local: 'local',
    dataUrl: '<lms:path/>/equipment/equipment_usage_list?equipmentId=<c:out value="${equipmentId}"/>',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    showLinenumber: true,
    gridTitle: '<c:out value="${name}"/>使用情况详细列表',
    columns: [
      {
        name: 'usageType',
        label: '类型',
        align: 'center',
        width: 50,
        render: function(value) {
          switch (value) {
            case 1:
                    return '录入系统';
                    break;
            case 2:
                    return '转移位置';
                    break;
            case 3:
                    return '仪器维修';
                    break;
            case 4:
                    return '仪器报废';
                    break;
          }
        }
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


