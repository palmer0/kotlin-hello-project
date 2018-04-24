package es.ulpgc.eite.clean.hello

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import es.ulpgc.eite.clean.R
import es.ulpgc.eite.clean.mvp.GenericActivity
import es.ulpgc.eite.clean.util.gone
import es.ulpgc.eite.clean.util.invisible
import es.ulpgc.eite.clean.util.visible
import kotlinx.android.synthetic.main.activity_hello.*
import kotlinx.android.synthetic.main.content_hello.*

class HelloView :
        GenericActivity<Hello.PresenterToView, Hello.ViewToPresenter, HelloPresenter>(),
        Hello.PresenterToView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        setSupportActionBar(toolbar)
        Log.d(TAG, "calling onCreate()")

        sayHelloBtn.setOnClickListener { presenter.onSayHelloButtonClicked() }
        goToByeBtn.setOnClickListener { presenter.onGoToByeButtonClicked() }
    }


    /**
     * Method that initialized MVP objects
     * [super.onResume] should always be called
     */
    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume(HelloPresenter::class.java!!, this)
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

    override fun hideScreenToolbar() {
        toolbar.gone();
    }

    override fun setHelloMessage(text: String) {
        helloMsgTxt.text = text
    }

    override fun hideHelloMessage() {
        helloMsgTxt.invisible()
    }

    override fun showHelloMessage() {
        helloMsgTxt.visible()
    }

    override fun setSayHelloButton(label: String) {
        sayHelloBtn.text = label
    }

    override fun setGoToByeButton(label: String) {
        goToByeBtn.text = label
    }

    override fun hideProgressIndicator() {
        progressHello.invisible()
    }

    override fun showProgressIndicator() {
        progressHello.visible()
    }


}
