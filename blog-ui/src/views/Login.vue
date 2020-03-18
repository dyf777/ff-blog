<template>
    <div class="bg-div">
        <img src="../assets/login.jpg" class="login-img">
        <div class="login-containner">
            <h2 class="login-title">系统登录</h2>
            <el-form label-position="top" ref="loginForm" :model="loginForm" :rules="rules">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
            <el-checkbox class="login-remember">记住密码</el-checkbox>
            <el-button type="primary" style="width: 100%" @click="doLogin">登录</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data(){
            return {
                loginForm:{
                    username:'admin',
                    password:'111'
                },
                rules:{
                    username:[{required:true,message:'请输入用户名',trigger:'blur'}],
                    password:[{required:true,message:'请输入密码',trigger:'blur'}]
                }
            }
        },
        methods:{
            doLogin(){
                this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                    if (resp){
                        this.$store.commit('saveUser',resp);
                        this.$router.replace('/home');
                    }else{
                        this.$message.error("请输入用户名和密码");
                    }
                })
            }
        }
    }
</script>

<style>
    .bg-div {
        background-color: #f7f9fb;
        height: 100%;
        position: fixed;
        width: 100%;
        margin: 0px;
    }

    .login-img {
        width: 95px;
        height: 95px;
        border-radius: 50%;
        margin-top: 30px;
    }

    .login-containner {
        background-color: #fff;
        background-clip: padding-box;
        margin: 20px auto;
        width: 320px;
        padding: 25px 30px 40px 30px;
        border-radius: 10px;
        border: 1px solid transparent;
        box-shadow: 0 0 40px rgba(0,0,0,.05);
    }

    .login-title {
        text-align: left;
        margin: 10px auto;
    }

    .el-form--label-top {
        text-align: left;
        margin-bottom: 10px;
        padding-bottom: 0px;
    }

    .el-form--label-top .el-form-item__label {
        padding-bottom: 0px;
    }
    .el-form-item{
        margin-bottom: 10px;
    }

    .login-remember {
        text-align: left;
        margin: 10px auto 20px auto;
    }

</style>