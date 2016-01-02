<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
  $('#lab_usage-datagrid').datagrid({
    showToolbar: true,
    local: 'local',
    dataUrl: '<lms:path/>/labroom/list_usage?labRoomId=<c:out value="${labRoomId}"/>',
    dataType: 'json',
    sortAll: true,
    filterAll: true,
    showLinenumber: true,
    columns: [
      {
        name: 'targetDate',
        label: '目标日期',
        align: 'center',
        width: 140,
          render: function(value) {
              var d = new Date(value);
              console.log(d);
              return d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
          }
      },
      {
        name: 'targetTime',
        label: '目标时间段',
        align: 'center',
        width: 140,
        render: function(value){
          if(value == 0){
            return "08:00-11:50";
          } else if(value==1){
            return "14:00-17:40";
          } else{
            return "19:00-21:00";
          }
        }
      },
      {
        name: 'status',
        label: '所处状态',
        align: 'center',
        width: 140,
        render: function(value){
          if(value == 1){
            return "预约状态";
          } else{
            return "完成状态";
          }
        }
      },
      {
        name: 'experimentLession',
        label: '所属实验课',
        align: 'center',
        width: 140,
        render: function(value){
          return value.experimentProject.name;
        }
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
  <table id="lab_usage-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
  </table>
</div>


