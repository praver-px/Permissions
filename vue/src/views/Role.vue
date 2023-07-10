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
        :data="tableData" border stripe
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
          prop="flag"
          label="唯一标识">
      </el-table-column>
      <el-table-column
          prop="description"
          label="描述">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" circle slot="reference" @click="selectMenu(scope.row)"><i
              class="el-icon-menu"></i></el-button>
          <el-button type="primary" circle class="ml-5" @click="handEdit(scope.row)"><i class="el-icon-edit"></i>
          </el-button>
          <el-popconfirm title="您确定要删除这行信息嘛？" class="ml-5"
                         @confirm="del(scope.row.id)">
            <el-button type="danger" circle slot="reference"><i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          :page-sizes="[5,10,15,20]"
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          @current-change="handleCurrentChange"
          layout="total,sizes,prev,pager,next,jumper"
          :hide-on-single-page="true"
          :total=total>
      </el-pagination>
    </div>


    <div>
      <el-dialog
          title="角色信息"
          :visible.sync="centerDialogVisible"
          width="40%"
          center>
        <el-form label-width="200px">
          <el-form-item label="用户名">
            <el-input v-model="form.name" autocomplete="off" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="标识符">
            <el-input v-model="form.flag" autocomplete="off" placeholder="请输入标识符"></el-input>
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

      <el-dialog
          title="菜单分配"
          width="20%"
          :visible.sync="menuDialogVisible"
          center>
        <el-tree
            ref="tree"
            :props="props"
            :data="menuData"
            show-checkbox
            node-key="id"
            default-expand-all
            :default-checked-keys="checks"
            @check-change="handleCheckChange">
          <span class="custom-tree-node" slot-scope="{node,data}">
            <span><i :class="data.icon"></i> {{ data.name }}</span>
          </span>
        </el-tree>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
            <el-button @click="menuDialogVisible = false">取 消</el-button>
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
      multipleSelection: [],
      visible: false,
      centerDialogVisible: false,
      menuDialogVisible: false,
      menuData: [],
      ids: [],
      props: {
        label: 'name',
      },
      roleId: 0,
      roleFlag: '',
      // expands: [],
      checks: [],
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
      }
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/role/page", {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      })

      this.request.get("/menu/ids").then(r => {
        if (r.code === '200') {
          this.ids = r.data
        }
      })
    },
    reset() {
      this.params.name = ""
      this.load()
    },
    handAdd() {
      this.form = {}
      this.centerDialogVisible = true;
    },
    handEdit(row) {
      this.form = row
      this.centerDialogVisible = true;
    },
    saveOrUpdate() {
      request.post("/role", this.form).then(res => {
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
      request.delete("/role/" + id).then(res => {
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
        request.delete("/role/deleteList/" + ids).then(res => {
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
    selectMenu(role) {
      this.roleId = role.id
      this.roleFlag = role.flag
      request.get("/menu").then(res => {
        if (res.code === '200') {
          this.menuData = res.data
          // 解析后台返回数据中的id数据
          // this.expands = this.menuData.map(v => v.id)
        }
      })

      request.get("/role/roleMenu/" + this.roleId).then(res => {
        this.menuDialogVisible = true
        if (res.code === '200') {
          this.checks = res.data
          // this.ids.forEach(id => {
          //       if (!this.checks.includes(id)) {
          //         this.$nextTick(() => {
          //           this.$refs.tree.setChecked(id, false)
          //         })
          //       }
          //     })
        }
        this.menuDialogVisible = true
      })
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
    saveRoleMenu() {
      request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200') {
          this.$notify.success("设置成功");
          this.menuDialogVisible = false

          if (this.roleFlag === 'ROLE_ADMIN') {
            this.$store.commit("logout")
          }

        } else {
          this.$notify.error(res.msg);
        }
      })
    },
  }
}
</script>

<style>
.headerBg {
  background-color: #f0f9eb !important;
}
</style>