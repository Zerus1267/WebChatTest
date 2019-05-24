var Contacts = document.getElementsByClassName("chat_list");
var ActiveUser = document.getElementById("username");
var ContactUsers = document.getElementsByClassName("contact-username");
var ActiveUserId = document.getElementById("userId");
function changeClass() {
    // get all 'a' elements
/*

    this.currentUser = document.querySelector(".username");
    this.allUsers = document.querySelectorAll(".contact-username");
    this.ActiveUserContactBarIndex = -1;
    for(var i = 0; i < this.allUsers.size; i++){
        console.log(this.ActiveUser.textContent + " " + this.allUsers[i].textContent);
        if(this.currentUser.value === this.allUsers[i].value){
            this.ActiveUserContactBarIndex = i;
            break;
        }
    }
    console.log("Logged user is " + this.ActiveUserContactBarIndex);
    var temp = document.getElementsByClassName("chat_list");
    temp[this.ActiveUserContactBarIndex].classList.add("offline_contact");
*/



    var a = document.getElementsByClassName('chat_list active_chat');
    var domList = Array.prototype.slice.call(document.body.getElementsByClassName('chat_list'));
    for (i = 0; i < a.length; i++) {
        a[i].classList.remove('active_chat')
    }
    //console.log("Index of contact = " + domList.indexOf(this));
    if(!this.classList.contains("offline_contact")){
        this.classList.add('active_chat');
        var chatElem = document.getElementsByClassName('ChatWindows');
        for(j = 0; j < chatElem.length; j++){
            chatElem[j].style.visibility = 'hidden';
        }
        SelectedContactIndex = domList.indexOf(this);
        chatElem[domList.indexOf(this)].style.visibility = 'visible';
        SelectedUserName = ContactUsers[domList.indexOf(this)].textContent;
    }


}

for(var i=0;i<Contacts.length; i++){
    Contacts[i].addEventListener("click",changeClass,false);
}
function toServer() {

}

var but_send = window.document.querySelector(".msg_send_btn");
//but_send.addEventListener("click",e=>chatUnit.)
var chatUnit={
    onOpenSock () {

    },
    onMessage(msg) {
        //alert("Enter onMessage func!");
        //console.log(msg.date);
        //console.log(msg.text);
        let msgBlock = document.createElement("div");
        msgBlock.className = "incoming_msg";
        let msgIcon = document.createElement("div");
        msgIcon.className = "incoming_msg_img";
        let Icon = document.createElement("img");
        Icon.setAttribute('src', 'https://ptetutorials.com/images/user-profile.png');
        let recmsg = document.createElement("div");
        recmsg.className = "received_msg";
        let recText = document.createElement("div");
        recText.className = "received_withd_msg";
        let Text = document.createElement("p");
        Text.textContent = msg.text;
        recText.appendChild(Text);
        let msgSubText = document.createElement("span");
        msgSubText.className = "time_date";
        msgSubText.innerText = msg.date;
        recText.appendChild(msgSubText);
        recmsg.appendChild(recText);
        msgIcon.appendChild(Icon);
        msgBlock.appendChild(msgIcon);
        msgBlock.appendChild(recmsg);


        //console.log("Message to: " + msg.fromindex-1);
        this.msgContainer[msg.fromindex-1].appendChild(msgBlock);
        /*let msghtml = `
        <div class="outgoing_msg">
                <div class="sent_msg">
                    <p>Test which is a new approach to have all
                        solutions</p>
                    <span class="time_date"> 11:01 AM    |    June 9</span> </div>
            </div>`*/
    },
    onClose() {

    },
    reflect(msg){
        let msgBlock = document.createElement("div");
        msgBlock.className = "outgoing_msg";
        let msgG = document.createElement("div");
        msgG.className = "sent_msg";
        let msgText = document.createElement("p");
        msgText.innerText = temp.text;

        let msgspan = document.createElement("span");
        msgspan.className = "time_date";
        msgspan.innerText = temp.date;

        msgG.appendChild(msgText);
        msgG.appendChild(msgspan);
        msgBlock.appendChild(msgG);
        this.msgContainer[SelectedContactIndex].appendChild(msgBlock);
        console.log("Sending to me my message");
    },
    sendMessage(msg){
        //this.ws.send(JSON.stringify(msg));
        //alert("Send mess JSON");
        this.reflect(msg);
        this.msgTextArea.value = "";
        this.ws.send(JSON.stringify(msg));
    },

    openSocket() {
        //("Open socket func enter");
        console.log("Username = " + ActiveUser.textContent);
        this.ws = new WebSocket("ws://localhost:8082/chat/"+ActiveUser.textContent);
        this.ws.onopen = ()=>this.onOpenSock();
        this.ws.onmessage = (e)=>this.onMessage(JSON.parse(e.data));
        this.ws.onclose = ()=>this.onClose();
    },
    bindEvents() {
        //alert("Bind Events!");
        this.msgTextArea = document.querySelector(".write_msg");
        this.msgSendBtn = document.querySelector(".msg_send_btn");
        this.msgContainer = document.querySelectorAll(".ChatWindows");
        this.openSocket();
        this.msgSendBtn.addEventListener("click", e=>{
            e.preventDefault();
            //alert("Sendind " + this.msgTextArea.value);
            if(this.msgTextArea.value.toString()!="") {
                this.send();
            }
            else console.log("Empty message");
        })
    },
    dateControl(strdate){
        var months = new Map();
        months.set("01","January");
        months.set("02", "February");
        months.set("03", "March");
        months.set("04", "April");
        months.set("05", "May");
        months.set("06", "June");
        months.set("07", "July");
        months.set("08", "August");
        months.set("09", "September");
        months.set("10", "October");
        months.set("11", "November");
        months.set("12", "December");
        temp = strdate.toString().substring(0,2);
        console.log(temp);
        this.strdateday = months.get(temp);
    },
    send(){
        //date = new Date().format('m-d-Y h:i:s');
        //date1 = new Date().format('h:i');
        //date2 = new Date().format('d')
        //this.strdate = date1.toString();
        //console.log("Date is " + date.toString() + " | " + date1.toString());
        //new Date().format('m-d-Y h:i:s');
        //this.dateControl(date);
        //final = date1 + " AM" + " | " + this.strdateday + " " + date2;
        finalUser = SelectedUserName.replace('@','');
        temp={date:"Test Date",text:this.msgTextArea.value,to:finalUser,fromindex:ActiveUserId.innerText};

        //console.log(temp.date);
        //console.log(temp.text);
        //console.log(temp.to);
        this.sendMessage(temp);

    },
    init(){
        //alert("Init function");
        this.msgTextArea = document.querySelector(".write_msg");
        this.msgSendBtn = document.querySelector(".msg_send_btn");
        this.msgContainer = document.querySelector(".msg_history");


        //var username = `<%= sessionScope.user.getFirstname()%>`;
        //console.log(" "+username);
        this.bindEvents();
    }
}