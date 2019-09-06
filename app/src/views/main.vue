<template>

    <el-container style=" border: 1px solid #eee">
        <el-header>
            <router-link to="/dep" style="text-decoration: none;">
                <img src="img/logo.png" style="width: 48px;vertical-align: middle;">
                <span style="vertical-align: middle;  margin-left: 5px; font-size: 18px; font-weight: bold;margin-right: 50px">API Manager</span>
            </router-link>
            <!-- <router-link to="/" v-t="'homePage'"></router-link> &nbsp;-->
            <span @click="click(0)"> <router-link :class="select(0)"
                                                  :to="{path: `/main/api/${this.$route.params.depId}/list`}">
                API</router-link>
            </span>
            <span style="margin-left: 30px">&nbsp;</span>
            <span @click="click(1)">
                <router-link v-t="'document'" :class="select(1)"
                                                  :to="{path: `/main/doc/${this.$route.params.depId}`}"> </router-link>
            </span>

            <el-link style="font-size:16px;float:right" @click="logout()" v-t="'logout'"></el-link>

        </el-header>
        <el-main>

            <router-view></router-view>
        </el-main>
    </el-container>

    <!--<div>
        <router-view></router-view>
    </div>-->

</template>

<style scoped>
    .selected {

        border-bottom: 2px solid #409eff;
        padding: 10px;
        text-decoration-line: none;

    }

    .unselected {
        padding: 10px;
        text-decoration-line: none;
    }

    .el-header {
        background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
    }

</style>

<script>
    export default {
        data() {

            var p=this.$route.path
            var a=["/main/api","/main/doc"];
            var n=0;
            for(var i=0;i<a.length;i++) if(p.indexOf(a[i])>=0) n=i;

            return {
                index: n
            }
        },
        methods: {
            click(n) {

                this.index = n;


            },
            select(n) {
                if (this.index == n) return "selected";
                return "unselected";
            },
            logout(){

                this.ajax.get("/apimanager/user/loginOut", ()=>{
                    this.$router.push("/");
                });

            }
        }
    };
</script>