<template>
  <div>
    <div style="padding:10px 0">
      <el-input placeholder="请输入名称" v-model="params.username" suffix-icon="el-icon-user"
                style="width: 200px"></el-input>
      <el-input placeholder="请输入邮箱" v-model="params.email" suffix-icon="el-icon-message" style="width: 200px"
                class="ml-5"></el-input>
      <el-input placeholder="请输入地址" v-model="params.address" suffix-icon="el-icon-location-outline"
                style="width: 200px"
                class="ml-5"></el-input>
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

      <el-upload
          action="http://localhost:9090/user/import"
          accept="xlsx"
          :on-success="handleImpSuccess"
          :show-file-list="false"
          style="display: inline-block">
        <el-button type="primary" class="ml-5">导入<i class="el-icon-sort-down"></i></el-button>
      </el-upload>
      <el-button type="primary" class="ml-5" @click="exp">导出<i class="el-icon-sort-up"></i></el-button>
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
          prop="avatar"
          label="头像">
        <template v-slot="scope">
          <el-avatar
              size="large"
              :src="scope.row.avatar">
          </el-avatar>
        </template>
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名">
      </el-table-column>
      <el-table-column prop="role" label="角色">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
          <el-tag type="success" v-if="scope.row.role === 'ROLE_TEACHER'">教师</el-tag>
          <el-tag type="info" v-if="scope.row.role === 'ROLE_STUDENT'">学生</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="nickname"
          label="昵称">
      </el-table-column>
      <el-table-column
          prop="email"
          label="邮箱">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="手机号">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="info" circle class="ml-5" v-if="scope.row.role  === 'ROLE_TEACHER'"
                     @click="lookCourse(scope.row.courses)"><i
              class="el-icon-s-order"></i>
          </el-button>
          <el-button type="info" circle class="ml-5" v-if="scope.row.role  === 'ROLE_STUDENT'"
                     @click="lookStuCourse(scope.row.stuCourses)"><i
              class="el-icon-menu"></i>
          </el-button>
          <el-popconfirm title="您确定要将密码重置为123456吗？" class="ml-5"
                         @confirm="resetPass(scope.row)">
            <el-button type="warning" circle slot="reference"><i class="el-icon-refresh-right"></i>
            </el-button>
          </el-popconfirm>
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
          title="用户信息"
          :visible.sync="centerDialogVisible"
          width="40%"
          center>
        <el-form label-width="200px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname" autocomplete="off" placeholder="请输入昵称"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
              <el-option v-for="item in roles" :key="item.name" :lable="item.name" :value="item.flag">
                {{ item.name }}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-input v-model="form.address" autocomplete="off" placeholder="请输入地址"></el-input>
          </el-form-item>
          <el-form-item label="头像" prop="avatar">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:9090/api/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess">
              <img v-if="form.avatar" :src="form.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
            <el-button @click="centerDialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>


    <div>
      <el-dialog
          title="授课信息"
          :visible.sync="courseDialogVisible"
          width="40%"
          center>
        <el-table :data="course" border stripe>
          <el-table-column prop="name" label="课程名"></el-table-column>
          <el-table-column prop="score" label="学分"></el-table-column>
          <el-table-column prop="times" label="课时"></el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="courseDialogVisible = false">确 定</el-button>
            <el-button @click="courseDialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>

    <div>
      <el-dialog
          title="选课信息"
          :visible.sync="studentDialogVisible"
          width="40%"
          center>
        <el-table :data="StuCourse" border stripe>
          <el-table-column prop="name" label="课程名"></el-table-column>
          <el-table-column prop="score" label="学分"></el-table-column>
          <el-table-column prop="times" label="课时"></el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="studentDialogVisible = false">确 定</el-button>
            <el-button @click="studentDialogVisible = false">取 消</el-button>
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
      roles: [],
      course: [],
      StuCourse: [],
      multipleSelection: [],
      visible: false,
      centerDialogVisible: false,
      courseDialogVisible: false,
      studentDialogVisible: false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      params: {
        pageNum: 1,
        pageSize: 10,
        username: '',
        email: '',
        address: ''
      }
    }
  },
  created() {
    this.load();
    this.getUser()
  },
  methods: {
    load() {
      request.get("/user/page", {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      })

      request.get("/role").then(res => {
        this.roles = res.data
        console.log(this.roles)
      })
    },
    reset() {
      this.params.username = ""
      this.params.email = ""
      this.params.address = ""
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
      console.log(this.form)
      request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success("操作成功");
          this.$emit("refreshUser")
          this.centerDialogVisible = false;
          this.load()
        } else {
          this.$notify.error(res.msg);
        }
      })
      this.centerDialogVisible = false;
      this.load()
    },
    resetPass(row) {
      row.password = 123456;
      request.post("/user", row).then(res => {
        if (res.code === '200') {
          this.$notify.success("重置了" + row.nickname + '的密码');
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      })
    },
    del(id) {
      request.delete("/user/" + id).then(res => {
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
        request.delete("/user/deleteList/" + ids).then(res => {
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
    exp() {
      window.open("http://localhost:9090/api/user/export")
    },
    handleImpSuccess() {
      this.$notify.success("文件导入成功！");
      this.load()
    },
    handleAvatarSuccess(res) {
      this.form.avatar = res
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      this.request.get("/user/username/" + username).then(res => {
        this.user = res.data
      })
    },
    lookCourse(courses) {
      this.course = courses
      this.courseDialogVisible = true
    },
    lookStuCourse(stuCourses) {
      this.StuCourse = stuCourses
      this.studentDialogVisible = true
    }
  }
}
</script>

<style>
.headerBg {
  background-color: #f0f9eb !important;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>