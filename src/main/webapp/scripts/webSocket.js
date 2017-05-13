/* 
 * Demonstration code for simple chat app using JEE 7 WebSockets support. 
 * This client-side JavaScript uses no 3rd party libraries, although those 
 * are also available.
 */

// Websocket Endpoint url (uses websocket (ws) protocol
// Secure version uses 'wss:'
// 'chat' is endpoint alias -- see ChatServerEndpoint.java under Source Packages
var endPointURL = "wss://"+window.location.host + "/jls-MyEcomApp/chat";
 
// Global variable for websocket client
var chatClient = null;
 
/*
 * Creates a websocket client and opens a connection to the server-side
 * end point that stays open until closed. The client reacts to incoming
 * messages from the server via the 'onmessage' event. This is handled in 
 * the code below by an anonymous function that parses the data sent from
 * the server as JSON and then modifies the DOM with the appropriate 
 * information. 
 */
function connect () {
    // open a connection to server
    chatClient = new WebSocket(endPointURL);
   
    /*
     * This is calle a "callback function". It's a function that gets called 
     * automatically by your web browser when data is received from the
     * server.
     * @param {type} event - contains the data pushed from the server
     * @returns {undefined}
     */
    chatClient.onmessage = function (event) {
        var messagesArea = document.getElementById("messages");
     alert("ChatClient"); 
        // JavaScript can parse text data into JSON
        var jsonObj = JSON.parse(event.data);
        // Get values from JSON object using simple OOP techniques ... 
        // THIS is the value of the JSON format!
        var message = jsonObj.user + ": " + jsonObj.message + "\r\n";
          
        messagesArea.value = messagesArea.value + "Message:" + message;
        messagesArea.scrollTop = messagesArea.scrollHeight;
    };
}
 
 /*
  * Closes the connection to the server-side end point
  */
function disconnect () {
    chatClient.close();
}
 
/*
 * Must be called only after a connection is open. Then it sends a message
 * in JSON format to the server-side end point. The end point then broadcasts
 * a response to all connected clients.
 * 
 * Notice that with WebSockets, communication can be one-way, from server to 
 * client, or two-way, using both server-to-client and client-to-server
 * communication. Use sendMessage with the onMessage event callback 
 * for two-way communication.
 */
function sendMessage() {
    var user = document.getElementById("userName").value.trim();
    if (user === "")
        alert ("Please enter your name!");
     
    var inputElement = document.getElementById("messageInput");
    var message = inputElement.value.trim();

    if (message !== "") {
        var jsonObj = {"user" : user, "message" : message};
        alert(jsonObj); 
        chatClient.send(JSON.stringify(jsonObj));
        inputElement.value = "";
    }
    else {
         alert("message"); 
    }
    inputElement.focus();
}