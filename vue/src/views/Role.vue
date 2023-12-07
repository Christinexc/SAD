<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>

    </div>
    <div style="margin:10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
<!--      <el-button >导出 <i class="el-icon-upload2"></i></el-button>-->
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="header">

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column prop="flag" label="唯一标识">
      </el-table-column>
      <el-table-column prop="description" label="描述"  >
      </el-table-column>
      <el-table-column label="操作"  >
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row)">分配菜单<i class="el-icon-menu"></i> </el-button>
          <el-button type="success" @click="handleEdite(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除？"
              @confirm="handleDelete(scope.row.id)"
          >

            <el-button type="danger" slot="reference">删除 <i class="el-icon-delete"></i> </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>


    </el-table>
    <div style="padding: 10px 0">

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="名称:" >
          <el-input v-model="uform.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识:" >
          <el-input v-model="uform.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述:" >
          <el-input v-model="uform.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="菜单分配" :visible.sync="menudialogVisible" width="30%">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="[1]"
          :default-checked-keys="this.checked">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menudialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import header from "@/components/Header.vue";

export default {
  name: "Role",
  computed: {
    header() {
      return header
    }
  },
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      dialogFormVisible: false,
      menudialogVisible: false,
      uform: {},
      menuData:[],
      checked:[],
      roleId:0,
      props:{
        label: 'name'
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/role/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&name=" + this.name)
          .then(res => {
            //console.log(res);
            this.tableData = res.data.records;
            this.total = res.data.total;
          })


    },
    save() {
      this.request.post("/role", this.uform).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(("保存失败"))
        }
      })

    },
    saveRoleMenu(){
      this.request.post("/role/roleMenu/"+this.roleId, this.$refs.tree.getCheckedKeys()).then(res=>{
        if (res.code === '200') {
          this.$message.success("绑定成功")
          this.menudialogVisible = false
          this.load()
        } else {
          this.$message.error(("绑定失败"))
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.uform = {}
    },
    handleSizeChange(pageSize) {
     // console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleEdite(row) {

      this.uform = row
      this.dialogFormVisible = true
    },
    handleDelete(id) {
      this.request.delete("/role/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(("删除失败"))
        }
      })
    },
    handleCurrentChange(pageNum) {
      //console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    selectMenu(roleId) {
      this.menudialogVisible = true
      this.roleId=roleId.id
      //console.log(this.roleId)
      //请求菜单数据
      this.request.get("/menu?"+ "name=" + this.name)
          .then(res => {
           // console.log(res);
            this.menuData = res.data
          })
      this.request.get("/role/roleMenu/"+this.roleId).then(res=>{
        //console.log(res);
        this.checked=res.data
        console.log(this.checked);
      })

    }
  }
}
</script>

<style scoped>

</style>