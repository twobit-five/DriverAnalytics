package com.twobit.driver.data.obd

import android.bluetooth.BluetoothSocket
import android.util.Log
import com.github.eltonvs.obd.command.at.ResetAdapterCommand
import com.github.eltonvs.obd.command.control.AvailablePIDsCommand
import com.github.eltonvs.obd.command.engine.RPMCommand
import com.github.eltonvs.obd.command.engine.SpeedCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection

const val TAG: String = "OBDModule"

class OdbTest (
    private val socket: BluetoothSocket
) {

    private val obdConnection = ObdDeviceConnection(socket.inputStream, socket.outputStream)

    //to run each time the device is connected
    suspend fun onOBDConnect(){
        val runResetAdapterCommand = obdConnection.run(ResetAdapterCommand())
        Log.i("ObdTest", "Reset Adapter Response: $runResetAdapterCommand")

        //FIXME val runAvailablePIDsCommand = obdConnection.run(AvailablePIDsCommand())
        //Log.i("ObdTest", "Available Commands Response: $runAvailablePIDsCommand")
    }

    //to run while collecting data
    suspend fun onDataCollect(){
        //TODO create a loop that starts collecting data and storing it locally until stopped by user
        val tempSpeed = obdConnection.run(SpeedCommand())
        Log.i("ObdTest", "OBD2 Response: $tempSpeed")
        val tempRPM = obdConnection.run(RPMCommand())
        Log.i("ObdTest", "OBD2 Response: $tempRPM")
    }
}
