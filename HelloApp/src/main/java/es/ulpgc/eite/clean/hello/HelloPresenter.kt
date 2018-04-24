package es.ulpgc.eite.clean.hello

import android.content.Context
import android.util.Log
import es.ulpgc.eite.clean.app.Mediator
import es.ulpgc.eite.clean.mvp.GenericActivity
import es.ulpgc.eite.clean.mvp.GenericPresenter

/**
 * Layer PRESENTER from Model View Presenter (MVP) Pattern.
 * Mediates the comunication between layers VIEW and MODEL.
 */
class HelloPresenter :
        GenericPresenter<Hello.PresenterToView, Hello.PresenterToModel, Hello.ModelToPresenter, HelloModel>(),
        Hello.ViewToPresenter, Hello.ModelToPresenter, Hello.ByeToHello, Hello.HelloToBye {

    private var byeButtonClicked: Boolean = false
    private var byeGetTaskRunning: Boolean = false
    private var byeMsgText: String? = null


    /**
     * Operation called during VIEW creation in [GenericActivity.onResume]
     * Responsible to initialize MODEL.
     * Always call [GenericPresenter.onCreate] to initialize the object
     * Always call  [GenericPresenter.setView] to save a ViewIn reference
     *
     * @param view The current VIEW instance
     */
    override fun onCreate(view: Hello.PresenterToView) {
        super.onCreate(HelloModel::class.java!!, this)
        setView(view)
        Log.d(TAG, "calling onCreate()")
        settingInitialState()
    }

    /**
     * Operation called by VIEW after its reconstruction.
     * Always call [GenericPresenter.setView] to
     * save the new instance of ViewIn
     *
     * @param view The current VIEW instance
     */
    override fun onResume(view: Hello.PresenterToView) {
        setView(view)
        Log.d(TAG, "calling onResume()")
        val mediator = application as Mediator.Lifecycle
        mediator.resumingHelloScreen(this)
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


    // Hello To Bye ////////////////////////////////////

    override var isHelloGetTaskRunning: Boolean = false
        private set

    override var isHelloButtonClicked: Boolean = false
        private set

    override val helloMessage: String
        get() = model.helloMessage

    override val managedContext: Context?
        //get() = activityContext
        get() {
            if (isViewRunning) {
                return view.activityContext
            }
            return null
        }

    // Bye To Hello ////////////////////////////////////


    override fun onHelloScreenResumed() {
        Log.d(TAG, "calling onHelloScreenResumed()")
        settingUpdatedState()
    }

    /*
    override fun setByeButtonClicked(clicked: Boolean) {
        byeButtonClicked = clicked
    }

    override fun setByeMsgText(text: String) {
        byeMsgText = text
    }

    override fun setByeGetTaskRunning(running: Boolean) {
        byeGetTaskRunning = running
    }
    */

    override var isByeButtonClicked: Boolean
        get() = byeButtonClicked
        set(value) {
            byeButtonClicked = value
        }

    override var isByeGetTaskRunning: Boolean
        get() = byeGetTaskRunning
        set(value) {
            byeGetTaskRunning = value
        }

    override var byeMessage: String?
        get() = byeMsgText
        set(value) {
            byeMsgText = value
        }


    // Model To Presenter ////////////////////////////////////


    override fun onHelloGetMessageTaskFinished(text: String) {
        Log.d(TAG, "calling onHelloGetMessageTaskFinished()")
        if (isViewRunning) {
            view.setHelloMessage(text)
            view.showHelloMessage()

            if (isHelloGetTaskRunning) {
                view.hideProgressIndicator()
                Log.d(TAG, "calling hideProgressIndicator()")
                isHelloGetTaskRunning = false
            }
        }
    }

    // View To Presenter ////////////////////////////////////


    override fun onGoToByeButtonClicked() {
        Log.d(TAG, "calling onGoToByeButtonClicked()")

        Log.d(TAG, "calling goToByeScreen()")
        val mediator = application as Mediator.Navigation
        mediator.goToByeScreen(this)
    }

    override fun onSayHelloButtonClicked() {
        Log.d(TAG, "calling onSayHelloButtonClicked()")

        if (isViewRunning) {
            if (isHelloButtonClicked) {
                view.hideHelloMessage()
                Log.d(TAG, "calling resetHelloGetMessageTask()")
                model.resetHelloGetMessageTask()
            }

            if (!isHelloGetTaskRunning) {
                view.showProgressIndicator()
                Log.d(TAG, "calling showProgressIndicator()")
            }

            Log.d(TAG, "calling startHelloGetMessageTask()")
            model.startHelloGetMessageTask()
            isHelloButtonClicked = true
            isHelloGetTaskRunning = true
            byeButtonClicked = false
            byeGetTaskRunning = false
        }
    }

    /////////////////////////////////////////////////////////


    private fun settingInitialState() {
        Log.d(TAG, "calling settingInitialState()")
        if (isViewRunning) {
            view.hideScreenToolbar()
            view.setSayHelloButton(model.sayHelloButtonLabel)
            view.setGoToByeButton(model.goToByeButtonLabel)
            view.hideHelloMessage()
            view.hideProgressIndicator()
        }
    }

    private fun settingUpdatedState() {
        settingInitialState()

        Log.d(TAG, "calling settingUpdatedState()")
        if (isViewRunning) {
            if (isHelloGetTaskRunning) {
                Log.d(TAG, "calling showProgressIndicator()")
                view.showProgressIndicator()

            } else {
                if (isHelloButtonClicked) {
                    view.showHelloMessage()
                    Log.d(TAG, "calling startHelloGetMessageTask()")
                    model.startHelloGetMessageTask()
                }

                if (byeButtonClicked && !byeGetTaskRunning) {
                    settingPropagatedState()
                }
            }
        }
    }

    private fun settingPropagatedState() {
        Log.d(TAG, "calling settingPropagatedState()")
        byeMsgText?.let { view.setHelloMessage(it) }
        view.showHelloMessage()
    }
}
