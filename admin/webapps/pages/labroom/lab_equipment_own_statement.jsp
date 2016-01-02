<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $('#lab_equipment_usage-datagrid').datagrid({
    showToolbar: true,
    local: 'local',
    dataUrl: '<lms:path/>/labroom/lab_equipment_own_statement?labRoomId=<c:out value="${labRoomId}"/>',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    showLinenumber: true,
    columns: [
      {
        name: 'name',
        label: '设备名字',
        align: 'center',
        width: 100
      },
      {
        name: 'model',
        label: '设备型号',
        align: 'center',
        width: 140
      },
      {
        name: 'number',
        label: '设备编号',
        align: 'center',
        width: 140
      },
      {
        name: 'function',
        label: '设备功能简介',
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
  <table id="lab_equipment_usage-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
  </table>
</div>


