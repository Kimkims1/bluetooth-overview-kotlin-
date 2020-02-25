package com.carldroid.bluetoothkt

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BT: Int = 1;
    private val REQUEST_CODE_DISCOVERABLE_BT: Int = 2;


    lateinit var bAdapter: BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bAdapter == null) {
            bluetoothstatusTv.setText("Bluetooth is not available")
        } else {
            bluetoothstatusTv.setText("Bluetooth is available")
        }

        if (bAdapter.isEnabled) {
            bluetoothstatusIv.setImageResource(R.drawable.ic_bluetooth_on)
        } else {
            bluetoothstatusIv.setImageResource(R.drawable.ic_bluetooth_disabled)
        }

        turnonbtn.setOnClickListener {
            if (bAdapter.isEnabled) {
                Toast.makeText(this, "Already On", Toast.LENGTH_SHORT).show()
                //bluetoothstatusIv.setImageResource(R.drawable.ic_bluetooth_on)

            } else {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
            }

        }
        turnoffbtn.setOnClickListener {
            if (!bAdapter.isEnabled) {
                Toast.makeText(this, "Already Off", Toast.LENGTH_SHORT).show()
            } else {
                bAdapter.disable()
                bluetoothstatusIv.setImageResource(R.drawable.ic_bluetooth_disabled)
                Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()

            }
        }

        discoverablebtn.setOnClickListener {
            if (!bAdapter.isDiscovering) {
                Toast.makeText(this, "Making your device discoverable", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)
            }
        }

        pairedbtn.setOnClickListener {
            if (bAdapter.isEnabled) {
                pairedtv.setText("Paired Devices")
                //get list of paired devices
                val devices = bAdapter.bondedDevices
                for (device in devices) {
                    val deviceName = device.name
                    val deviceAdress = device.address
                    pairedtv.append("\nDevice: $deviceName ,$deviceAdress ")
                }
            } else {
                Toast.makeText(this, "Turn on bluetooth first", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            REQUEST_CODE_ENABLE_BT -> {
                if (requestCode == Activity.RESULT_OK) {
                    bluetoothstatusIv.setImageResource(R.drawable.ic_bluetooth_on)
                    Toast.makeText(this, "Bluetooth is Already On", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Bluetooth Couldn't be turned On", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
