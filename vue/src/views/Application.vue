<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="techname"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>

    </div>
    <div style="margin:10px 0">
      <el-button type="primary" @click="handleAdd" v-if="this.user.role ==='ROLE_ADMIN'||this.user.role==='ROLE_COMPANY'" >新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button v-if="this.user.role ==='ROLE_ADMIN'||this.user.role==='ROLE_MANAGER'">导出 <i class="el-icon-upload2"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="header">

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="techname" label="技术名" width="140">
      </el-table-column>
      <el-table-column prop="comecompany" label="来源公司">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="140" >
      </el-table-column>
      <el-table-column prop="introduce" label="简介">
      </el-table-column>
      <el-table-column v-if=" this.user.role==='ROLE_COMPANY'" label="操作"  >
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
      <el-table-column v-if=" this.user.role==='ROLE_MANAGER'|| this.user.role==='ROLE_ADMIN'"  label="操作"  >
        <template slot-scope="scope">
          <el-button  type="success" @click="handleApprove(scope.row.id)" >审批 <i class="el-icon-edit"></i></el-button>
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
    <el-dialog title="技术需求信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="技术需求:" >
          <el-input v-model="uform.techname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="来源公司:" >
          <el-input v-model="uform.comecompany" autocomplete="true" disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="简介:" >
          <el-input v-model="uform.introduce" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="审批" :visible.sync="visFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="审批意见:" >
          <el-input v-model="uform.opinion" ></el-input>
        </el-form-item>
        <el-form-item label="审批状态:" >
        <template>
          <el-select v-model="uform.status" placeholder="请选择">
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
        <el-button @click="visFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import header from "@/components/Header.vue";

export default {
  name: "Application",
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
      dialogFormVisible:false,
      visFormVisible:false,
      uform:{},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      options: [{
        value: '未审批',
        label: '未审批'
      }, {
        value: '同意',
        label: '同意'
      }, {
        value: '拒绝',
        label: '拒绝'
      }]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load()
    {
      let id=String(-1)
      if (this.user.role==='ROLE_COMPANY')
          id=String(this.user.id)
      this.request.get("/application/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: id
        }
    }).then(res=>{
            console.log(res);
            this.tableData= res.data.records;
            this.total=res.data.total;
          })
    },
    save()
    {
      this.request.post("/application",this.uform).then(res=>
      {
        if(res.code==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.visFormVisible=false
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
      this.uform.comecompany=this.user.nickname
      this.uform.companyId=this.user.id
      this.uform.status="未审批";
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
    handleApprove(row)
    {
      this.uform= this.tableData[row-1]
      this.visFormVisible=true
    },
    handleDelete(id)
    {
      this.request.delete("/application/"+id).then(res=>
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