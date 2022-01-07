package service.impl;

import dao.EmpListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.Dept;
import po.Emp;
import service.EmpListService;

import java.util.List;

@Service("EmpListService")
public class EmpListServiceImpl implements EmpListService {
    @Autowired
    private EmpListDao empListDao;

    @Override
    public List<Emp> selectEmp(String deptId) {
        return this.empListDao.selectEmp(deptId);
    }

    @Override
    public List<Dept> selectDept() {
        return this.empListDao.selectDept();
    }

    @Override
    public void updateEmp(String id, String name, String sex, String info) {
        this.empListDao.updateEmp(id, name, sex, info);
    }

    @Override
    public String selectDeptId(String id) {
        return this.empListDao.selectDeptId(id);
    }

    @Override
    public void deleteEmp(String id) {
        this.empListDao.deleteEmp(id);
    }
}
