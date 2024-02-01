package com.ipsmeet.phonepepaypagesdk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ipsmeet.phonepepaypagesdk.databinding.ActivityMainBinding
import com.phonepe.intent.sdk.api.B2BPGRequestBuilder
import com.phonepe.intent.sdk.api.PhonePe
import com.phonepe.intent.sdk.api.PhonePeInitException
import com.phonepe.intent.sdk.api.models.PhonePeEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.Charset
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val merchantID = "PGTESTPAYUAT"
    private lateinit var merchantTID: String
    private var apiEndPoint = "/pg/v1/pay"
    private val saltKey = "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399"
    private lateinit var checksum: String
    private lateinit var payloadBase64: String
    private val B2B_PG_REQUEST_CODE = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        merchantTID = System.currentTimeMillis().toString()

        // Initialize PhonePe
        PhonePe.init(this, PhonePeEnvironment.UAT_SIMULATOR, merchantID, null)

        // Create JSON object
        val data = JSONObject()
        data.put("merchantTransactionId", System.currentTimeMillis().toString()) //String. Mandatory
        data.put("merchantId" , merchantID) //String. Mandatory
        data.put("merchantUserId" , System.currentTimeMillis().toString()) //String. Mandatory
        data.put("amount", 100 ) //Long. Mandatory
        data.put("callbackUrl", "https://webhook.site/78c109f4-036f-4662-acdf-b26a0d1e8997") //String. Mandatory

        val paymentInstrument = JSONObject()
        paymentInstrument.put("type", "PAY_PAGE")

        data.put("paymentInstrument", paymentInstrument)

        // Encode JSON object in Base64
        payloadBase64 = android.util.Base64.encodeToString(
            data.toString().toByteArray(Charset.defaultCharset()), android.util.Base64.NO_WRAP
        )

        // Create checksum
        checksum = sha256(payloadBase64 + apiEndPoint + saltKey) + "###1"

        Log.d("phonepe", "onCreate: $checksum")
        Log.d("phonepe", "payloadBase64: $payloadBase64")

        val headers: HashMap<String, String> = HashMap()
        headers["X-CALLBACK-URL"] = "https://webhook.site/78c109f4-036f-4662-acdf-b26a0d1e8997" // Merchant server URL
        headers["X-CALL-MODE"] = "POST"

        // Construct B2BPGRequest
        val b2BPGRequest = B2BPGRequestBuilder()
            .setData(payloadBase64)
            .setChecksum(checksum)
            .setUrl(apiEndPoint)
            .build()

        // Launch Intent
        binding.btnLaunchPayment.setOnClickListener {
            try {
                startActivityForResult(
                    PhonePe.getImplicitIntent(this, b2BPGRequest, "")!!,
                    B2B_PG_REQUEST_CODE
                )
            } catch(e: PhonePeInitException){
                e.printStackTrace()
            }
        }
    }

    // Generate checksum
    private fun sha256(input: String): String {
        val bytes = input.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == B2B_PG_REQUEST_CODE) {
            Toast.makeText(this, "onActivityResult()", Toast.LENGTH_SHORT).show()

            /*This callback indicates only about completion of UI flow.
            Inform your server to make the transaction
            status call to get the status. Update your app with the
            success/failure status.*/
        }
    }

}