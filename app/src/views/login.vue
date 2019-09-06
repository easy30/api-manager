<template>
    <div>
        <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
            <h3 class="login-title">API Manager</h3>
            <el-form-item :label="$t('username')" prop="username">
                <el-input size="big" type="text"  v-model="form.username"/>
            </el-form-item>
            <el-form-item :label="$t('password')" prop="password">
                <el-input  size="big" type="password"  v-model="form.password"/>
            </el-form-item>
            <el-form-item>
                <el-button size="big" type="primary" @click="onSubmit('loginForm')" v-t="'login'"></el-button>
            </el-form-item>
            <el-form-item>
                <span style="color: red">{{message}}</span>

            </el-form-item>
        </el-form>

        
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                form: {
                    username: this.$cookies.get("username"),
                    password: this.$cookies.get("password")
                },
                message:"",

                // 表单验证，需要在 el-form-item 元素中增加 prop 属性
                rules: {
                    username: [
                        {required: true, message: this.$t('usernameRequire'), trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message:this.$t('passwordRequire'), trigger: 'blur'}
                    ]
                },

                // 对话框显示和隐藏
                dialogVisible: false
            }
        },
        methods: {
            onSubmit(formName) {
                // 为表单绑定验证功能
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // 使用 vue-router 路由到指定页面，该方式称之为编程式导航

                        this.ajax.postForm("/apimanager/user/login",
                            { account: this.form.username,password:this.form.password},
                            ()=>{
                                console.log("hello");
                                this.$cookies.set("username",this.form.username,"30d");
                                this.$cookies.set("password",this.form.password,"30d");
                                this.$router.push("/dep");
                             },
                            err=>{  this.message=err.message; }
                            );




                    } else {
                        this.dialogVisible = true;
                        return false;
                    }
                });
            }
        }
    }
</script>

<style  scoped>
    .login-box {
        border: 1px solid #DCDFE6;
        width: 350px;
        margin: 180px auto;
        padding: 35px 35px 15px 35px;
        border-radius: 5px;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        box-shadow: 0 0 25px #909399;
    }

    .login-title {
        text-align: center;
        margin: 0 auto 40px auto;
        color: #303133;
    }
</style>