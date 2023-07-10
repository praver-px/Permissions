<template>
  <div>
    <div style="padding:10px 0">
      <el-input placeholder="请输入名称" v-model="params.name" suffix-icon="el-icon-user"
                style="width: 200px"></el-input>
      <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
      <el-button type="warning" class="ml-5" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button
          type="danger"
          class="ml-5"
          @click="rmvList"
          :disabled="this.multipleSelection.length === 0">
        批量删除<i class="el-icon-delete"></i>
      </el-button>
      <el-button type="primary" class="ml-5" @click="handAdd">新增<i class="el-icon-circle-plus-outline"></i>
      </el-button>
    </div>

    <el-table
        :data="tableData"
        stripe
        border
        row-key="id"
        default-expand-all
        :header-cell-class-name="'headerBg'"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="id"
          label="ID"
          width="55">
      </el-table-column>
      <el-table-column
          prop="name"
          label="名称">
      </el-table-column>
      <el-table-column
          prop="path"
          label="路径">
      </el-table-column>
      <el-table-column
          prop="pagePath"
          label="页面路径">
      </el-table-column>
      <el-table-column
          prop="icon"
          class="fontSize18"
          align="center"
          label-class-name="fontSize12"
          label="图标">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column
          prop="description"
          label="描述">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" v-if="!scope.row.pid && !scope.row.path" circle @click="add2Menu(scope.row)"><i
              class="el-icon-plus"></i></el-button>
          <el-button type="primary" circle class="ml-5" @click="handEdit(scope.row)"><i class="el-icon-edit"></i>
          </el-button>
          <el-popconfirm title="您确定要删除这行信息嘛？" class="ml-5"
                         @confirm="del(scope.row.id)">
            <el-button type="danger" circle slot="reference"><i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div>
      <el-dialog
          title="菜单信息"
          :visible.sync="centerDialogVisible"
          width="40%"
          center>
        <el-form label-width="200px">
          <el-form-item label="名称">
            <el-input v-model="form.name" autocomplete="off" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="路径">
            <el-input v-model="form.path" autocomplete="off" placeholder="请输入路径"></el-input>
          </el-form-item>
          <el-form-item label="页面路径">
            <el-input v-model="form.pagePath" autocomplete="off" placeholder="请输入路径"></el-input>
          </el-form-item>
          <el-form-item label="图标">
            <template slot-scope="scope">
              <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in icons" :key="item.name" :label="item.name" :value="item.value">
                  <i :class="item.value"/> {{item.name}}
                </el-option>
              </el-select>
            </template>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="form.description" autocomplete="off" placeholder="请输入描述"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
            <el-button @click="centerDialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      form: {},
      icons:[],
      multipleSelection: [],
      visible: false,
      centerDialogVisible: false,
      params: {
        name: '',
      }
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/menu", {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data;
          // this.total = res.data.total;
        }
      })

      request.get("/menu/icons").then(res => {
        if (res.code === '200') {
          this.icons = res.data;
        }
      })
    },
    reset() {
      this.params.name = ""
      this.load()
    },
    handAdd() {
      this.form = {}
      this.centerDialogVisible = true
    },
    handEdit(row) {
      this.form = row
      this.centerDialogVisible = true
    },
    saveOrUpdate() {
      request.post("/menu", this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success("操作成功");
          this.centerDialogVisible = false;
          this.load()
        } else {
          this.$notify.error(res.msg);
        }
      })
      this.centerDialogVisible = false;
      this.load()
    },
    del(id) {
      request.delete("/menu/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success("删除成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    rmvList() {
      let ids = this.multipleSelection.map(v => v.id);
      this.$confirm("确定要删除这" + ids.length + "行数据吧？", "确认信息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: 'warning'
      }).then(() => {
        // console.log(ids);
        request.delete("/menu/deleteList/" + ids).then(res => {
          if (res.code === '200') {
            this.$message.success("批量删除成功!");
            this.load();
          }
        })
      })
    },
    handleCurrentChange(pageNum) {
      //  点击分页按钮实现翻页
      this.params.pageNum = pageNum;
      this.load();
    },
    add2Menu(row) {
      this.centerDialogVisible = true
      this.form = {}
      this.form.pid = row.id


    }
  }
}
</script>

<style>
.headerBg {
  background-color: #f0f9eb !important;
}
.fontSize12{
  font-size: 12px;
}
.fontSize18{
  font-size: 18px;
}
</style>