function createForm(action, name, value, name2, value2) {
    //虚拟表单提交
    const temp = document.createElement("form");
    temp.action = action;
    temp.method = "post";
    temp.style.display = "none";
    const opt = document.createElement("textarea");
    const opt2 = document.createElement("textarea");
    opt.name = name;
    opt.value = value;
    opt2.name = name2;
    opt2.value = value2;
    temp.appendChild(opt);
    temp.appendChild(opt2);
    document.body.appendChild(temp);
    temp.submit();
}

function send(deptId) {
    createForm("empList.do", "deptId", deptId);
}

function updateInfo(id, stat) {
    const tr = document.getElementsByClassName("updateInfo" + id);
    if (tr[0].style.display == "none" && stat == "1") {
        tr[0].style.display = "table-row";
    } else {
        tr[0].style.display = "none";
        swal("无权限", "", "error")
    }
}

function deleteEmp(id, deptId, stat) {
    if (stat == "1") {
        createForm("deleteEmp.do", "id", id, "deptId", deptId);
    } else {
        // window.alert("无权限")
        swal("无权限", "", "error")
    }
}

function showTime() {
    const time = document.getElementById("time");
    const month = new Date().getMonth() + 1;
    const day = new Date().getDate();
    time.innerHTML = month + " 月 " + day + " 日";
}