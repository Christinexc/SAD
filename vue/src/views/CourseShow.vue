<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="coursename"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="header">

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="coursename" label="课程名" width="140">
      </el-table-column>
      <el-table-column prop="teachername" label="授课教师">
      </el-table-column>
      <el-table-column prop="fee" label="费用" width="140" >
      </el-table-column>
      <el-table-column prop="coursetype" label="课程类型">
      </el-table-column>
      <el-table-column prop="coursetime" label="授课时间">
      </el-table-column>


      <el-table-column label="操作"  >
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdite(scope.row)">选课 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定选课？"
              @confirm="handleDelete(scope.row.id)"
          >
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

  </div>
</template>

<script>
export default {
  name: "Course",
  data(){
    return {
      tableData:[],
      total:0,
      pageNum:1,
      pageSize:10,
      coursename:"",
      dialogFormVisible:false,
      uform:{},

    }
  },
  created() {
    this.load()
  },
  methods:{
    load()
    {
      this.request.get("/course/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&coursename="+this.coursename)
          .then(res=>{
            console.log(res);
            this.tableData= res.data.records;
            this.total=res.data.total;
          })


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