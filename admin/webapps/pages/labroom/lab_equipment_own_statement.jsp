<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $('#lab_equipment_usage-datagrid').datagrid({
    showToolbar: true,
    local: 'local',
      gridTitle: '<c:out value="${title}"/>',
    dataUrl: '<lms:path/>/labroom/lab_equipment_own_statement?labRoomId=<c:out value="${labRoomId}"/>',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    showLinenumber: true,
    columns: [
      {
        name: 'equipment',
        label: '设备名字',
        align: 'center',
        width: 100,
          render: function(value){
              return value.name;
          }
      },
      {
        name: 'equipment',
        label: '设备型号',
        align: 'center',
        width: 140,
          render: function(value){
              return value.model;
          }
      },
      {
        name: 'equipment',
        label: '设备编号',
        align: 'center',
        width: 140,
          render: function(value){
              return value.number;
          }
      },
      {
        name: 'equipment',
        label: '设备功能简介',
        align: 'center',
        width: 140,
          render: function(value){
              return value.function;
          }
      }
    ],
    paging: false,
    linenumberAll: true,
    fullGrid:true
  })
</script>

<div class="bjui-pageContent tableContent" >
  <table id="lab_equipment_usage-datagrid" data-width="100%" data-height="90%" class="table table-bordered table-hover table-striped table-top">
  </table>    <button type="button" class="btn btn-blue" style="width: 100%;" data-toggle="dialog"
                      data-id="addEquipmentDialog" data-url="<lms:path/>/equipment/equipmentList?labRoomId=<c:out value="${labRoomId}"/>"
                      data-title="选择需要添加的仪器设备" data-mask="true"  data-height="460" data-width="1000">添加设备仪器</button>
</div>



