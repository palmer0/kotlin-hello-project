package es.ulpgc.eite.clean.bye

import android.os.Handler
import android.util.Log

import es.ulpgc.eite.clean.mvp.GenericModel


/**
 * Layer MODEL from Model View Presenter (MVP) pattern.
 * Responsible to deal with all business logic and data in general.
 */

class ByeModel : GenericModel<Bye.ModelToPresenter>(), Bye.PresenterToModel {


    private var isTaskFinished: Boolean = false

    /**
     * Method that recovers a reference to the PRESENTER
     * You must ALWAYS call [super.onCreate] here
     *
     * @param presenter Presenter interface
     */
    override fun onCreate(presenter: Bye.ModelToPresenter) {
        super.onCreate(presenter)
        Log.d(TAG, "calling onCreate()")

        byeMessage = "Bye World!"
        sayByeButtonLabel = "Say Bye"
        backToHelloButtonLabel = "Back To Hello"
    }

    /**
     * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
     * Usefull for kill/stop activities that could be running on the background Threads
     *
     * @param isChangingConfiguration Informs that a change is occurring on the configuration
     */
    override fun onDestroy(isChangingConfiguration: Boolean) {

    }

    // Presenter To Model ////////////////////////////////////

    override lateinit var sayByeButtonLabel: String
        private set

    override lateinit var backToHelloButtonLabel: String
        private set

    override lateinit var byeMessage: String
        private set


    override fun resetByeGetMessageTask() {
        isTaskFinished = false
    }

    override fun startByeGetMessageTask() {
        if (isTaskFinished) {
            presenter.onByeGetMessageTaskFinished(byeMessage)
        } else {
            startDelayedTask()
        }
    }

    /////////////////////////////////////////////////////////


    private fun startDelayedTask() {
        Log.d(TAG, "calling startDelayedTask()")

        // Mock Bye: A handler to delay the answer
        Handler().postDelayed({
            isTaskFinished = true
            presenter.onByeGetMessageTaskFinished(byeMessage)
        }, 5000)
    }

}
