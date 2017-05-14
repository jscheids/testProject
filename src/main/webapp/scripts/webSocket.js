/*
Custom javascript to support websocket "admin chat feature"
 */

// Websocket Endpoint url (uses websocket (ws) protocol to allow "instant messaging" between users on the index.jsp page.
// 'chat' is endpoint alias, see MessagingEndpoint.java for more information on how the websockect is used.
var endPointURL = "wss://"+window.location.host + "/jls-MyEcomApp/chat";

// Global variable for websocket client
var chatClient = null;

/*
 * This function creates a  websocket client and opens a connection to the server-side
 * end point.
 * Will remain open until closed.
 * Client reacts to incoming messages from the server via the 'onmessage' event.
 * Functionality handled here by an anonymous function that parses data sent from
 * the server as JSON and then modifies the DOM (the message window) with the message.
 */
function connect () {
    // open connection to server
    chatClient = new WebSocket(endPointURL);

    /*
     * This function is a callback function- called automatically by the web browser when data is received from the server.
     * @param {type} event - contains the data pushed from the server
     * @returns {undefined}
     */
    chatClient.onmessage = function (event) {
        var messagesArea = document.getElementById("messages");
        // parse text data into JSON
        var jsonObj = JSON.parse(event.data);
        var message =  jsonObj.message + "\r\n";
        messagesArea.value = messagesArea.value + "Message:" + message;
        messagesArea.scrollTop = messagesArea.scrollHeight;
    };
}

 /*
  * Close the connection to server-side end point
  */
function disconnect () {
    chatClient.close();
}

/*
 * This function should only be called  after a connection is open.
 * Sends JSON formated msg to end point (Server Side),which then can "broadcast"response to all connected clients.
 * Use sendMessage with the onMessage event callback for two-way communication.
 */
function sendMessage() {
    var inputElement = document.getElementById("messageInput");
    var message = inputElement.value.trim();

    if (message !== "") {
        var jsonObj = {"message" : message};
        chatClient.send(JSON.stringify(jsonObj));
        inputElement.value = "";
    }
    else {
         alert("Please enter msg");
    }
    inputElement.focus();
}