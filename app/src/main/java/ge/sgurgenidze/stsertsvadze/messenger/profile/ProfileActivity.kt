package ge.sgurgenidze.stsertsvadze.messenger.profile

import android.os.Bundle
import android.view.View
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun homepageImageButtonClicked(view: View) {
        finish()
    }

}
