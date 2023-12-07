<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>

    </div>
    <div style="margin:10px 0">
      <el-button type="primary" @click="handleAdd"
                 v-if="this.user.role==='ROLE_ADMIN'||this.user.role==='ROLE_MANAGER'" >新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button >导出 <i class="el-icon-upload2"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="header">

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="课程名" >
      </el-table-column>
      <el-table-column prop="teacher" label="授课教师">
      </el-table-column>
      <el-table-column prop="fee" label="费用" width="140" >
      </el-table-column>
      <el-table-column prop="coursetype" label="课程类型">
      </el-table-column>
      <el-table-column prop="coursetime" label="授课时间">
      </el-table-column>
      <el-table-column v-if="this.user.role==='ROLE_CLERK'" prop="evaluationStatus" label="课程评价表">
      </el-table-column>
      <el-table-column   v-if="this.user.role==='ROLE_USER'" label="操作">
        <template slot-scope="scope">
        <el-button type="success" @click="selectCourse(scope.row.id)">选课 <i class="el-icon-edit"></i></el-button>
        </template>
      </el-table-column>

      <el-table-column   v-if="this.user.role==='ROLE_CLERK'" label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="success" @click="viewCourseMessage(scope.row.id)">查看学生信息 <i class="el-icon-edit"></i></el-button>
          <el-button type="success" @click="handleEvaluate(scope.row.id)">发放课程评价表 <i class="el-icon-edit"></i></el-button>

        </template>


      </el-table-column>

      <el-table-column v-if="this.user.role==='ROLE_ADMIN'||this.user.role==='ROLE_MANAGER'" label="操作"  >
        <template slot-scope="scope">
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
    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="课程名:" >
          <el-input v-model="uform.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="授课教师:" >
          <el-select clearable v-model="uform.teacherId" placeholder="请选择">
            <el-option v-for="item in teachers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="费用:" >
          <el-input v-model="uform.fee" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程类型:" >
          <el-input v-model="uform.coursetype" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="授课时间:" >
          <el-input v-model="uform.coursetime" autocomplete="off"></el-input>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import header from "@/components/Header.vue";
export default {
  name: "Course",
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
      name:"",
      dialogFormVisible:false,
      uform:{},
      teachers:[],
      stu_course:[],
      courses:[],
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    load()
    {
      if(this.user.role!=='ROLE_USER')
      {
        this.request.get("/course/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&name="+this.name)
            .then(res=>{
              console.log(res);
              this.tableData= res.data.records;
              this.total=res.data.total;
              for (let i=0;i<this.total;i++)
              {
                if(this.tableData[i].evaluationStatus==="1")
                  this.tableData[i].evaluationStatus="已发放"
                else
                  this.tableData[i].evaluationStatus="未发放"
              }
            })
      }
      else {
        this.request.get("/course/stu/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&studentId="+this.user.id)
            .then(res=>{
              console.log(res);
              this.tableData= res.data.records;
              this.total=res.data.total;


            })
      }

      this.request.get("/user/role/ROLE_TEACHER").then(res=>{
           this.teachers=res.data
          })

    },
    save()
    {
      this.uform.evaluationStatus=0
      this.request.post("/course",this.uform).then(res=>
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
      this.request.delete("/course/"+id).then(res=>
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
    selectCourse(courseId)
    {

      this.request.post('/course/studentCourse/'+this.user.id+"/"+courseId).then(res=>{
        if(res.code==='200'){
          this.$message.info("已选课，待审批")
        }else{
          this.$message.success("选课失败")
        }
      })
    },
    handleEvaluate(courseId)
    {
      this.uform={}
      this.uform.id=courseId
      this.uform.evaluationStatus=1
      this.request.post("/course",this.uform).then(res=>
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
    viewCourseMessage(courseId)
    {
      this.$router.push({
        name: 'StudentCourse',
        params: {
          courseId: courseId
        }
      });
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