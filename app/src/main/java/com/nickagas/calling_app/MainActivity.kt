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

class MainActivity : AppCompatActivity() {

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
            Log.i("nagas makeTelecomCall","init ")
            makeTelecomCall()

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

            val phoneAccountHandle = PhoneAccountHandle(
                ComponentName(applicationContext, MyConnectionService::class.java),
                "UniqueIdentifier"
            )
            val phoneAccount = PhoneAccount.Builder(phoneAccountHandle, "YourAppLabel")
                .setCapabilities(PhoneAccount.CAPABILITY_CONNECTION_MANAGER)//CAPABILITY_CONNECTION_MANAGER CAPABILITY_SELF_MANAGED
                .build()
            val telecomManager = applicationContext.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            telecomManager.registerPhoneAccount(phoneAccount)


            val uri = Uri.fromParts(PhoneAccount.SCHEME_TEL, "xxxxxxx", null)
            val bundle = Bundle()
            bundle.putParcelable(
                TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE,
                phoneAccountHandle
            )
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
}