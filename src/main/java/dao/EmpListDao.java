package dao;

import org.apache.ibatis.annotations.Param;
import po.Dept;
import po.Emp;

import java.util.List;

public interface EmpListDao {
    List<Emp> selectEmp(@Param("deptId") String dept);

    List<Dept> selectDept();

    void updateEmp(@Param("id") String id, @Param("name") String name, @Param("sex") String sex, @Param("info") String info);

    String selectDeptId(@Param("id") String id);

    void deleteEmp(@Param("id") String id);
}
