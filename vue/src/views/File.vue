<template>
  <div>
    <div style="padding:10px 0">
      <el-input placeholder="请输入名称" v-model="params.name" suffix-icon="el-icon-document"
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
      <el-upload
          action="http://localhost:9090/api/file/upload"
          :on-success="handleFileUploadSuccess"
          :show-file-list="false"
          style="display: inline-block">
        <el-button type="primary" class="ml-5">上传文件<i class="el-icon-circle-plus-outline"></i></el-button>
      </el-upload>
    </div>
    <el-table
        :data="tableData"  stripe
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
          label="文件名">
      </el-table-column>
      <el-table-column
          prop="type"
          label="文件类型">
      </el-table-column>
      <el-table-column
          prop="size"
          label="文件大小（kb）">
      </el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button circle type="success" @click="download(scope.row.url)">
            <i class="el-icon-download"></i></el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.enable"
              @change="changeEnable(scope.row)"
              active-color="#13ce66"
              inactive-color="#ccc">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
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

  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "File",
  data() {
    return {
      total: 0,
      tableData: [],
      multipleSelection: [],
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',

      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/file/page", {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      })
    },
    reset() {
      this.params.name = ""
      this.load()
    },
    del(id) {
      request.delete("/file/" + id).then(res => {
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
        request.delete("/file/deleteList/" + ids).then(res => {
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
    changeEnable(row) {
      request.post("/file/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("更新成功!");
          this.load();
        }
      })
    },
    handleFileUploadSuccess(res) {
      console.log(res)
      this.load();
    },
    download(url) {
      window.open(url)
    }
  }
}
</script>

<style scoped>

</style>