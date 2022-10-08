package com.geektech.permissions.ui.fragments

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import coil.load
import com.geektech.permissions.BuildConfig
import com.geektech.permissions.databinding.FragmentShowPictureBinding
import java.io.File

class ShowPictureFragment : Fragment() {

    private lateinit var binding: FragmentShowPictureBinding

     private val permissionLauncher: ActivityResultLauncher<String>
     = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
         if (!result){

             if(!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                 Toast.makeText(requireContext(), "shouldShowRequestPermissionRationale",Toast.LENGTH_SHORT).show()
                 startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                     Uri.parse("package:" + BuildConfig.APPLICATION_ID)))

             }

         }else{
             Toast.makeText(requireContext(), "The permission is granted",Toast.LENGTH_SHORT).show()
             takePictureIntent.launch("image/*")

         }
     }

    private val takePictureIntent = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.image.load(it)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addPicture.setOnClickListener {
            checkPermission()

        }
    }

    private fun checkPermission(){
        when{
            ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED-> {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }
            else -> {
                takePictureIntent.launch("image/*")

            }
        }
    }
}
