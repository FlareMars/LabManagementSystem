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
                name: 'function',
                label: '设备功能简介',
                align: 'center',
                width: 140
            },
            {
                name: 'labRoom',
                label: '所在位置',
                align: 'center',
                width: 140,
                render: function(value){
                    return value.name;
                }
            },
            {
                name: 'usages',
                label: '设备使用情况',
                align: 'center',
                width: 100,
                render: function(value) {
                    return value.name;
                }

            }
        ],
        editUrl: '<lms:path/>/equipment/editdata?type=equipment',
        delUrl: '<lms:path/>/equipment/deletedata?type=equipment',
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


