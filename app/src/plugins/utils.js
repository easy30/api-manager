
      function treeNodeEach(items,childName,func) {
        _treeNodeEach(null,items,childName,0,func);
    }


     function  _treeNodeEach(pnode,items,childName,deep,func) {
        if(items==null) return;
        for(var i in items){
            var obj=items[i];
            func(obj,pnode,deep);
            var childs=obj[childName]
            if(childs!=null){

                _treeNodeEach(obj,childs,childName,deep+1,func);

            }

        }

    }

    function notNull(obj,def) {
        if(typeof(obj)=="undefined" || obj==null) return def?def:"";
        return obj;
    }

   function  clone(obj){
          var o;
          // 如果  他是对象object的话  , 因为null,object,array  也是'object';
          if (typeof obj === 'object') {

              // 如果  他是空的话
              if (obj === null) {
                  o = null;
              }
              else {

                  // 如果  他是数组arr的话
                  if (obj instanceof Array) {
                      o = [];
                      for (var i = 0, len = obj.length; i < len; i++) {
                          o.push(clone(obj[ i ]));
                      }
                  }
                  // 如果  他是对象object的话
                  else {
                      o = {};
                      for (var j in obj) {
                          o[ j ] = clone(obj[ j ]);
                      }
                  }

              }
          }
          else {
              o = obj;
          }
          return o;
      }

      function isObjectField(type) {
          return type == 4;
      }
      function isArrayField(type) {
          return type >= 5;
      }

      function compare(prop){
          return function(o1, o2){
              var a, b;
               if(prop) {
                   a = o1[prop];
                   b = o2[prop];
               }else{
                   a=o1;
                   b=o2;
               }
                  if (a == b) {
                      return 0;
                  }else {
                 
                      return a < b ? -1 : 1;
                  }
                  
              
          }
      }



      export default {
        treeNodeEach,
        notNull,
          clone,
          isArrayField,
          compare,
          isObjectField
    }


