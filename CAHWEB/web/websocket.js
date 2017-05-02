/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//JQUERY

    $(document).keypress(function(e) { 
        if(e.which === 13) {
            sendMsg();
            $("#client_msg").val("");
        }
    });
// WEBSOCKET
window.onload = init;
var socket = new WebSocket("ws://localhost:8080/CAHWEB/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var Client = JSON.parse(event.data);
    if (Client.action === "add") {
        printClientElement(Client);
    }
    if (Client.action === "number") {
      var a =  $("#hiddenInput").val(Client.id);
          alert(Client.id);
    }
    
    if (Client.action === "join") {
        printClientElement(Client);
    }
    if (Client.action === "msg") {
         $("#client_msg").val("");
         $("textarea").append(Client.status+"\n");
    }
    if (Client.action === "remove") {
        document.getElementById(Client.id).remove();
        //Client.parentNode.removeChild(Client);
    }
    if (Client.action === "toggle") {
                alert("toggle");
        var node = document.getElementById(Client.id);
        var statusText = node.children[2];
        if (Client.status === "On") {
            statusText.innerHTML = "Status: " + Client.status + " (<a href=\"#\" OnClick=toggleClient(" + Client.id + ")>Turn off</a>)";
        } else if (Client.status === "Off") {
            statusText.innerHTML = "Status: " + Client.status + " (<a href=\"#\" OnClick=toggleClient(" + Client.id + ")>Turn on</a>)";
        }
    }
}

function addClient(name, type, description) {
    var ClientAction = {
        action: "add",
        name: name,
        type: type,
        description: description
    };
    socket.send(JSON.stringify(ClientAction));
}

function removeClient(element) {
    var id = element;
    var ClientAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(ClientAction));
}

function toggleClient(element) {
    var id = element;
    var ClientAction = {
        action: "toggle",
        id: id
    };
    socket.send(JSON.stringify(ClientAction));
}
function numbers() {
    var ClientAction = {
        action: "number"
    };
    socket.send(JSON.stringify(ClientAction));
}
function toggleJoinClient(element) {
    var id = element;
    var ClientAction = {
        action: "join",
        id: id
    };
    socket.send(JSON.stringify(ClientAction));
}

function printClientElement(Client) {
    var content = document.getElementById("content");
    
    var ClientDiv = document.createElement("div");
    ClientDiv.setAttribute("id", Client.id);
    ClientDiv.setAttribute("class", "client Game " );
    content.appendChild(ClientDiv);

    var ClientName = document.createElement("span");
    ClientName.setAttribute("class", "ClientName");
    ClientName.innerHTML = Client.name;
    ClientDiv.appendChild(ClientName);

    var ClientType = document.createElement("span");
    ClientType.innerHTML = "<b>Type:</b> " + Client.type;
    ClientDiv.appendChild(ClientType);

    var ClientStatus = document.createElement("span");
    if (Client.status === "On") {
        ClientStatus.innerHTML = "<b>Status:</b> " + Client.status + " (<a href=\"#\" OnClick=toggleClient(" + Client.id + ")>Turn off</a>)";
    } else if (Client.status === "Off") {
        ClientStatus.innerHTML = "<b>Status:</b> " + Client.status + " (<a href=\"#\" OnClick=toggleClient(" + Client.id + ")>Turn on</a>)";
        //ClientDiv.setAttribute("class", "Client off");
    }
    else if (Client.status === "joined") {
        ClientStatus.innerHTML = "<b>Status:</b> " + Client.status + " (<a href=\"#\" OnClick=toggleJoinClient(" + Client.id + ")>joined</a>)";
    }
    else{
         ClientStatus.innerHTML = "<b>Status:</b> " + Client.status + " (<a href=\"#\" OnClick=toggleJoinClient(" + Client.id + ")>join</a>)";
    }
    
    ClientDiv.appendChild(ClientStatus);

    var ClientDescription = document.createElement("span");
    ClientDescription.innerHTML = "<b>Comments:</b> " + Client.description;
    ClientDiv.appendChild(ClientDescription);

    var removeClient = document.createElement("span");
    removeClient.setAttribute("class", "removeClient");
    removeClient.innerHTML = "<a href=\"#\" OnClick=removeClient(" + Client.id + ")>Remove Client</a>";
    ClientDiv.appendChild(removeClient);
}

function showForm() {
    document.getElementById("addClientForm").style.display = '';
}

function hideForm() {
    document.getElementById("addClientForm").style.display = "none";
}
function PickCard(){
 $("#PickCard").val("0");
alert( $("#PickCard").val());
}
function StartGame(){
    $("#play").remove();
    $(".play").css("display","block");

}
function  requestGame(){
    var action = "request";
        if ($("#ToggleGame").val() === "Join"){
            action = "join";
           $("#ToggleGame").val("Joined").css("background-color","lightBlue");
           $("#butonsDiv").append("<input type='button' id='play' class='button' value='Play' onclick='StartGame()'>");
           $("#play").css("background-color","red");
        }else {
           $("#ToggleGame").val("Join").css("background-color","#5eb85e");
        }
    var form = document.getElementById("addClientForm");
    var name = form.elements["client_name"].value;
    var ClientAction = {
        action: action,
        name: name
    }; 
    socket.send(JSON.stringify(ClientAction));    
}
function sendMsg (){
    var action = "msg";
    var form = document.getElementById("addClientForm");
    var div =  document.getElementById("messageForm");
    var name = form.elements["client_name"].value;
    var msg = div.elements["client_msg"].value;
    var ClientAction = {
        action: action,
        name: name,
        message:msg
    }; 
    socket.send(JSON.stringify(ClientAction));  
    
}
function formSubmit() {
    var form = document.getElementById("addClientForm");
    var name = form.elements["client_name"].value;
    var type = form.elements["client_type"].value;
    var description = form.elements["client_description"].value;
    hideForm();
    document.getElementById("addClientForm").reset();
    addClient(name, type, description);
}
function init() {
    hideForm();
}