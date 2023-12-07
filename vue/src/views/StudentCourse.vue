<template>
  <div>
    <div v-if="this.user.role==='ROLE_CLERK' ">
    <el-page-header @back="goBack" content="详情页面">
    </el-page-header>
    </div>
    <el-table :data="courses" border stripe :header-cell-class-name="header">
      <el-table-column v-if="this.user.role==='ROLE_CLERK'||this.user.role==='ROLE_EXECUTOR'" prop="id" label="学生ID">
      </el-table-column>
      <el-table-column v-if="this.user.role==='ROLE_CLERK'||this.user.role==='ROLE_EXECUTOR'" prop="nickname" label="学生姓名">
      </el-table-column>
      <el-table-column prop="name" label="课程名称">
      </el-table-column>
      <el-table-column prop="fee" label="课程费用"  >
      </el-table-column>
      <el-table-column prop="coursetime" label="课程时间"  >
      </el-table-column>
      <el-table-column  v-if="this.user.role==='ROLE_USER'||this.user.role==='ROLE_EXECUTOR'" prop="status" label="选课状态"  >
      </el-table-column>
      <el-table-column  v-if="this.user.role==='ROLE_CLERK'|| this.user.role==='ROLE_USER'" prop="evaluateStatus" label="是否评价"  >
      </el-table-column>
      <el-table-column v-if="this.user.role==='ROLE_CLERK'|| this.user.role==='ROLE_USER'"label="操作" >
        <template slot-scope="scope">
          <el-button  type="success" @click="handleEdite(scope.row.id,scope.row.courseId)">现场信息 <i class="el-icon-edit"></i></el-button>

        </template>
      </el-table-column  >

      <el-table-column v-if=" this.user.role==='ROLE_USER'"label="操作" >
        <template slot-scope="scope">
          <el-button  type="success" @click="handleEvaluate(scope.row.id,scope.row.courseId)">评价 <i class="el-icon-edit"></i></el-button>

        </template>

      </el-table-column  >
      <el-table-column v-if=" this.user.role==='ROLE_EXECUTOR'"label="操作" >
        <template slot-scope="scope">
          <el-button  type="success" @click="handleExecute(scope.row.id,scope.row.courseId)">审核 <i class="el-icon-edit"></i></el-button>

        </template>

      </el-table-column  >

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

    <el-dialog title="课程信息" :visible.sync="vis" width="30%">
      <el-form label-width=120px>

        <el-form-item label="缴费状态:"  >
          <el-input v-model="uform.feeStatus" autocomplete="off" disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="发书状态:" >
          <el-input v-model="uform.bookStatus" autocomplete="off" disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="签到状态:" >
          <el-input v-model="uform.checkIn" autocomplete="off" disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="vis = false">取 消</el-button>
        <el-button type="primary" @click="vis = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="学生ID:" >
          <el-input v-model="submit.studentId"  disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程ID:" >
          <el-input v-model="submit.courseId" autocomplete="off" disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="缴费状态:"  >
          <el-tooltip :content="'缴费: '+submit.feeStatus" placement="">
            <el-switch
                v-model="submit.feeStatus"
                active-value="true"
                inactive-value="false">
            </el-switch>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="发书状态:" >
          <el-tooltip :content="'发书: '+submit.bookStatus" placement="">
            <el-switch
                v-model="submit.bookStatus"
                active-value="true"
                inactive-value="false">
            </el-switch>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="签到状态:" >
          <el-tooltip :content="'签到: '+submit.checkIn" placement="">
            <el-switch
                v-model="submit.checkIn"
                active-value="true"
                inactive-value="false">
            </el-switch>
          </el-tooltip>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="评价" :visible.sync="evaluevis" width="30%">
      <el-form label-width=120px>
        <el-form-item label="评价" >
          <el-input  v-model="submit.evaluation"  autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="evaluevis = false">取 消</el-button>
        <el-button type="primary" @click="saveEvaluation">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="审批" :visible.sync="executeFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="审批状态:" >
          <template>
            <el-select v-model="submit.success" placeholder="请选择">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="executeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateCourse">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import options from "axios";
import header from "@/components/Header.vue";
import course from "@/views/Course.vue";

export default {
  name: "Menu",

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
      shows:[],
      pageSize:10,
      dialogFormVisible:false,
      evaluevis:false,
      executeFormVisible:false,
      uform:{},
      submit:{},
      vis:false,
      PJ1:" ",
      PJ2:"xxxx",
      courses:[],
      options: [{
        value: '0',
        label: '未审批'
      }, {
        value: '1',
        label: '同意'
      }, {
        value: '2',
        label: '拒绝'
      }],
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    options,
    load()
    {
      if(this.user.role==="ROLE_CLERK")
        this.clerkMenu();
      else if (this.user.role==='ROLE_EXECUTOR')
        this.executorMenu();
      else
        this.stuMenu();


    },
    goBack() {
      this.$router.push({
        name: 'Course',

      });
    },
    clerkMenu()
    {
      this.courses=[]
      const courseId = this.$route.params.courseId;
      console.log(courseId);
      this.request.get("/studentcourse/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          id: String(-1),
          course_id: String(courseId),
        }
      }) .then(res=>{
        console.log(res);
        this.tableData= res.data.records;
        console.log(this.tableData);
        let i=0;
        for(i;i<this.tableData.length;i++) {
          // 还未选课成功
          if (this.tableData[i].success!=="1")
            continue
          this.courses.push(this.tableData[i].courses[0]) ;
          this.courses[i].id=this.tableData[i].studentId;
          this.courses[i].nickname=this.tableData[i].nickname;
          this.courses[i].evaluateStatus=this.tableData[i].evaluateStatus
          this.courses[i].teacherId=i;
          this.courses[i].courseId=this.tableData[i].courseId
          if (this.courses[i].evaluateStatus==="1")
            this.courses[i].evaluateStatus="已评价"
          else if (this.courses[i].evaluateStatus==="0")
            this.courses[i].evaluateStatus="未评价"

        }

        this.total=res.data.total;
      })
    },
    executorMenu() {
      this.courses=[]
      this.request.get("/studentcourse/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          id: String(-1),
          course_id: String(-1),
        }
      }) .then(res=>{
        console.log(res);
        this.tableData= res.data.records;
        console.log(this.tableData);
        let i=0;
        for(i;i<this.tableData.length;i++) {
          this.courses.push(this.tableData[i].courses[0]) ;
          this.courses[i].id=this.tableData[i].studentId;
          this.courses[i].nickname=this.tableData[i].nickname;
          this.courses[i].evaluateStatus=this.tableData[i].evaluateStatus;
          this.courses[i].teacherId=i;
          this.courses[i].courseId=this.tableData[i].courseId
          if (this.tableData[i].success==="1")
            this.courses[i].status="已选课"
          else if (this.tableData[i].success==="0")
            this.courses[i].status="正在审核"
          else
            this.courses[i].status="已被拒绝"
          console.log(course[i]);

        }

        this.total=res.data.total;
      })
    },
    stuMenu()
    {
      this.courses=[]
      this.request.get("/studentcourse/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          id: String(this.user.id),
          course_id: String(-1),
        }
      }) .then(res=>{
        console.log(res);
        this.tableData= res.data.records;
        console.log(this.tableData);
        let i=0;
        for(i;i<this.tableData.length;i++) {

          this.courses.push(this.tableData[i].courses[0]) ;
          this.courses[i].id=this.tableData[i].studentId;
          this.courses[i].teacherId=i;
          this.courses[i].courseId=this.tableData[i].courseId
          if (this.tableData[i].success==="1")
            this.courses[i].status="已选课"
          else if (this.tableData[i].success==="0")
            this.courses[i].status="正在审核"
          else
            this.courses[i].status="已被拒绝"
          this.courses[i].evaluateStatus=this.tableData[i].evaluateStatus
          if (this.courses[i].evaluateStatus==="1")
            this.courses[i].evaluateStatus="已评价"
          else if (this.courses[i].evaluateStatus==="0")
            this.courses[i].evaluateStatus="未评价"

        }
        this.total=res.data.total;
      })

    },
    handleExecute(stuId,courseId)
    {
      this.executeFormVisible=true
      this.submit=[]
      this.submit.id=stuId
      this.submit.courseId=courseId

    },
    handleEdite(stuId,courseId)
    {
      this.load();
      let i=0
      if(this.user.role==='ROLE_USER')
      {
        this.vis=true
        this.uform=[]

        for(i;i<this.tableData.length;i++)
        {
          if(this.tableData[i].studentId===stuId && this.tableData[i].courseId===courseId)
          {
            this.uform=this.tableData[i];
            if(this.uform.feeStatus==="true")
              this.uform.feeStatus="已缴费"
            else
              this.uform.feeStatus="未缴费"
            if(this.uform.checkIn==="true")
              this.uform.checkIn="已签到"
            else
              this.uform.checkIn="未签到"
            if(this.uform.bookStatus==="true")
              this.uform.bookStatus="已发书"
            else
              this.uform.bookStatus="未发书"
            console.log(this.tableData[i])
          }
        }

      }
      else
      {
        this.dialogFormVisible=true
        this.submit=[]
        for(i;i<this.tableData.length;i++)
        {
          if(this.tableData[i].studentId===stuId&&this.tableData[i].courseId===courseId)
          {
            this.submit=this.tableData[i];

          }
        }
      }



    },
    updateCourse()
    {

      this.request.post('/course/update/studentCourse/'+this.submit.id+"/"+this.submit.courseId+"/"+this.submit.success+"/success").then(res=>{
        if(res.code==='200'){
          this.$message.info("提交成功")
          this.load()
          this.executeFormVisible=false
        }else{
          this.$message.success("提交失败")
        }
      })


    },
    saveEvaluation()
    {
      this.request.post('/course/update/studentCourse/'+this.submit.id+"/"+this.submit.courseId+"/"+this.submit.evaluation+"/evaluation").then(res=>{
        if(res.code==='200'){
          this.$message.info("提交成功")
          this.load()
          this.executeFormVisible=false
        }else{
          this.$message.success("提交失败")
        }
      })
    },
    save()
    {
      this.request.post("/studentcourse",this.submit).then(res=>
      {
        if(res.code==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.executeFormVisible=false
          this.load()
        }else{
          this.$message.error(("保存失败"))
        }
      })

    },
    handleEvaluate(stuId,courseId)
    {
      this.evaluevis=true;
      this.submit=[]
      this.submit.id=stuId
      this.submit.courseId=courseId
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },



    handleCurrentChange(pageNum)
    {
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },

  }
}
</script>

<style scoped>
.fontSize18{
  font-size: 18px;
}
.fontSize12{
  font-size: 12px;
}

</style>