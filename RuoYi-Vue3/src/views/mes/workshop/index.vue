<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="工厂编码" prop="factoryCode">
        <el-input v-model="queryParams.factoryCode" placeholder="请输入工厂编码" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="工厂名称" prop="factoryName">
        <el-input v-model="queryParams.factoryName" placeholder="请输入工厂名称" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="车间编码" prop="workshopCode">
        <el-input v-model="queryParams.workshopCode" placeholder="请输入车间编码" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="车间名称" prop="workshopName">
        <el-input v-model="queryParams.workshopName" placeholder="请输入车间名称" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="车间状态" clearable style="width: 200px">
          <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建者" prop="createBy">
        <el-input v-model="queryParams.createBy" placeholder="请输入创建者" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker v-model="createTimeRange" value-format="YYYY-MM-DD" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="更新者" prop="updateBy">
        <el-input v-model="queryParams.updateBy" placeholder="请输入更新者" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="更新时间" style="width: 308px">
        <el-date-picker v-model="updateTimeRange" value-format="YYYY-MM-DD" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['mes:workshop:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['mes:workshop:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="Upload" @click="handleImport" v-hasPermi="['mes:workshop:import']">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mes:workshop:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="workshopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="工厂编码" align="center" prop="factoryCode" min-width="120" show-overflow-tooltip />
      <el-table-column label="工厂名称" align="center" prop="factoryName" min-width="160" show-overflow-tooltip />
      <el-table-column label="车间编码" align="center" prop="workshopCode" min-width="120" show-overflow-tooltip />
      <el-table-column label="车间名称" align="center" prop="workshopName" min-width="160" show-overflow-tooltip />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" min-width="110" show-overflow-tooltip />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateBy" min-width="110" show-overflow-tooltip />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="260" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)" v-hasPermi="['mes:workshop:query']">详情</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['mes:workshop:edit']">修改</el-button>
          <el-button v-if="scope.row.status === '1'" link type="primary" icon="CircleCheck" @click="handleStatusChange(scope.row, '0')" v-hasPermi="['mes:workshop:edit']">启用</el-button>
          <el-button v-else link type="primary" icon="CircleClose" @click="handleStatusChange(scope.row, '1')" v-hasPermi="['mes:workshop:edit']">停用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="workshopRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="选择工厂" prop="factoryId">
          <el-select v-model="form.factoryId" placeholder="请选择工厂" filterable clearable style="width: 100%">
            <el-option v-for="item in factoryOptions" :key="item.factoryId" :label="item.factoryCode + ' - ' + item.factoryName" :value="item.factoryId" />
          </el-select>
        </el-form-item>
        <el-form-item label="车间编码" prop="workshopCode">
          <el-input v-model="form.workshopCode" placeholder="请输入车间编码" maxlength="64" show-word-limit />
        </el-form-item>
        <el-form-item label="车间名称" prop="workshopName">
          <el-input v-model="form.workshopName" placeholder="请输入车间名称" maxlength="100" show-word-limit />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" maxlength="500" show-word-limit />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="车间详情" v-model="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="工厂编码">{{ detail.factoryCode }}</el-descriptions-item>
        <el-descriptions-item label="工厂名称">{{ detail.factoryName }}</el-descriptions-item>
        <el-descriptions-item label="车间编码">{{ detail.workshopCode }}</el-descriptions-item>
        <el-descriptions-item label="车间名称">{{ detail.workshopName }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="sys_normal_disable" :value="detail.status" />
        </el-descriptions-item>
        <el-descriptions-item label="备注">{{ detail.remark }}</el-descriptions-item>
        <el-descriptions-item label="创建者">{{ detail.createBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(detail.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新者">{{ detail.updateBy }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ parseTime(detail.updateTime) }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="detailOpen = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>

    <excel-import-dialog
      ref="importWorkshopRef"
      title="车间导入"
      action="/mes/workshop/importData"
      template-action="/mes/workshop/importTemplate"
      template-file-name="workshop_template"
      update-support-label="是否更新已经存在的车间数据"
      @success="getList"
    />
  </div>
</template>

<script setup name="Workshop">
import request from "@/utils/request"
import ExcelImportDialog from "@/components/ExcelImportDialog"

function listWorkshop(query) {
  return request({
    url: "/mes/workshop/list",
    method: "get",
    params: query
  })
}

function getWorkshop(workshopId) {
  return request({
    url: "/mes/workshop/" + workshopId,
    method: "get"
  })
}

function addWorkshop(data) {
  return request({
    url: "/mes/workshop",
    method: "post",
    data: data
  })
}

function updateWorkshop(data) {
  return request({
    url: "/mes/workshop",
    method: "put",
    data: data
  })
}

function changeWorkshopStatus(workshopId, status) {
  return request({
    url: "/mes/workshop/changeStatus",
    method: "put",
    data: {
      workshopId,
      status
    }
  })
}

function listFactoryOptions() {
  return request({
    url: "/mes/workshop/factoryOptions",
    method: "get"
  })
}

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = useDict("sys_normal_disable")

const workshopList = ref([])
const factoryOptions = ref([])
const open = ref(false)
const detailOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const total = ref(0)
const title = ref("")
const detail = ref({})
const createTimeRange = ref([])
const updateTimeRange = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    factoryCode: undefined,
    factoryName: undefined,
    workshopCode: undefined,
    workshopName: undefined,
    status: undefined,
    createBy: undefined,
    updateBy: undefined
  },
  rules: {
    factoryId: [
      { required: true, message: "所属工厂不能为空", trigger: "change" }
    ],
    workshopCode: [
      { required: true, message: "车间编码不能为空", trigger: "blur" },
      { max: 64, message: "车间编码长度不能超过64个字符", trigger: "blur" }
    ],
    workshopName: [
      { required: true, message: "车间名称不能为空", trigger: "blur" },
      { max: 100, message: "车间名称长度不能超过100个字符", trigger: "blur" }
    ],
    remark: [
      { max: 500, message: "备注长度不能超过500个字符", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

function buildQueryParams() {
  const params = proxy.addDateRange({ ...queryParams.value }, createTimeRange.value, "CreateTime")
  return proxy.addDateRange(params, updateTimeRange.value, "UpdateTime")
}

function getList() {
  loading.value = true
  listWorkshop(buildQueryParams()).then(response => {
    workshopList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function getFactoryOptions() {
  listFactoryOptions().then(response => {
    factoryOptions.value = response.data || []
  })
}

function cancel() {
  open.value = false
  reset()
}

function reset() {
  form.value = {
    workshopId: undefined,
    factoryId: undefined,
    workshopCode: undefined,
    workshopName: undefined,
    status: "0",
    remark: undefined
  }
  proxy.resetForm("workshopRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  createTimeRange.value = []
  updateTimeRange.value = []
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.workshopId)
  single.value = selection.length !== 1
}

function handleAdd() {
  reset()
  getFactoryOptions()
  open.value = true
  title.value = "新增车间"
}

function handleUpdate(row) {
  reset()
  getFactoryOptions()
  const workshopId = row.workshopId || ids.value
  getWorkshop(workshopId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改车间"
  })
}

function handleDetail(row) {
  getWorkshop(row.workshopId).then(response => {
    detail.value = response.data || {}
    detailOpen.value = true
  })
}

function submitForm() {
  proxy.$refs["workshopRef"].validate(valid => {
    if (valid) {
      if (form.value.workshopId !== undefined) {
        updateWorkshop(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addWorkshop(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleStatusChange(row, status) {
  const action = status === "0" ? "启用" : "停用"
  proxy.$modal.confirm(`确认要${action}“${row.workshopName}”车间吗？`).then(function() {
    return changeWorkshopStatus(row.workshopId, status)
  }).then(() => {
    row.status = status
    proxy.$modal.msgSuccess(action + "成功")
  }).catch(() => {})
}

function handleImport() {
  proxy.$refs["importWorkshopRef"].open()
}

function handleExport() {
  proxy.download("mes/workshop/export", buildQueryParams(), `workshop_${new Date().getTime()}.xlsx`)
}

getFactoryOptions()
getList()
</script>
