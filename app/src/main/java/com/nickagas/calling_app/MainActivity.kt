package com.nickagas.calling_app

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nickagas.calling_app.databinding.ActivityMainBinding
import android.Manifest
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS = 1

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Log.i("nagas","init makeTelecomCall")
            if (checkPermissions()) {
                makeTelecomCall()
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun makeTelecomCall() {

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is granted, you can proceed with making phone calls
            Log.d("nagas","makeTelecomCall PERMISSION_GRANTED")
            val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            val phoneAccountHandle = PhoneAccountHandle(ComponentName(this, MyConnectionService::class.java), "myConnectionService")
            val phoneAccount = PhoneAccount.builder(phoneAccountHandle, "MyPhoneAccount")
                .setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER)
                .build()

            telecomManager.registerPhoneAccount(phoneAccount)
            val uri = Uri.fromParts("tel:", "6977368455", null)
            val bundle = Bundle()
//            bun
            telecomManager.placeCall(uri, bundle)



        } else {
            // Permission is not granted, request the permission from the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                123456
            )

        }

    }
    private fun checkPermissions(): Boolean {
        val callPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val manageOwnCallsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_OWN_CALLS)
        val readPhoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)

        val permissionsNeeded = mutableListOf<String>()
        if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }
        if (manageOwnCallsPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.MANAGE_OWN_CALLS)
        }
        if (readPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE)
        }

        return if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), REQUEST_PERMISSIONS)
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                makeTelecomCall()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}