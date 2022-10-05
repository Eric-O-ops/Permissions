package com.geektech.permissions.ui.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.geektech.permissions.App
import com.geektech.permissions.BuildConfig
import com.geektech.permissions.databinding.FragmentShowPictureBinding

class ShowPictureFragment : Fragment() {

    private lateinit var binding: FragmentShowPictureBinding

     private val permissionLauncher: ActivityResultLauncher<String>
     = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
         if (!result){
             Toast.makeText(requireContext(), "You need permission",Toast.LENGTH_SHORT).show()

         }else{
             Toast.makeText(requireContext(), "The permission is granted",Toast.LENGTH_SHORT).show()
             takeAndAddPicture()
         }
     }

    private val takePictureIntent = registerForActivityResult(ActivityResultContracts.GetContent()){
        Glide.with(binding.image).load(it).into(binding.image)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        putPicture()

    }

    private fun putPicture(){
        binding.addPicture.setOnClickListener {

            if( shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) )
                {
                    askPermission()

                }
            else if(checkPermission() != PackageManager.PERMISSION_GRANTED &&
                App.preferences.getBooleanValue("askAlreadyPermission"))
                {
                    goToSettingsForPermission()

            }else {
                    askPermission()
                    App.preferences.setBooleanValue("askAlreadyPermission",true)

                }
            }
    }

    private fun askPermission(){
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

    }

    private fun takeAndAddPicture(){
            takePictureIntent.launch("image/*")

    }

    private fun checkPermission(): Int{
       return ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)

    }

    private fun goToSettingsForPermission(){
        Toast.makeText(requireContext(), "Please, give grant your permission",Toast.LENGTH_LONG).show()
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + BuildConfig.APPLICATION_ID)))

    }
}
