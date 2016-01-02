<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="lms" uri="/lms-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $('#labrooms-datagrid').datagrid({
        showToolbar: true,
        toolbarItem: 'add, edit, cancel, | , save, del',
        local: 'local',
        dataUrl: '<lms:path/>/labroom/labroom_list',
        dataType: 'json',
        sortAll: true,
        filterAll: true,
        columns: [
            {
                name: 'department',
                label: '所在大楼',
                align: 'center',
                width: 100
            },
            {
                name: 'roomNumber',
                label: '课室号',
                align: 'center',
                width: 140
            },
            {
                name: 'description',
                label: '课室简介',
                align: 'center',
                width: 140
            },
            {
                name: 'type',
                label: '实验室类别',
                align: 'center',
                width: 140,
                type: 'select',
                items: [{0:'计算机实验室'}, {1:'物理实验室'}, {2:'生物实验室'}, {3:'化学实验室'}]
            },
            {
                name: 'manager',
                label: '管理员',
                align: 'center',
                width: 140,
                type: 'select',
                render: function(value){
                    return value.realName;
                },
                items: function(){
                    return $.getJSON('<lms:path/>/roommanager/list_all_managers')
                }
            },
            {
                name: 'id',
                label: '使用情况列表',
                align: 'center',
                width: 100,
                render: function(id) {
                    var url = '<lms:path/>/labroom/labroom_usage_page?labRoomId=' + id;
                    return "<a data-toggle='ajaxload' data-target='#usageTable' href='" + url + "'>查看</a>"
                }

            },
            {
                name: 'equipmentList',
                label: '设备仪器列表',
                align: 'center',
                width: 100,
                render: function(id) {
                    var url = '<lms:path/>/labroom/lab_equipment_own_statement?labRoomId=' + id;
                    return "<a data-toggle='ajaxload' data-target='#equipmentUsageTable' href='" + url + "'>查看</a>"
                }
            },
            {
                name: 'consumptionGoodsList',
                label: '低值品列表',
                align: 'center',
                width: 100,
                render: function(value){
                    return value.name;
                }
            }
        ],
        editUrl: '<lms:path/>/labroom/editdata?type=labroom',
        delUrl: '<lms:path/>/labroom/deletedata?type=labroom',
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
    <table id="labrooms-datagrid" data-width="100%" data-height="100%" class="table table-bordered table-hover table-striped table-top">
    </table>
</div>

<div class="bjui-layout" style="top: 350px;height:250px" id="usageTable"></div>
<div class="bjui-layout" style="top: 350px;height:250px" id="equipmentUsageTable"></div>


