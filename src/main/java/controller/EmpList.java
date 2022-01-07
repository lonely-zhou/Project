package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import po.Dept;
import po.Emp;
import service.EmpListService;

import java.util.List;

@Controller
public class EmpList {
    @Autowired
    private EmpListService empListService;

    @RequestMapping(value = "/empList.do")
    public String empList(String deptId, Model model) {
        List<Emp> emps = this.empListService.selectEmp(deptId);
        List<Dept> depts = this.empListService.selectDept();
        model.addAttribute("depts", depts);
        model.addAttribute("emps", emps);
        return "empList";
    }

    @RequestMapping(value = "/updateEmp.do", method = RequestMethod.POST)
    public String updateEmp(String id, String name, String sex, String info, Model model) {
        this.empListService.updateEmp(id, name, sex, info);
        String deptId = this.empListService.selectDeptId(id);
        List<Emp> emps = this.empListService.selectEmp(deptId);
        List<Dept> depts = this.empListService.selectDept();
        model.addAttribute("depts", depts);
        model.addAttribute("emps", emps);
        return "empList";
    }

    @RequestMapping(value = "/deleteEmp.do")
    public String deleteEmp(String id, String deptId, Model model) {
        this.empListService.deleteEmp(id);
        List<Emp> emps = this.empListService.selectEmp(deptId);
        List<Dept> depts = this.empListService.selectDept();
        model.addAttribute("depts", depts);
        model.addAttribute("emps", emps);
        return "empList";
    }
}
