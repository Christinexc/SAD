<template>

    <el-container style="min-height: 100vh">
      <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0 21 41/35%)">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>
      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;" >
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
        </el-header>
        <el-main >
<!--        当前页面的子路由会在这里展示-->
         <router-view />
        </el-main>
      </el-container>
    </el-container>


</template>

<script>
// @ is an alias to /src
import Header from "@/components/Header.vue";
import Aside from "@/components/Aside.vue";

export default {
  name: 'Manage',
  data() {
    return {

      sideWidth:200,
      isCollapse:false,
      collapseBtnClass:"el-icon-s-fold",
      logoTextShow:true
    }

  },

  components:{
    Aside,
    Header,

  },


  created() {
    this.getUser()
  },
  methods:{
    collapse(){//点击收缩按钮
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.sideWidth=64
            this.collapseBtnClass='el-icon-s-unfold'
            this.logoTextShow=false
      }else{
        this.sideWidth=200
            this.collapseBtnClass='el-icon-s-fold'
            this.logoTextShow=true
      }

    },

  },
  getUser() {
    let username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:""

  }
}
</script>
