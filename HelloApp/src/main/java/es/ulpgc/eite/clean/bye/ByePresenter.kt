package es.ulpgc.eite.clean.bye

import android.util.Log
import es.ulpgc.eite.clean.app.Mediator

import es.ulpgc.eite.clean.mvp.GenericActivity
import es.ulpgc.eite.clean.mvp.GenericPresenter

/**
 * Layer PRESENTER from Model View Presenter (MVP) Pattern.
 * Mediates the comunication between layers VIEW and MODEL.
 * Layer Presenter no padrão Model View Presenter (MVP).
 */

class ByePresenter :
        GenericPresenter<Bye.PresenterToView, Bye.PresenterToModel, Bye.ModelToPresenter, ByeModel>(),
        Bye.ViewToPresenter, Bye.ModelToPresenter, Bye.HelloToBye, Bye.ByeToHello {


    private var helloButtonClicked: Boolean = false
    private var helloGetTaskRunning: Boolean = false
    private var helloMsgText: String? = null

    /**
     * Operation called during VIEW creation in [GenericActivity.onResume]
     * Responsible to initialize MODEL.
     * Always call [GenericPresenter.onCreate] to initialize the object
     * Always call  [GenericPresenter.setView] to save a ViewIn reference
     *
     * @param view The current VIEW instance
     */
    override fun onCreate(view: Bye.PresenterToView) {
        super.onCreate(ByeModel::class.java!!, this)
        setView(view)
        Log.d(TAG, "calling onCreate()")

        Log.d(TAG, "calling startingByeScreen()")
        val mediator = application as Mediator.Lifecycle
        mediator.startingByeScreen(this)
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call [GenericPresenter.setView]
     * to save the new instance of ViewIn
     *
     * @param view The current VIEW instance
     */
    override fun onResume(view: Bye.PresenterToView) {
        setView(view)
        Log.d(TAG, "calling onResume()")

        settingUpdatedState()
    }

    /**
     * Helper method to inform Presenter that a onBackPressed event occurred
     * Called by [GenericActivity]
     */
    override fun onBackPressed() {
        Log.d(TAG, "calling onBackPressed()")
    }

    /**
     * Hook method called when the VIEW is being destroyed or
     * having its configuration changed.
     * Responsible to maintain MVP synchronized with Activity lifecycle.
     * Called by onDestroy methods of the VIEW layer, like: [GenericActivity.onDestroy]
     *
     * @param isChangingConfiguration true: configuration changing & false: being destroyed
     */
    override fun onDestroy(isChangingConfiguration: Boolean) {
        super.onDestroy(isChangingConfiguration)

        if (isChangingConfiguration) {
            Log.d(TAG, "calling onChangingConfiguration()")
        } else {
            Log.d(TAG, "calling onDestroy()")
        }
    }


    // View To Presenter ////////////////////////////////////


    override fun onBackToHelloButtonClicked() {
        Log.d(TAG, "calling backToHelloScreen()")
        val mediator = application as Mediator.Navigation
        mediator.backToHelloScreen(this)
    }

    override fun onSayByeButtonClicked() {
        if (isViewRunning) {
            if (isByeButtonClicked) {
                view.hideByeMessage()
                Log.d(TAG, "calling resetByeGetMessageTask()")
                model.resetByeGetMessageTask()
            }

            if (!isByeGetTaskRunning) {
                Log.d(TAG, "calling showProgressIndicator()")
                view.showProgressIndicator()
            }

            Log.d(TAG, "calling startByeGetMessageTask()")
            model.startByeGetMessageTask()
            isByeButtonClicked = true
            isByeGetTaskRunning = true
        }
    }

    // Model To Presenter ////////////////////////////////////


    override fun onByeGetMessageTaskFinished(text: String) {
        Log.d(TAG, "calling onByeGetMessageTaskFinished()")
        if (isViewRunning) {
            view.setByeMessage(text)
            view.showByeMessage()

            if (isByeGetTaskRunning) {
                Log.d(TAG, "calling hideProgressIndicator()")
                view.hideProgressIndicator()
                isByeGetTaskRunning = false
            }
        }
    }


    // Hello To Bye ////////////////////////////////////


    override fun onByeScreenStarted() {
        Log.d(TAG, "calling onByeScreenStarted()")
        if (isViewRunning) {
            settingInitialState()
            settingPropagatedState()
        }
    }

    /*
    override fun setHelloGetTaskRunning(running: Boolean) {
        helloGetTaskRunning = running
    }


    override fun setHelloButtonClicked(clicked: Boolean) {
        helloButtonClicked = clicked
    }

    override fun setHelloMsgText(text: String) {
        helloMsgText = text
    }
    */


    override var isHelloButtonClicked: Boolean
        get() = helloButtonClicked
        set(value) {
            helloButtonClicked = value
        }

    override var isHelloGetTaskRunning: Boolean
        get() = helloGetTaskRunning
        set(value) {
            helloGetTaskRunning = value
        }

    override var helloMessage: String?
        get() = helloMsgText
        set(value) {
            helloMsgText = value
        }

    // Bye To Hello ////////////////////////////////////


    override var isByeGetTaskRunning: Boolean = false
        private set

    override var isByeButtonClicked: Boolean = false
        private set

    override val byeMessage: String
        get() = model.byeMessage


    override fun destroyView() {
        if (isViewRunning) {
            view.finishScreen()
        }
    }


    /////////////////////////////////////////////////////////


    private fun settingInitialState() {
        Log.d(TAG, "calling settingInitialState()")
        if (isViewRunning) {
            view.hideScreenToolbar()
            view.setSayByeButton(model.sayByeButtonLabel)
            view.setGoToHelloButton(model.backToHelloButtonLabel)
            view.hideProgressIndicator()
            view.hideByeMessage()
        }
    }

    private fun settingUpdatedState() {
        settingInitialState()

        Log.d(TAG, "calling settingUpdatedState()")
        if (isViewRunning) {
            if (isByeGetTaskRunning) {
                Log.d(TAG, "calling showProgressIndicator()")
                view.showProgressIndicator()
                settingPropagatedState()

            } else {
                if (isByeButtonClicked) {
                    view.showByeMessage()
                    Log.d(TAG, "calling startByeGetMessageTask()")
                    model.startByeGetMessageTask()
                } else {
                    settingPropagatedState()
                }
            }
        }
    }


    private fun settingPropagatedState() {
        Log.d(TAG, "calling settingPropagatedState()")
        if (helloButtonClicked && !helloGetTaskRunning) {
            helloMsgText?.let { view.setByeMessage(it) }
            view.showByeMessage()
        } else {
            view.hideByeMessage()
        }
    }

}
