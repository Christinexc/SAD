<template>
  <!-- Login -->
  <div id="login">
    <div id="login-form">
      <h1>培训管理系统</h1>

      <el-input type="text" prefix-icon="el-icon-user-solid" placeholder="用户名" id="name" autocapitalize="off" v-model="user.username" aria-autocomplete="off"></el-input>

        <el-input type="password"  prefix-icon="el-icon-lock" placeholder="密码" id="password" autocapitalize="off" show-password v-model="user.password"></el-input>

      <div>
        <el-button type="primary" auotocomplete="off" @click="login">登录</el-button>
        <el-button type="info" @click="resetInfo">重置</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data: function () {
    return {
     user:{}
    }
  },
  methods: {
    // 清空当前填写信息
    resetInfo: function () {
      this.user.username = "";
      this.user.password = "";
    },
    login()
    {
      this.request.post("/user/login",this.user).then(res=>{
        if(res.code==='200'){
          localStorage.setItem("user",JSON.stringify(res.data))
          localStorage.setItem("menus",JSON.stringify(res.data.menus))
          this.$message.success("登录成功")
          this.$router.push("/home")
        }else{
          this.$message.error("用户名或密码错误")

        }
      })
    }


  },


}

</script>

<style lang="less" scoped>
#login {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  position: relative;
  // 背景图片样式
  background-image: url("../assets/bg3.jpg");
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
}

#login-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50vw;
  min-width: 300px;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  background-color: rgba(255, 255, 255, 0.6);
  border-radius: 15px;
  // 表单 box-shadow 样式
  box-shadow: 0 15px 25px rgba(0, 0, 0, .5);

  h1 {
    width: 60%;
    margin: 50px auto 0;
    color: black;
    text-align: center;
  }

  input {
    width: 60%;
    margin: 0 auto;
    // 注意 border outline 默认值
    outline: none;
    border: none;
    padding: 10px;
    border-bottom: 1px solid black;
    background: transparent;
    color: black;
  }

  label {
    width: 60%;
    margin: 0 auto;
    position: relative;
    top: 30px;
    left: -15px;
  }

  div {
    width: 60%;
    margin: 10px auto;
    display: flex;
    justify-content: center;
    align-content: center;
  }

  button {
    // rgba
    background-color: rgba(9, 108, 144, 0.5);
    margin: 10px 25px 40px 25px;
  }

  p {
    width: 60%;
    margin: 8px auto;
    position: relative;
    left: -15px;
    color: #ff0000;
    font-size: 8px;
  }
}
// 浏览器兼容 , 针对谷歌浏览器 默认设置的 奇怪样式
input {
  -webkit-text-fill-color: black !important;
  transition: background-color 5000s ease-in-out ,width 1s ease-out!important;
}

</style>
