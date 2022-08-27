$(function () {
    var username; //当前用户名
    var toUsername ="大厅";//默认是在大厅聊天
    //简单模拟的用户登陆
    while (!username) {
        username = prompt("请输入您的临时昵称：");
    }
    var ws = new WebSocket("ws://localhost/chat/" + username);
    var resultObj;//接受到的推送数据
    ws.onopen = function () {
        console.log("websocket连接成功")
    }

    ws.onmessage = function (event) {
        resultObj = JSON.parse(event.data);
        console.log('收到推送数据', resultObj)
        $("#onlineCount").text("【当前在线人数" + resultObj.onlineCount + "人】");
        //展示好友在线列表
        if (resultObj.onlineUsernames) {
            $("#userList").empty();
            $("#userList").append(`<p style="color: #ac7e61">【点击和指定人聊天】</p><p><a style="color: #e6a23c" href="">大厅</a></p>`)
            for (let name of resultObj.onlineUsernames) {
                if (name != username) {
                    $("#userList").append(`<p><a style="color: #e6a23c"  href="">${name}</a></p>`)
                }
            }
        }

        //展示接受到的消息
        if (resultObj.message) {
            var html = `<div class="${resultObj.fromUsername == username ? 'messageRight' : 'messageLeft'}">
                                    <div style="color: #000000 ;" >昵称:${resultObj.fromUsername}</div>
                                    <div style="color: #FFFFFF ;" ><h3>${resultObj.message}</h3></div>
                                </div>`;
            $("#chat").append(html);
            // 最底部
            var div = document.getElementById('chat');
            div.scrollTop = div.scrollHeight;

            //将接受到的消息存储到sessionStorage对象中，便于加载数据
            if(resultObj.toUsername=='大厅'){
                var chatData = sessionStorage.getItem('大厅');
                if(chatData){
                    html = chatData +html;
                }
                sessionStorage.setItem('大厅',html);
            }else{
                var chatData = sessionStorage.getItem(resultObj.fromUsername);
                if(chatData){
                    html = chatData +html;
                }
                sessionStorage.setItem(resultObj.fromUsername,html);
            }

        }
    }

    ws.onclose = function () {
        console.log("websocket连接关闭")
    }

    $("#sendBtn").click(function () {
        var msg = $("#message").val();
        var json = {'fromUsername': username, 'toUsername': toUsername, 'message': msg};
        ws.send(JSON.stringify(json));//向服务端推送数据
        $("#message").val('');//清除输入框的内容
        //如果指定了发送给谁的消息，则同步储存到sessionStorage中
        if(toUsername && toUsername!='大厅'){
            var html = `<div class="messageRight">
                                    <div>${username}</div>
                                    <div>${msg}</div>
                                </div>`;
            $("#chat").append(html)
            var chatData = sessionStorage.getItem(toUsername);
            if(chatData){
                html = chatData +html;
            }
            sessionStorage.setItem(toUsername,html);
        }

    });

    //点击好友列表，先清除当前聊天内容，并加载对应的聊天记录
    $("#userList").on("click","a",function () {
        toUsername = $(this).text();
        $("#title").text("正在和"+toUsername+"聊天");
        let chatData = sessionStorage.getItem(toUsername);
        $("#chat").empty().html(chatData);
        return false;
    })
})