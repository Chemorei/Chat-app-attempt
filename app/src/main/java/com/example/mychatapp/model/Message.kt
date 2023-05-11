package com.example.mychatapp.model

class Message {

    var messageId :String? =null
    var message :String? =null
    var SenderId :String? =null
    var imageUrl :String? =null
    var timeStamp :Long =0
    constructor(){}
    constructor(
        message :String?,
        SenderId :String?,
        timeStamp :Long
    ){
        this.message = message
        this.SenderId = SenderId
        this.timeStamp = timeStamp

    }
}