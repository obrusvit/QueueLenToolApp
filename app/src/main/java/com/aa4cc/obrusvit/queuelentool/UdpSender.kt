package com.aa4cc.obrusvit.queuelentool

import android.util.Log
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

data class UdpSocketOptions(val remoteHost:String?, val remotePort:Int)

// Global
val DefaultUdpSocketSoftOptions = UdpSocketOptions("192.168.0.25", 10002)

object UdpSender {

    fun sendUDP(messageStr: String, udpSocketSoftOptions: UdpSocketOptions): Boolean {
        return try {
            /* These lines works correctly, tested separately */
            //Open a port to send the package
            val socket = DatagramSocket()
            socket.broadcast = true
            val sendData = messageStr.toByteArray()
            val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName(udpSocketSoftOptions.remoteHost), udpSocketSoftOptions.remotePort)
            socket.send(sendPacket)
            Log.i("SEND UDP", "fun sendBroadcast: packet sent to: " + InetAddress.getByName(udpSocketSoftOptions.remoteHost) + ":" + udpSocketSoftOptions.remotePort)
            true
        } catch (e: IOException) {
            Log.e("SEND UDP", "Exception caucht sending UDP")
            false
        }
    }
}

