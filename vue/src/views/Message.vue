<template>

  <div>
    <el-empty  v-if="this.tableData.length===0" description="无新消息"></el-empty>
<div v-if="this.tableData.length!=0">
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="messgename"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="header">

      <el-table-column prop="type" label="消息类型" >
      </el-table-column>
      <el-table-column prop="sentFrom" label="来自" >
      </el-table-column>
      <el-table-column prop="time" label="时间" >
      </el-table-column>




      <el-table-column label="操作"  >
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdite(scope.row)" >查看详细 <i class="el-icon-edit"></i></el-button>
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
    <el-dialog
        title="详细内容"
        :visible.sync="dialogFormVisible"
        width="30%"
        center>
      <span>{{tableData[0].content}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisible = false">取 消</el-button>

  </span>
    </el-dialog>
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
  </div>
</template>

<script>
import header from "@/components/Header.vue";

export default {
  name: "Message",
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
      messgename:"",
      dialogFormVisible:false,
      uform:{},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}

    }
  },
  created() {
    this.load()
  },
  methods:{
    load()
    {
      this.request.get("/message/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: String(this.user.id)
        }
      })
          .then(res=>{
            console.log(res);
            this.tableData= res.data.records;
            this.total=res.data.total;
          })


    },

    handleAdd()
    {

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
      this.request.delete("/message/"+id).then(res=>
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