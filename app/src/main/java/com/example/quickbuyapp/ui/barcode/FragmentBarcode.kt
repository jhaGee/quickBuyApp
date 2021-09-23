package com.example.quickbuyapp.ui.barcode


import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.quickbuyapp.R
import kotlinx.android.synthetic.main.barcode_fragment.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat



class FragmentBarcode : Fragment() {

    private  val CAMERA_REQUEST_CODE=101
    companion object {
        fun newInstance() = FragmentBarcode()
    }

    private lateinit var viewModel: FragmentBarcodeScannerViewModel
    private lateinit var codeScanner: CodeScanner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.barcode_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentBarcodeScannerViewModel::class.java)
        setper()
        codeScanner()
    }
    private fun codeScanner() {
        codeScanner = CodeScanner(requireActivity(), scanner_view)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread {
                    tv_textView.text = it.text
                }
            }
            errorCallback = ErrorCallback {
                requireActivity().runOnUiThread {
                    Log.e("Main", "Camera Initialisation Error: ${it.message}")
                }
            }
        }
        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setper() {
        val permission: Int =
            ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0]!= PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(context,"CAMERA PERMISSION REQUIRED",Toast.LENGTH_LONG).show()
            }
        }
    }
}


