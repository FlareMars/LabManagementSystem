<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%

%>
<script type="text/javascript" >
  function myCallback(json) {
    $(this).navtab('refresh').dialog('closeCurrent');
  }


</script>

<div class="bjui-pageContent">
  <form action="<lms:path/>/labroom/submitConsumptionGoodsStock" class="pageForm" data-toggle="validate" id="modifyStockRequest" data-callback="myCallback">
      <input type="hidden" name="middleId" value="${middleId}">
    <table class="table table-condensed table-hover">
      <tbody>
      <tr>
        <td>
          <label for="dialog_stockNum" class="control-label x90">库存量：</label>
          <label id="dialog_stockNum" >${stockNum}</label>
        </td></tr>
      <tr>
        <td>
          <label for="dialog_number" class="control-label x90">数量：</label>
          <input type="text" data-toggle="spinner" name="number" id="dialog_number" data-rule="required" size="10">
        </td></tr>
      <tr>
        <td>
          <label for="dialog_detail" class="control-label x90">详情：</label>
          <textarea name="detail" id="dialog_detail" data-rule="required" cols="20" rows="5"></textarea>
        </td></tr>
      </tbody>
    </table>
  </form>
</div>
<div class="bjui-pageFooter">
  <ul>
    <li><button type="button" class="btn-close">关闭</button></li>
    <li><button type="submit" class="btn-default">保存</button></li>
  </ul>
</div>