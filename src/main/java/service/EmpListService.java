package service;

import org.apache.ibatis.annotations.Param;
import po.Dept;
import po.Emp;

import java.util.List;

public interface EmpListService {
    List<Emp> selectEmp(String deptId);

    List<Dept> selectDept();

    void updateEmp(String id, String name, String sex, String info);

    String selectDeptId(String id);

    void deleteEmp(String id);
}
