package es.ulpgc.eite.clean.bye

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import es.ulpgc.eite.clean.R
import es.ulpgc.eite.clean.mvp.GenericActivity
import es.ulpgc.eite.clean.util.gone
import es.ulpgc.eite.clean.util.invisible
import es.ulpgc.eite.clean.util.visible
import kotlinx.android.synthetic.main.activity_bye.*
import kotlinx.android.synthetic.main.content_bye.*

class ByeView :
        GenericActivity<Bye.PresenterToView, Bye.ViewToPresenter, ByePresenter>(),
        Bye.PresenterToView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bye)
        setSupportActionBar(toolbar)
        Log.d(TAG, "calling onCreate()")

        sayByeBtn.setOnClickListener { presenter.onSayByeButtonClicked() }
        backToHelloBtn.setOnClickListener { presenter.onBackToHelloButtonClicked() }
    }

    /**
     * Method that initialized MVP objects
     * [super.onResume] should always be called
     */
    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume(ByePresenter::class.java!!, this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "calling onBackPressed()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "calling onDestroy()")
    }


    // Presenter To View ////////////////////////////////////


    override fun finishScreen() {
        finish()
    }

    override fun hideByeMessage() {
        byeMsgTxt.invisible()
    }

    override fun hideProgressIndicator() {
        progressBye.invisible()
    }

    override fun hideScreenToolbar() {
        toolbar.gone()
    }


    override fun setByeMessage(text: String) {
        byeMsgTxt.text = text
    }

    override fun setGoToHelloButton(label: String) {
        backToHelloBtn.text = label
    }

    override fun setSayByeButton(label: String) {
        sayByeBtn.text = label
    }

    override fun showByeMessage() {
        byeMsgTxt.visible()
    }

    override fun showProgressIndicator() {
        progressBye.visible()
    }


}
