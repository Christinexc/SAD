<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <b style="color: white" v-show="logoTextShow">员工培训管理系统</b>
    </div>
    <div v-for="item in menus" :key="item.id+' '">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
      </div>

    </div>



  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean,

  },
  data(){
    return{
      menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):[]
    }
  }
}
</script>

<style scoped>

</style>