<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#equipments-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/equipment/equipment_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
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
                name: 'number',
                label: '数量',
                align: 'center',
                width: 100
            },
            {
                name: 'function',
                label: '设备功能简介',
                align: 'center',
                width: 140
            },
            {
                name: 'position',
                label: '所在位置',
                align: 'center',
                width: 140,
                type: 'select',
                items: function(){
                    return $.getJSON('<lms:path/>/labroom/list_all_labrooms');
                }
            },
            {
                name: 'id',
                label: ' ',
                width: 120,
                align: 'center',
                render: function(value) {
                    var url = '<lms:path/>/pages/equipment/doUsage.jsp?equipmentId=' + value;

                    return "<button data-toggle='ajaxload' class='editBtn btn btn-green' data-target='#usageTable' href='<lms:path/>/equipment/usage_statement?equipment=" + value + "'>" + "使用情况" + "</button>";// +
                            //"<button data-toggle='dialog' class='btn btn-red' data-id='handleEquipmentDialog' data-width='400' data-title='操作仪器设备' data-mask='true' data-url='" + url +  "'>操作</button>";

                }
            }
        ],
        editUrl: '<lms:path/>/equipment/editdata',
        delUrl: '<lms:path/>/equipment/deletedata',
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
    <table id="equipments-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
    </table>
</div>

<div class="bjui-layout" style="top: 350px;height:250px" id="usageTable"></div>
