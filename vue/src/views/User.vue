<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="username"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>

    </div>
    <div style="margin:10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button >导出 <i class="el-icon-upload2"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="header">

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="username" label="账户" >
      </el-table-column>
      <el-table-column prop="role" label="角色">
      </el-table-column>
      <el-table-column prop="nickname" label="名称"  >
      </el-table-column>
      <el-table-column prop="email" label="email">
      </el-table-column>
      <el-table-column prop="phone" label="擅长领域/公司名称">
      </el-table-column>
      <el-table-column prop="address" label="职称/技术水平">
      </el-table-column>
      <el-table-column label="操作"  width="300"  v-if="this.user.role==='ROLE_EXECUTOR'">
        <template  slot-scope="scope">
          <el-button type="primary"
                     @click="lookCourse(scope.row.id)" v-if="scope.row.role=== 'ROLE_TEACHER'"
          >查看任课情况 <i class="el-icon-school"></i></el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="300"  v-if="this.user.role==='ROLE_ADMIN'||this.user.role==='ROLE_MANAGER'">
        <template  slot-scope="scope">
          <el-button type="primary"
                     @click="lookCourse(scope.row.id)" v-if="scope.row.role=== 'ROLE_TEACHER'"
          >查看任课情况 <i class="el-icon-school"></i></el-button>
         <div>
           <el-button  type="success" @click="handleEdite(scope.row)" >编辑 <i class="el-icon-edit"></i></el-button>
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
         </div>

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
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="用户名:" >
          <el-input v-model="uform.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色:" >
          <el-select clearable v-model="uform.role" placeholder="请选择角色" style="width: 80%">
            <el-option v-for="item in roles" :key="item.name" :lable="item.name" :value="item.flag"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名:" >
          <el-input v-model="uform.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱:" >
          <el-input v-model="uform.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="擅长领域:" >
          <el-input v-model="uform.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职称:" >
          <el-input v-model="uform.address" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="课程信息" :visible.sync="vis" width="30%">
      <el-table :data="courses" border stripe>
        <el-table-column prop="name" label="课程名称"></el-table-column>
        <el-table-column prop="coursetime" label="课程时间"></el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>

import header from "@/components/Header.vue";

export default {
  name: "User",
  computed: {
    header() {
      return header
    }
  },
  data(){
    return {
      tableData:[],
      total:0,
      pageNum:1,
      pageSize:10,
      username:"",
      dialogFormVisible:false,
      uform:{},
      roles:[],
      courses:[],
      vis:false,
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    lookCourse(id)
    {

      this.request.get("/user/page/stu",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          id:String(id),
        }
      }).then(res=>{
        console.log(res.data.records)
        if(res.data.records.length===0)
          this.$message.error(("无授课信息"))
        else this.courses=res.data.records[0].courses
        this.vis=true
      })
    },
    load()
    {


      this.request.get("/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
        }
      }).then(res=>{
           //console.log(res);
            this.tableData= res.data.records;
            this.total=res.data.total;
          })
      this.request.get("/role").then(res=>{
        this.roles=res.data
      })

    },

    save()
    {
      this.request.post("/user",this.uform).then(res=>
      {
        if(res.code==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error(("保存失败"))
        }
      })

    },
    handleAdd()
    {
      this.dialogFormVisible=true
      this.uform={}
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleEdite(row)
    {
      this.uform=row
      this.dialogFormVisible=true
    },
    handleDelete(id)
    {
      this.request.delete("/user/"+id).then(res=>
      {
        if(res.code==='200'){
          this.$message.success("删除成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error(("删除失败"))
        }
      })
    },
    handleCurrentChange(pageNum)
    {
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    }
  }
}
</script>

<style scoped>

</style>