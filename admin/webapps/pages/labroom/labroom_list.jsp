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
                name: 'id',
                hide: true
            },
            {
                name: 'department',
                label: '所在大楼',
                align: 'center',
                rule:'required',
                width: 100
            },
            {
                name: 'roomNumber',
                label: '课室号',
                align: 'center',
                rule:'required',
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
                items: [{0:'计算机实验室'}, {1:'物理实验室'}, {2:'生物实验室'}, {3:'化学实验室'},{4:'仓库'}]
            },
            {
                name: 'managerInfo',
                label: '管理员',
                align: 'center',
                width: 140,
                type: 'select',
                render: function(value) {
                    return value.substring(0,value.indexOf('_'));
                },
                items: function(){
                    return $.getJSON('<lms:path/>/roommanager/list_all_managers')
                }
            },
            {
                name: 'id',
                label: ' ',
                align: 'center',
                width: 320,
                edit: false,
                add: false,
                render: function(id) {
                    var urlUsage = '<lms:path/>/labroom/labroom_usage_page?labRoomId=' + id;
                    var urlEquipment = '<lms:path/>/labroom/lab_equipment_own_page?labRoomId=' + id;
                    var urlConsumptionGoods = '<lms:path/>/labroom/lab_consumption_goods_own_page?labRoomId=' + id;
                    return "<button data-toggle='ajaxload' class='editBtn btn btn-red' data-target='#container' href='" + urlUsage + "'>使用情况列表</button>" + "<i> </i>" +
                            "<button data-toggle='ajaxload' class='editBtn btn btn-blue' data-target='#container' href='" + urlEquipment + "'>设备仪器列表</button>" + "<i> </i>" +
                            "<button data-toggle='ajaxload' class='editBtn btn btn-green' data-target='#container' href='" + urlConsumptionGoods + "'>低值品列表</button>"
                }

            }
        ],
        editUrl: '<lms:path/>/labroom/editdata',
        delUrl: '<lms:path/>/labroom/deletedata',
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

<div class="bjui-layout" style="top: 350px;height:300px" id="container"></div>


