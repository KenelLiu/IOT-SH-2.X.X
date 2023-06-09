
var wsUrl='http://127.0.0.1:8181/websocket/bodyiot';
var stompClient = null;
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS(wsUrl);
    stompClient = Stomp.over(socket);
    stompClient.connect(header, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
		//========订阅告警数据推送  需要区分单位,不能使用广播形式=======//
        /**
        stompClient.subscribe('/topic/alarm', function (data) {
            //showGreeting(JSON.parse(data.body).content);//解释数据
        	showGreeting(data.body);
        });
        */
		//========订阅告警数据推送 分组区分单位[A医院/B医院]=======/
	    stompClient.subscribe('/user/'+tenantId+'/alarm', function (data) {
            showGreeting(data.body);
        });           
        //=======此项功仅用于点对点测试==========//
	    stompClient.subscribe('/user/queue/chat', function (data) {
            showGreeting(data.body);
        });     	
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
//=========推送设备数据===============//
function pushTopic() {
	var dataJson={
		'customerId':1,
		'gwId':'0031',
		'mac':$("#mac").val(),
		'timestamp':new Date().getTime(),
		"tenantId":tenantId //租户Id,区分单位[A医院/B医院] 与分组订阅值保持一致
	};
    stompClient.send("/app/device/pushOne", {}, JSON.stringify(dataJson));
}

//=======此项功能仅用于点对点测试==========//
function pushQueue() {
	stompClient.send("/app/person/chat", {}, JSON.stringify({'mac': $("#mac").val(),'receiver':chatUser}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("#pushTopic").click(function() { pushTopic(); });
    $("#pushQueue").click(function() { pushQueue(); });
});

