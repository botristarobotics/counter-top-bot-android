package com.botrista.countertopbot.repository.agei

import com.botrista.countertopbot.repository.BaseRepository
import com.botrista.countertopbot.util.Const
import com.chaquo.python.PyObject
import com.chaquo.python.Python

//TODO: Inject python object or not?
class DeviceRepositoryImpl() : BaseRepository(), DeviceRepository {
    private val py = Python.getInstance()
    private val ageiFirmwareModule: PyObject =
        py.getModule("drinkbot-firmware-interface.agei_firmware.firmware")

    private val agei = ageiFirmwareModule.callAttr(
        "AgeiFirmware",
        true,
        "WARNING",
        "/home/botrista/workspace/firmware_interface_logs",
        Const.SIM_ZMQ_ADDRESS
    )

    override suspend fun connect(): Boolean {
        return agei.callAttr("connect_mcu").toBoolean()
    }

    override suspend fun disconnect(): Boolean {
        return agei.callAttr("disconnect_mcu").toBoolean()
    }
}