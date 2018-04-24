package es.ulpgc.eite.clean.app

import android.app.Application
import android.content.Intent
import android.util.Log
import es.ulpgc.eite.clean.bye.Bye
import es.ulpgc.eite.clean.bye.ByeView
import es.ulpgc.eite.clean.hello.Hello

/**
 * Created by Luis on 13/11/16.
 */

class MediatorApp : Application(), Mediator.Lifecycle, Mediator.Navigation {

    protected val TAG = this.javaClass.getSimpleName()

    private var helloToByeState: ScreenState? = null
    private var byeToHelloState: ScreenState? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "calling onCreate()")
    }

    // Navigation /////////////////////////////////////////

    override fun backToHelloScreen(presenter: Bye.ByeToHello) {
        Log.d(TAG, "calling savingByeToHelloState()")
        byeToHelloState = ScreenState(
                presenter.isByeButtonClicked,
                presenter.isByeGetTaskRunning,
                presenter.byeMessage
        )

        /*
        byeToHelloState = ScreenState()
        byeToHelloState!!.isButtonClicked = presenter.isByeButtonClicked
        byeToHelloState!!.isGetTaskRunning = presenter.isByeGetTaskRunning
        byeToHelloState!!.byeMessage = presenter.byeMessage
        */

        Log.d(TAG, "calling finishingByeScreen()")
        presenter.destroyView()
    }

    override fun goToByeScreen(presenter: Hello.HelloToBye) {
        Log.d(TAG, "calling savingHelloToByeState()")
        helloToByeState = ScreenState(
                presenter.isHelloButtonClicked,
                presenter.isHelloGetTaskRunning,
                presenter.helloMessage
        )

        val view = presenter.managedContext
        Log.d(TAG, "calling startingByeScreen()")
        view?.startActivity(Intent(view, ByeView::class.java))

        /*
        if (view != null) {
            Log.d(TAG, "calling startingByeScreen()")
            view.startActivity(Intent(view, ByeView::class.java))
        }
        */
    }

    // Lifecycle /////////////////////////////////////////////

    override fun resumingHelloScreen(presenter: Hello.ByeToHello) {
        byeToHelloState?.applyToHello(presenter)
        byeToHelloState = null

        /*
        if (byeToHelloState != null) {
            Log.d(TAG, "calling resumingHelloScreen()")
            Log.d(TAG, "calling restoringByeToHelloState()")
            presenter.setByeButtonClicked(byeToHelloState!!.isButtonClicked)
            presenter.setByeGetTaskRunning(byeToHelloState!!.isGetTaskRunning)
            byeToHelloState!!.byeMessage?.let { presenter.setByeMsgText(it) }

            Log.d(TAG, "calling removingByeToHelloState()")
            byeToHelloState = null
        }
        */

        presenter.onHelloScreenResumed()
    }

    override fun startingByeScreen(presenter: Bye.HelloToBye) {
        helloToByeState?.applyToBye(presenter)
        helloToByeState = null

        presenter.onByeScreenStarted()
    }

    // State /////////////////////////////////////////////


    inner class ScreenState {

        internal var isButtonClicked: Boolean = false
        internal var isGetTaskRunning: Boolean = false
        internal var messageText: String? = null

        constructor(isButtonClicked: Boolean, isGetTaskRunning: Boolean, messageText: String?) {
            this.isButtonClicked = isButtonClicked
            this.isGetTaskRunning = isGetTaskRunning
            this.messageText = messageText
        }

    }

    private fun ScreenState.applyToHello(presenter: Hello.ByeToHello) {
        Log.d(TAG, "calling resumingHelloScreen()")

        Log.d(TAG, "calling restoringByeToHelloState()")
        //presenter.setByeButtonClicked(isButtonClicked)
        //presenter.setByeGetTaskRunning(isGetTaskRunning)
        //messageText?.let { presenter.setByeMsgText(it) }
        presenter.isByeButtonClicked = isButtonClicked
        presenter.isByeGetTaskRunning = isGetTaskRunning
        presenter.byeMessage = messageText
        Log.d(TAG, "calling removingByeToHelloState()")

    }

    private fun ScreenState.applyToBye(presenter: Bye.HelloToBye) {
        Log.d(TAG, "calling settingHelloToByeState()")
        presenter.isHelloButtonClicked = isButtonClicked
        presenter.isHelloGetTaskRunning = isGetTaskRunning
        presenter.helloMessage = messageText
        Log.d(TAG, "calling removingHelloToByeState()")

    }
}
