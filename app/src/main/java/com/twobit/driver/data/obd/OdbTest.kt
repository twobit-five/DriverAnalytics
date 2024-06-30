package com.twobit.driver.data.obd

import android.bluetooth.BluetoothSocket
import android.util.Log
import com.github.eltonvs.obd.command.at.ResetAdapterCommand
import com.github.eltonvs.obd.command.control.AvailablePIDsCommand
import com.github.eltonvs.obd.command.control.VINCommand
import com.github.eltonvs.obd.command.engine.RPMCommand
import com.github.eltonvs.obd.command.engine.SpeedCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection

class OdbTest (
    private val socket: BluetoothSocket
) {
    private val obdConnection = ObdDeviceConnection(socket.inputStream, socket.outputStream)

    //creating functions for commands to be called on button press
    suspend fun getVIN(){
        val runVINCommand = obdConnection.run(VINCommand())
        Log.i("ObdTest", "VIN: $runVINCommand")
    }

    suspend fun resetAdapter(){
        val runResetAdapterCommand = obdConnection.run(ResetAdapterCommand())
        Log.i("ObdTest", "Reset Adapter Response: $runResetAdapterCommand")
    }

//    suspend fun searchCommands() {
//        val runAvailablePIDsCommand = obdConnection.run(AvailablePIDsCommand())
//        Log.i("ObdTest", "Available Commands Response: $runAvailablePIDsCommand")
//    }

    //to run while collecting data
    suspend fun onDataCollect(){
        //TODO create a loop that starts collecting data and storing it locally until stopped by user
        val tempSpeed = obdConnection.run(SpeedCommand())
        Log.i("ObdTest", "OBD2 Response: $tempSpeed")
        val tempRPM = obdConnection.run(RPMCommand())
        Log.i("ObdTest", "OBD2 Response: $tempRPM")
    }
}
