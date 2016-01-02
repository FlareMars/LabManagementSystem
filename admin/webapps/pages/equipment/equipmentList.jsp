<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    function selectEquipment(id) {
        $.post("<lms:path/>/labroom/addEquipmentAction",{equipmentId:id,labRoomId:'${labRoomId}'});
        $(this).navtab('refresh').dialog('closeCurrent');
    }
</script>
<div class="bjui-pageHeader">
  <form id="pagerForm" data-toggle="ajaxsearch" action="<lms:path/>/equipment/equipmentList" method="post">
    <div class="bjui-searchBar">
      <label>设备名字：</label><input type="text" value="" name="name" size="30" />&nbsp;
        <input type="hidden" value="${labRoomId}" name="labRoomId">
      <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
      <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>&nbsp;
    </div>
  </form>
</div>
<div class="bjui-pageContent tableContent">
  <table class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%">
    <thead>
    <tr>
      <th align="center" width="25">No.</th>
      <th align="center" data-order-field="name">名字</th>
      <th align="center" >型号</th>
      <th align="center" >编号</th>
      <th align="center" >当前位置</th>
      <th align="center" width="74">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${equipmentPage.content}" var="obj" varStatus="status">
      <tr data-id="${obj.id}" data-index="${status.index}">
        <td align="center">${status.index + 1}</td>
        <td align="center">${obj.name}</td>
        <td align="center">${obj.model}</td>
        <td align="center">${obj.number}</td>
        <td align="center">${obj.position}</td>
        <td align="center">
          <a href="javascript:;" onclick="selectEquipment('<c:out value="${obj.id}"/>')"
             data-toggle="lookupback" data-args="{equimentId:'<c:out value="${obj.id}"/>'}" class="btn btn-blue" title="选择本项" data-icon="check">选择</a>
        </td>
      </tr>
    </c:forEach>

    </tbody>
  </table>
</div>
