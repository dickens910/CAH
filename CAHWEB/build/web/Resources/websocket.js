/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = init;
var socket = new WebSocket("ws://localhost:8080/CAHwebSoap/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var Client = JSON.parse(event.data);
    if (Client.action === "add") {
        printClientElement(Client);
    }
    if (Client.action === "remove") {
        document.getElementById(Client.id).remove();
        //Client.parentNode.removeChild(Client);
    }
    if (Client.action === "toggle") {
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

function printClientElement(Client) {
    var content = document.getElementById("content");
    
    var ClientDiv = document.createElement("div");
    ClientDiv.setAttribute("id", Client.id);
    ClientDiv.setAttribute("class", "Client " + Client.type);
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