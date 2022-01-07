# 校园订餐


## 安装 axios
```
npm install axios
[用例](https://www.axios-http.cn/docs/example)
const axios = require('axios');
```

```
// 向给定ID的用户发起请求
axios.get('/user?ID=12345')
 .then(function (response) {
    // 处理成功情况`
    console.log(response);
  })
  .catch(function (error) {
    // 处理错误情况`
    console.log(error);
 })
  .then(function () {
  // 总是会执行
 });
```
## 安装router
`vue add router`