package com.cehome.apimanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGen {
    // 方法 注释
    private final static String METHOD_NOTES = "\t/**\r\n  \t *\r\n \t * @功能描述:\r\n \t * @param:\r\n \t * @return:\r\n  \t * @Author "
            + System.getProperty("user.name") + " \r\n \t * @date: " + getCurrentFormatDateWeek() + " \r\n  \t * @since 1.0.0\r\n  \t */\r\n";
    // 类 注释
    private final static String CLASS_NOTES = "\r\n/**\r\n *\r\n * @Author " + System.getProperty("user.name") + "\r\n * @since 1.0.0\r\n */\r\n";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // 导出文件的路径
        String outputBasePath = "d:/code/";
        // 服务名
        String serviceId = "order";
        // model所在包名，实体名
        String modelPackageName = "com.cehome.cloud." + serviceId + ".model";
        // 实现服务的包名路径
        String apiPackageName = "com.cehome.cloud." + serviceId + "";
        // 数据库表名，多个逗号分隔
        String tableNames = "order_split_type,order_info,order_item,order_operation_log";
        String[] array = tableNames.split(",");
        for (String tableName : array) {
            // build model name
            String modelName = "";
            String[] tableNameWords = tableName.toLowerCase().split("_");

            for (String word : tableNameWords) {
                modelName += word.substring(0, 1).toUpperCase() + word.substring(1);
            }
            // get columns
            Map<String, Object> map = getDbInfo(tableName);
            List<JavaColumn> javaList = (List<JavaColumn>) map.get("javaList");
            List<DbColumn> dbList = (List<DbColumn>) map.get("dbList");
            // output po file
            List<String> poLines = writePo(modelPackageName, modelName, javaList);
            outputFile(outputBasePath + "api/po/" + modelName + ".java", poLines);
            // output dto file
            List<String> dtoLines = writeDto(modelPackageName, modelName);
            outputFile(outputBasePath + "api/dto/" + modelName + "Dto.java", dtoLines);
            // output reqDto file
            List<String> reqDtoLines = writeReqDto(modelPackageName, modelName);
            outputFile(outputBasePath + "api/req/" + modelName + "ReqDto.java", reqDtoLines);
            // output servcer service
            List<String> apiServiceLine = writeApiService(apiPackageName, modelPackageName, modelName);
            outputFile(outputBasePath + "api/" + modelName + "Service.java", apiServiceLine);
            // -----------------------api End--------------------------------------------
            // output serviceImpl
            List<String> serviceLine = writeServiceService(apiPackageName, modelPackageName, modelName);
            outputFile(outputBasePath + "server/service/" + modelName + "ServiceImpl.java", serviceLine);
            // output dao
            List<String> daoLines = writeDao(apiPackageName, modelPackageName, modelName);
            outputFile(outputBasePath + "server/dao/" + "I" + modelName + "Dao.java", daoLines);
            // output daoImpl
            List<String> daoImplLines = writeDaoImpl(apiPackageName, modelPackageName, modelName);
            outputFile(outputBasePath + "server/dao/impl/" + modelName + "DaoImpl.java", daoImplLines);
            // output mapper
            List<String> mapperLines = writeMapper(tableName, modelName, modelPackageName, dbList, javaList);
            outputFile(outputBasePath + "server/mapper/" + modelName + "Mapper.xml", mapperLines);
        }
        System.out.println("生成文件完成，共花费:" + (System.currentTimeMillis() - start));

    }

    /**
     * 得到当前系统日期和星期并转化为字符串：系统默认显示格式
     * @return 系统默认日期和星期显示格式 15位字符串
     */
    public static String getCurrentFormatDateWeek() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Map<String, Object> getDbInfo(String tableName) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<JavaColumn> javaList = new ArrayList<JavaColumn>();
        List<DbColumn> dbList = new ArrayList<DbColumn>();
        String ip = "192.168.0.13";
        String port = "3306";
        String user = "root";
        String password = "asdf1234!";
        String db = "ershouji";
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?user=" + user + "&password=" + password
                + "&useUnicode=true&characterEncoding=UTF8";
        Connection conn = null;
        String sql = "select column_name, data_type ,column_comment from information_schema.columns t where table_name='" + tableName
                + "' and table_schema = '" + db + "'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String columnName = rs.getString("column_name");
                String dataType = rs.getString("data_type");
                String remark = rs.getString("column_comment");
                javaList.add(new JavaColumn(columnName, dataType, remark));
                dbList.add(new DbColumn(columnName, dataType, remark));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("javaList", javaList);
        map.put("dbList", dbList);

        return map;
    }

    public static List<String> writePo(String packageName, String fileName, List<JavaColumn> columns) {
        List<String> fileLines = new ArrayList<String>();
        fileLines.add("package " + packageName + ".po;\n\n");
        fileLines.add(CLASS_NOTES);
        fileLines.add("import java.io.Serializable;\n\n");
        fileLines.add("public class " + fileName + "  implements Serializable {\n\n");

        if (columns != null && columns.size() > 0) {

            // create fields
            for (JavaColumn c : columns) {
                fileLines.add("\t/**" + c.getRemark() + "**/ \r\n");
                fileLines.add("\tprivate " + c.getType() + " " + c.getName() + ";\n\n");
            }

            // create getters and setters
            for (JavaColumn c : columns) {
                fileLines.add("\tpublic " + c.getType() + " get" + c.getName().substring(0, 1).toUpperCase() + c.getName().substring(1) + "() {\n");
                fileLines.add("\t\treturn this." + c.getName() + ";\n");
                fileLines.add("\t}\n\n");
                fileLines.add("\tpublic void set" + c.getName().substring(0, 1).toUpperCase() + c.getName().substring(1) + "(" + c.getType() + " "
                        + c.getName() + ") {\n");
                fileLines.add("\t\tthis." + c.getName() + " = " + c.getName() + ";\n");
                fileLines.add("\t}\n\n");
            }
        }

        fileLines.add("}");
        return fileLines;
    }

    public static List<String> writeDto(String packageName, String fileName) {
        List<String> fileLines = new ArrayList<String>();
        fileLines.add("package " + packageName + ".dto;\n\n");
        fileLines.add("import java.io.Serializable;\n\n");
        fileLines.add("import " + packageName + ".po." + fileName + ";\n\n");
        fileLines.add(CLASS_NOTES);
        fileLines.add("public class " + fileName + "Dto extends " + fileName + "  implements Serializable {\n\n");
        fileLines.add("}");
        return fileLines;
    }

    public static List<String> writeReqDto(String packageName, String fileName) {
        List<String> fileLines = new ArrayList<String>();
        fileLines.add("package " + packageName + ".dto;\n\n");
        fileLines.add("import java.io.Serializable;\n\n");
        fileLines.add("import " + packageName + ".po." + fileName + ";\n\n");
        fileLines.add(CLASS_NOTES);
        fileLines.add("public class " + fileName + "ReqDto extends " + fileName + "  implements Serializable {\n\n");
        fileLines.add("\t/**起始条数**/ \r\n");
        fileLines.add("\tprivate Integer pageOffset;\n\n");
        fileLines.add("\t/**每页条数**/ \r\n");
        fileLines.add("\tprivate Integer pageSize;\n\n");
        fileLines.add("\tpublic Integer getPageOffset() {\n");
        fileLines.add("\t\treturn pageOffset;\n");
        fileLines.add("\t}\n\n");
        fileLines.add("\tpublic void setPageOffset(Integer pageOffset) {\n");
        fileLines.add("\t\tthis.pageOffset = pageOffset;\n");
        fileLines.add("\t}\n\n");
        fileLines.add("\tpublic Integer getPageSize() {\n");
        fileLines.add("\t\treturn pageSize;\n");
        fileLines.add("\t}\n\n");
        fileLines.add("\tpublic void setPageSize(Integer pageSize) {\n");
        fileLines.add("\t\tthis.pageSize = pageSize;\n");
        fileLines.add("\t}\n\n");
        fileLines.add("}");
        return fileLines;
    }

    public static List<String> writeApiService(String apiPackageName, String modelPackageName, String fileName) {
        List<String> fileLines = new ArrayList<String>();
        String fileNameVar = fileName.substring(0, 1).toLowerCase() + fileName.substring(1);
        fileLines.add("package " + apiPackageName + ".api;\n\n");
        fileLines.add("import " + modelPackageName + ".dto." + fileName + "Dto;\n");
        fileLines.add("import " + modelPackageName + ".req." + fileName + "ReqDto;\n");
        fileLines.add("import com.cehome.cloud.common.object.BaseResult;\n");
        fileLines.add("import org.springframework.cloud.netflix.feign.FeignClient;\n");
        fileLines.add("import com.cehome.cloud.common.dao.base.Page;\n");
        fileLines.add("import org.springframework.web.bind.annotation.RequestBody;\n");
        fileLines.add("import org.springframework.web.bind.annotation.RequestMapping;\n");
        fileLines.add("import org.springframework.web.bind.annotation.RequestMethod;\n");
        fileLines.add("import org.springframework.web.bind.annotation.RequestParam;\n\n");
        fileLines.add(CLASS_NOTES);
        fileLines.add("@FeignClient()\n");
        fileLines.add("public interface " + fileName + "Service {\n\n");
        // add
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@RequestMapping(value=\"/add\" , method = RequestMethod.POST)\n");
        fileLines.add("\tBaseResult<Void> add(@RequestBody  " + fileName + "ReqDto " + fileNameVar + "ReqDto);\n\n");
        // update
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@RequestMapping(value=\"/update\" , method = RequestMethod.POST)\n");
        fileLines.add("\tBaseResult<Integer> update(@RequestBody  " + fileName + "ReqDto " + fileNameVar + "ReqDto);\n\n");
        // delete
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@RequestMapping(value=\"/delete\" , method = RequestMethod.GET)\n");
        fileLines.add("\tBaseResult<Void> delete(@RequestParam(\"id\") Integer id);\n\n");
        // get
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@RequestMapping(value=\"/get\" , method = RequestMethod.GET)\n");
        fileLines.add("\tBaseResult<" + fileName + "Dto> get(@RequestParam(\"id\") Integer id);\n\n");
        // findPage
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@RequestMapping(value=\"/findPage\" , method = RequestMethod.POST)\n");
        fileLines.add("\tBaseResult<Page<" + fileName + "Dto>> find(@RequestBody  " + fileName + "ReqDto " + fileNameVar + "ReqDto);\n\n");
        fileLines.add("}\n");
        return fileLines;
    }

    // -----------------------api End--------------------------------------------
    public static List<String> writeServiceService(String apiPackageName, String modelPackageName, String fileName) {
        List<String> fileLines = new ArrayList<String>();
        String className = fileName + "ServiceImpl";
        String fileNameVar = fileName.substring(0, 1).toLowerCase() + fileName.substring(1);
        fileLines.add("package " + apiPackageName + ".service;\n\n");
        fileLines.add("import com.cehome.cloud.common.dao.base.Page;\n");
        fileLines.add("import " + apiPackageName + ".api." + fileName + "Service;\n");
        fileLines.add("import " + apiPackageName + ".dao.I" + fileName + "Dao;\n");
        fileLines.add("import " + modelPackageName + ".dto." + fileName + "Dto;\n");
        fileLines.add("import " + modelPackageName + ".req." + fileName + "ReqDto;\n");
        fileLines.add("import com.cehome.utils.FastjsonUtils;\n");
        fileLines.add("import com.cehome.cloud.common.object.BaseResult;\n");
        fileLines.add("import org.slf4j.Logger;\n");
        fileLines.add("import org.slf4j.LoggerFactory;\n");
        fileLines.add("import org.springframework.beans.BeanUtils;\n");
        fileLines.add("import org.springframework.beans.factory.annotation.Autowired;\n");
        fileLines.add("import org.springframework.transaction.annotation.Transactional;\n");
        fileLines.add("import org.springframework.web.bind.annotation.RestController;\n");
        fileLines.add("import java.util.ArrayList;\n");
        fileLines.add(CLASS_NOTES);
        fileLines.add("@RestController \n");
        fileLines.add("public class " + className + " implements " + fileName + "Service {\n\n");
        fileLines.add("\tprivate final Logger logger = LoggerFactory.getLogger(" + fileName + "ServiceImpl.class);\n\n");
        fileLines.add("\t@Autowired\n");
        fileLines.add("\tprivate I" + fileName + "Dao " + fileNameVar + "Dao;\n\n");
        // add
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\t@Transactional(rollbackFor = Exception.class)\n");
        fileLines.add("\tpublic BaseResult<Void> add(@RequestBody " + fileName + "ReqDto  reqDto) {\n");
        fileLines.add("\t\tlogger.info(\"" + className + ".add  reqDto:{}\"," + "FastjsonUtils.toJSONString(reqDto));\n");
        fileLines.add("\t\t" + fileNameVar + "Dao.add(reqDto);\n");
        fileLines.add("\t\treturn BaseResult.success();\n");
        fileLines.add("\t}\n");
        // update
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\t@Transactional(rollbackFor = Exception.class)\n");
        fileLines.add("\tpublic BaseResult update(@RequestBody " + fileName + "ReqDto  reqDto) {\n");
        fileLines.add("\t\tlogger.info(\"" + className + ".update  reqDto:{}\"," + "FastjsonUtils.toJSONString(reqDto));\n");
        fileLines.add("\t\t" + fileNameVar + "Dao.update(reqDto);\n");
        fileLines.add("\t\treturn BaseResult.success();\n");
        fileLines.add("\t}\n\n");
        // delete
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\t@Transactional(rollbackFor = Exception.class)\n");
        fileLines.add("\tpublic BaseResult<Void> delete(Integer id) {\n");
        fileLines.add("\t\t" + fileNameVar + "Dao.delete(id);\n");
        fileLines.add("\t\treturn BaseResult.success();\n");
        fileLines.add("\t}\n\n");
        // get
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic BaseResult<" + fileName + "Dto> get(Integer id) {\n");
        fileLines.add("\t\ttry {\n");
        fileLines.add("\t\t\t" + fileName + "Dto dto =" + fileNameVar + "Dao.get(id);\n");
        fileLines.add("\t\t\treturn BaseResult.success(dto);\n");
        fileLines.add("\t\t}catch (Exception e) {\n");
        fileLines.add("\t\t\tlogger.error(\"" + className + ".get  error:{}\",e);\n");
        fileLines.add("\t\t\treturn BaseResult.fail();\n");
        fileLines.add("\t\t}\n");
        fileLines.add("\t}\n\n");
        // find
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic BaseResult<Page<" + fileName + "Dto>> find(@RequestBody " + fileName + "ReqDto " + fileNameVar + "ReqDto){\n");
        fileLines.add("\t\ttry {\n");
        fileLines.add("\t\t\t// 创建分页对象\n");
        fileLines.add("\t\t\tPage<" + fileName + "Dto> page = new Page<>();\n");
        fileLines.add("\t\t\t// 创建dto对象集合\n");
        fileLines.add("\t\t\tArrayList<" + fileName + "Dto> dtoList = new ArrayList<>();\n");
        fileLines.add("\t\t\t// 获取数据库中的分页对象\n");
        fileLines.add("\t\t\tPage<Object> objectPage = " + fileNameVar + "Dao.find(" + fileNameVar + "ReqDto);\n");
        fileLines.add("\t\t\t// 将对象属性转至dto对象中\n");
        fileLines.add("\t\t\tfor (Object obj : objectPage.getDatas()) {\n");
        fileLines.add("\t\t\t\t" + fileNameVar + "dto = new  " + fileNameVar + "();\n");
        fileLines.add("\t\t\t\tBeanUtils.copyProperties( obj, dto);\n");
        fileLines.add("\t\t\t\tdtoList.add(dto);\n");
        fileLines.add("\t\t\t}\n");
        fileLines.add("\t\t\tpage.setDatas(dtoList);\n");
        fileLines.add("\t\t\tpage.setPageIndex(objectPage.getPageIndex());\n");
        fileLines.add("\t\t\tpage.setPageSize(objectPage.getPageSize());\n");
        fileLines.add("\t\t\tpage.setTotalPage(objectPage.getTotalPage());\n");
        fileLines.add("\t\t\tpage.setTotalRecord(objectPage.getTotalRecord());\n");
        fileLines.add("\t\t\tpage.setPageOffset(objectPage.getPageOffset());\n");
        fileLines.add("\t\t\treturn BaseResult.success(page);\n");
        fileLines.add("\t\t}catch (Exception e) {\n");
        fileLines.add("\t\t\tlogger.error(\"" + className + ".find  error:{}\",e);\n");
        fileLines.add("\t\t\treturn BaseResult.fail();\n");
        fileLines.add("\t\t}\n");
        fileLines.add("\t}\n\n");
        fileLines.add("}\n\n");
        return fileLines;
    }

    public static List<String> writeDao(String apiPackageName, String modelPackageName, String fileName) {
        List<String> fileLines = new ArrayList<String>();
        fileLines.add("package " + apiPackageName + ".dao;\n\n");
        fileLines.add("import " + modelPackageName + ".dto." + fileName + "Dto;\n");
        fileLines.add("import " + modelPackageName + ".req." + fileName + "ReqDto;\n");
        fileLines.add("import com.cehome.cloud.common.dao.base.Page;\n");
        fileLines.add(CLASS_NOTES);
        fileLines.add("public interface " + "I" + fileName + "Dao" + "{\n\n");
        String fileNameVar = fileName.substring(0, 1).toLowerCase() + fileName.substring(1);
        // add
        fileLines.add("\tvoid add(" + fileName + "ReqDto  " + fileNameVar + "reqDto);\n\n");
        // update
        fileLines.add("\tvoid update(" + fileName + "ReqDto  " + fileNameVar + "reqDto);\n\n");
        // delete
        fileLines.add("\tvoid delete(Integer id);\n\n");
        // get
        fileLines.add("\t" + fileName + "Dto get(Integer id);\n\n");
        // find
        fileLines.add("\tPage<Object> find(" + fileName + "ReqDto  " + fileNameVar + "reqDto);\n\n");
        fileLines.add("}\n\n");
        return fileLines;
    }

    public static List<String> writeDaoImpl(String apiPackageName, String modelPackageName, String fileName) {
        List<String> fileLines = new ArrayList<String>();
        fileLines.add("package " + apiPackageName + ".dao.impl;\n\n");
        fileLines.add("import " + apiPackageName + ".dao.I" + fileName + "Dao;\n");
        fileLines.add("import " + modelPackageName + ".dto." + fileName + "Dto;\n");
        fileLines.add("import " + modelPackageName + ".req." + fileName + "ReqDto;\n");
        fileLines.add("import com.cehome.cloud.common.dao.base.BaseDao;\n");
        fileLines.add("import com.cehome.cloud.common.dao.base.Page;\n");
        fileLines.add("import org.springframework.beans.BeanUtils;\n");
        fileLines.add("import org.springframework.stereotype.Repository;\n");
        fileLines.add("import " + modelPackageName + ".po." + fileName + ";");
        String fileNameVar = fileName.substring(0, 1).toLowerCase() + fileName.substring(1);
        fileLines.add(CLASS_NOTES);
        fileLines.add("@Repository\n");
        fileLines.add("public class " + fileName + "DaoImpl extends BaseDao<" + fileName + ">  implements I" + fileName + "Dao{\n\n");
        // add
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic void add(" + fileName + "ReqDto " + fileNameVar + "ReqDto){\n");
        fileLines.add("\t\tsuper.add(" + fileNameVar + "ReqDto);\n");
        fileLines.add("\t}\n\n");
        // update
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic void update(" + fileName + "ReqDto " + fileNameVar + "ReqDto){\n");
        fileLines.add("\t\tsuper.update(" + fileNameVar + "ReqDto);\n");
        fileLines.add("\t}\n\n");
        // delete
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic void delete(Integer id){\n");
        fileLines.add("\t\tsuper.delete(id);\n");
        fileLines.add("\t}\n\n");
        // get
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic " + fileName + "Dto get(Integer id){\n");
        fileLines.add("\t\t" + fileName + "Dto dto = new " + fileName + "Dto();\n");
        fileLines.add("\t\t" + fileName + " po = super.get(id);\n");
        fileLines.add("\t\tif(po != null){\n");
        fileLines.add("\t\t\tBeanUtils.copyProperties(po, dto);\n");
        fileLines.add("\t\t}\n");
        fileLines.add("\t\treturn  dto;\n");
        fileLines.add("\t}\n\n");
        // find
        fileLines.add(METHOD_NOTES);
        fileLines.add("\t@Override\n");
        fileLines.add("\tpublic Page<Object> find(" + fileName + "ReqDto " + fileNameVar + "ReqDto){\n");
        fileLines.add("\t\tint pageIndex = (" + fileNameVar + "ReqDto.getPageOffset() - 1) * " + fileNameVar + "ReqDto.getPageSize();\n");
        fileLines.add("\t\treturn super.find(" + fileNameVar + "ReqDto, pageIndex, " + fileNameVar + "ReqDto.getPageSize());\n");
        fileLines.add("\t}\n\n");
        fileLines.add("}\n\n");
        return fileLines;
    }

    public static List<String> writeMapper(String tableName, String modelName, String packageName, List<DbColumn> dbList, List<JavaColumn> javaList) {
        List<String> fileLines = new ArrayList<String>();
        fileLines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        fileLines.add("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        fileLines.add("<mapper namespace=\"" + packageName + ".po." + modelName + "\">\n\n");

        // resultMap
        fileLines.add("\t<resultMap id=\"baseResultMap\" type=\"" + packageName + ".dto." + modelName + "Dto\">\n");
        for (int i = 0; i < dbList.size(); i++) {
            DbColumn dc = dbList.get(i);
            JavaColumn jc = javaList.get(i);
            fileLines.add("\t\t<result column=\"" + dc.getName() + "\" property=\"" + jc.getName() + "\" />\n");
        }
        fileLines.add("\t</resultMap>\n\n");

        // columns
        fileLines.add("\t<sql id=\"columns\">\n");
        for (int i = 0; i < dbList.size(); i++) {
            DbColumn dc = dbList.get(i);
            if (i == 0) {
                fileLines.add("\t\t");
            }
            fileLines.add(dc.getName());
            if (i == dbList.size() - 1) {
                fileLines.add("\n");
            } else {
                fileLines.add(", ");
            }
        }
        fileLines.add("\t</sql>\n\n");

        // wheres
        fileLines.add("\t<sql id=\"wheres\">\n");
        fileLines.add("\t\twhere 1 = 1\n");
        for (int i = 0; i < dbList.size(); i++) {
            DbColumn dc = dbList.get(i);
            JavaColumn jc = javaList.get(i);
            if ("String".equals(jc.getType())) {
                fileLines.add("\t\t<if test=\"" + jc.getName() + " != null and " + jc.getName() + " != ''\">\n");
            } else {
                fileLines.add("\t\t<if test=\"" + jc.getName() + " != null\">\n");
            }
            fileLines.add("\t\t\tand " + dc.getName() + " = #{" + jc.getName() + "} \n");
            fileLines.add("\t\t</if>\n");
        }
        fileLines.add("\t</sql>\n\n");

        // orders
        fileLines.add("\t<sql id=\"orders\">\n");
        fileLines.add("\t\t<if test=\"orders != null and orders != ''\">\n");
        fileLines.add("\t\t\torder by ${orders}\n");
        fileLines.add("\t\t</if>\n");
        fileLines.add("\t</sql>\n\n");

        // list
        fileLines.add("\t<select id=\"list\" parameterType=\"" + packageName + ".req." + modelName + "ReqDto\" resultMap=\"baseResultMap\">\n");
        fileLines.add("\t\tselect\n\t\t\t<include refid=\"columns\" />\n\t\tfrom " + tableName
                + "\n\t\t<include refid=\"wheres\" />\n\t\t<include refid=\"orders\" />\n");
        fileLines.add("\t</select>\n\n");

        // find
        fileLines.add("\t<select id=\"find\" parameterType=\"" + packageName + ".req." + modelName + "ReqDto\" resultMap=\"baseResultMap\">\n");
        fileLines.add("\t\tselect\n\t\t\t<include refid=\"columns\" />\n\t\tfrom " + tableName
                + "\n\t\t<include refid=\"wheres\" />\n\t\t<include refid=\"orders\" />\n\t\tlimit #{pageOffset}, #{pageSize}\n");
        fileLines.add("\t</select>\n\n");

        // findCount
        fileLines.add("\t<select id=\"find_count\" parameterType=\"" + packageName + ".req." + modelName
                + "ReqDto\" resultType=\"java.lang.Integer\">\n");
        fileLines.add("\t\tselect\n\t\t\tcount(1)\n\t\tfrom " + tableName + "\n\t\t<include refid=\"wheres\" />\n");
        fileLines.add("\t</select>\n\n");

        // get
        fileLines.add("\t<select id=\"selectByPrimaryKey\" parameterType=\"java.lang.Integer\" resultMap=\"baseResultMap\">\n");
        fileLines.add("\t\tselect\n\t\t\t<include refid=\"columns\" />\n\t\tfrom " + tableName + "\n\t\twhere id = #{id}\n\t\tlimit 1\n");
        fileLines.add("\t</select>\n\n");

        // insert
        fileLines.add("\t<insert id=\"insertSelective\" parameterType=\"" + packageName + ".req." + modelName
                + "ReqDto\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n");
        fileLines.add("\t\tinsert into " + tableName + " \n");
        fileLines.add("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n");
        for (int i = 0; i < dbList.size(); i++) {
            DbColumn dc = dbList.get(i);
            JavaColumn jc = javaList.get(i);
            if ("String".equals(jc.getType())) {
                fileLines.add("\t\t\t<if test=\"" + jc.getName() + " != null and " + jc.getName() + " != ''\">\n");
            } else {
                fileLines.add("\t\t\t<if test=\"" + jc.getName() + " != null\">\n");
            }
            fileLines.add("\t\t\t\t" + dc.getName());
            fileLines.add(",\n");
            fileLines.add("\t\t\t</if>\n");
        }
        fileLines.add("\t\t</trim>\n");
        fileLines.add("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n");
        for (int i = 0; i < javaList.size(); i++) {
            JavaColumn jc = javaList.get(i);
            if ("String".equals(jc.getType())) {
                fileLines.add("\t\t\t<if test=\"" + jc.getName() + " != null and " + jc.getName() + " != ''\">\n");
            } else {
                fileLines.add("\t\t\t<if test=\"" + jc.getName() + " != null\">\n");
            }
            fileLines.add("\t\t\t\t#{" + jc.getName() + "}");
            fileLines.add(",\n");
            fileLines.add("\t\t\t</if>\n");
        }
        fileLines.add("\t\t</trim>\n");
        fileLines.add("\t</insert>\n\n");

        // update
        fileLines.add("\t<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + packageName + ".po." + modelName + "\">\n");
        fileLines.add("\t\tupdate " + tableName + "\n");
        fileLines.add("\t\t<set>\n");
        for (int i = 0; i < dbList.size(); i++) {
            DbColumn dc = dbList.get(i);
            JavaColumn jc = javaList.get(i);
            if ("String".equals(jc.getType())) {
                fileLines.add("\t\t\t<if test=\"" + jc.getName() + " != null and " + jc.getName() + " != ''\">\n");
            } else {
                fileLines.add("\t\t\t<if test=\"" + jc.getName() + " != null\">\n");
            }
            fileLines.add("\t\t\t\t" + dc.getName() + " = #{" + jc.getName() + "},\n");
            fileLines.add("\t\t\t</if>\n");
        }
        fileLines.add("\t\t</set>\n");
        fileLines.add("\t\twhere id = #{id}\n");
        fileLines.add("\t</update>\n\n");

        // delete
        fileLines.add("\t<delete id=\"delete\" parameterType=\"java.lang.Integer\">\n");
        fileLines.add("\t\tdelete from " + tableName + " where id = #{id}\n");
        fileLines.add("\t</delete>\n\n");
        fileLines.add("</mapper>");

        return fileLines;
    }

    public static void outputFile(String fileName, List<String> fileLines) throws IOException {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        for (String line : fileLines) {
            writer.write(line);
        }
        writer.flush();
        writer.close();
    }
}

class DbColumn {

    private String name;

    private String type;
    private String remark;

    public DbColumn(String name, String type, String remark) {
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

class JavaColumn {

    private String name;

    private String type;

    private String remark;

    public JavaColumn(String name, String type, String remark) {
        String[] words = name.split("_");
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                name = words[i];
            } else {
                name += words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            }
        }

        this.name = name;
        this.remark = remark;

        if ("INT".equalsIgnoreCase(type)) {
            this.type = "Integer";
        } else if ("INT UNSIGNED".equalsIgnoreCase(type)) {
            this.type = "Integer";
        } else if ("BIGINT".equalsIgnoreCase(type)) {
            this.type = "Long";
        } else if ("SMALLINT".equalsIgnoreCase(type)) {
            this.type = "short";
        } else if ("FLOAT".equalsIgnoreCase(type)) {
            this.type = "float";
        } else if ("DECIMAL".equalsIgnoreCase(type)) {
            this.type = "BigDecimal";
        } else if ("VARCHAR".equalsIgnoreCase(type)) {
            this.type = "String";
        } else if (type.contains("TEXT")) {
            this.type = "String";
        } else if (type.contains("BLOB")) {
            this.type = "byte[]";
        } else if ("datetime".equalsIgnoreCase(type)) {
            this.type = "Date";
        } else if ("double".equalsIgnoreCase(type)) {
            this.type = "Double";
        } else if ("char".equalsIgnoreCase(type)) {
            this.type = "Integer";
        } else if ("text".equalsIgnoreCase(type)) {
            this.type = "String";
        } else if ("tinyint".equalsIgnoreCase(type)) {
            this.type = "Integer";
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}