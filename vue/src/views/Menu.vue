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
      <el-table-column prop="name" label="名称">
      </el-table-column>
      <el-table-column prop="path" label="路径"  >
      </el-table-column>
      <el-table-column  label="图标"  class-name="fontSize18" align="center" lable-class-name="fontSize12">
        <template slot-scope="scope">
          <i :class="scope.row.icon"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"  >
      </el-table-column>
      <el-table-column label="操作"  >
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
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width=120px>
        <el-form-item label="名称:" >
          <el-input v-model="uform.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径:" >
        <el-input v-model="uform.path" autocomplete="off"></el-input>
      </el-form-item>
        <el-form-item label="图标:" >
            <el-select clearable v-model="uform.icon" placeholder="请选择" style="width: 80%">
              <el-option v-for="item in options" :key="item.name" :lable="item.name" :value="item.value">
                <i :class="item.value"/>{{ item.name }}
              </el-option>
            </el-select>
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
  </div>
</template>

<script>
import options from "axios";
import header from "@/components/Header.vue";

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
      pageSize:10,
      name:"",
      dialogFormVisible:false,
      uform:{},
      options:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    options,
    load()
    {
      this.request.get("/menu/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&name="+this.name)
          .then(res=>{
            console.log(res);
            this.tableData= res.data.records;
            this.total=res.data.total;
          })


    },
    save()
    {
      this.request.post("/menu",this.uform).then(res=>
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
      //请求图标数据
      this.request.get("/menu/icons")
          .then(res=>{
            console.log(res);
            this.options= res.data;
          })
    },
    handleDelete(id)
    {
      this.request.delete("/menu/"+id).then(res=>
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
.fontSize18{
  font-size: 18px;
}
.fontSize12{
  font-size: 12px;
}

</style>