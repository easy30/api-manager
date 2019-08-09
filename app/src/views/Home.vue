<template>

      <el-container>

          <el-main>
              <el-table

                      :data="tableData"
                      @row-click="rowClick"
                      border
                      style="width: 100%">

                  <el-table-column
                          prop="depName"
                          label="名称"
                          width="180">
                  </el-table-column>
                  <el-table-column
                          prop="depDesc"
                          label="说明">
                  </el-table-column>
              </el-table>

          </el-main>
      </el-container>





</template>

<script>
// @ is an alias to /src
//import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
    data () {
        return {
            tableData: [],
            info: {}
        }
    },
    mounted () {
    /*  this.axios.post("/apimanager/user/login?account=13911610242&password=123456")
          .then((response)=>{
              console.log("res="+response.data);


          }).catch((response)=>{
          console.log(response)
      })*/


        this.axios.get('/apimanager/department/list').then((response)=>{
            var json=response.data;
            this.tableData=json.data;

            //console.log("res="+response.data.data.pageIndex);
            this.info=response.data;

        }).catch((response)=>{
            console.log(response)
        })

    },
    methods: {
            rowClick(row, event, column) {
                console.log(row, event, column);
                //this.router.go()
               // this.$router.push({path: '/main/api', params: {deptId: row.id}});
                this.$router.push({path: `/main/api/${row.id}` });//, params: {deptId: row.id}});

         }
  },
  components: {

  }
}
</script>
