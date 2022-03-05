<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>记享-邮箱验证</title>
    <style>
        table {
            width: 700px;
            margin: 0 auto;
        }

        #top {
            width: 700px;
            border-bottom: 1px solid #ccc;
            margin: 0 auto 30px;
        }

        #top table {
            font: 12px Tahoma, Arial, 宋体;
            height: 40px;
        }

        #content {
            width: 680px;
            padding: 0 10px;
            margin: 0 auto;
        }

        #content_top {
            line-height: 1.5;
            font-size: 14px;
            margin-bottom: 25px;
            color: #4d4d4d;
        }

        #content_top strong {
            display: block;
            margin-bottom: 15px;
        }

        #content_top strong span {
            color: #f60;
            font-size: 16px;
        }

        #verificationCode {
            color: #f60;
            font-size: 24px;
        }

        #content_bottom {
            margin-bottom: 30px;
        }

        #content_bottom small {
            display: block;
            margin-bottom: 20px;
            font-size: 12px;
            color: #747474;
        }

        #bottom {
            width: 700px;
            margin: 0 auto;
        }

        #bottom div {
            padding: 10px 10px 0;
            border-top: 1px solid #ccc;
            color: #747474;
            margin-bottom: 20px;
            line-height: 1.3em;
            font-size: 12px;
        }

        #content_top strong span {
            font-size: 18px;
            color: #FE4F70;
        }

        #sign {
            text-align: right;
            font-size: 18px;
            color: #FE4F70;
            font-weight: bold;
        }

        #verificationCode {
            height: 100px;
            width: 680px;
            text-align: center;
            margin: 30px 0;
        }

        #verificationCode div {
            height: 100px;
            width: 680px;

        }
    </style>
</head>

<body>
<div class="emailCode">
    <table>
        <tbody>
        <tr>
            <td>
                <div id="top">
                    <table>
                        <tbody>
                        <tr>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div id="content">
                    <div id="content_top">
                        <strong>尊敬的用户：您好！</strong>
                        <strong>
                            您的验证码为：
                        </strong>
                        <div id="verificationCode">
                            <span> ${code} </span>
                        </div>
                    </div>
                    <div id="content_bottom">
                        <small>
                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证账户安全
                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)
                        </small>
                    </div>
                </div>
                <div id="bottom">
                    <div>
                        <p>此为系统邮件，请勿回复<br>
                            请保管好您的邮箱，避免账号被他人盗用
                        </p>
                        <p id="sign">——记享</p>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>

</html>