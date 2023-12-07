<template>
  <div style=" font-size: 12px;display: flex" >
    <div style="flex: 1;font-size: 18px;margin: 15px">
      <span :class="collapseBtnClass" style="cursor: pointer;" @click="collapse"></span>
    </div>
    <el-dropdown >
      <span >Hello! {{user.nickname}}</span>
      <i class="el-icon-setting" style="margin: 25px"></i>

      <el-dropdown-menu slot="dropdown">

        <el-dropdown-item >
          <router-link to="/login">退出</router-link> </el-dropdown-item>

      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:Boolean
  },
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : []
    }
  }

}
</script>

<style scoped>

</style>