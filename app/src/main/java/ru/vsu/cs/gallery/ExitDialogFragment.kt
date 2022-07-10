package ru.vsu.cs.gallery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ExitDialogFragment : DialogFragment() {

    interface ExitDialogFragmentListener {
        fun onDialogPositiveClick(dialog: DialogFragment?)
    }

    private var listener: ExitDialogFragmentListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(
                R.string.quit_question
            ).setPositiveButton(
                R.string.quit_yes,
            ) { _, _ ->
                listener?.onDialogPositiveClick(this)
            }.setNegativeButton(
                R.string.quit_no
            ) { _, _ ->
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ExitDialogFragmentListener
        } catch (e: ClassCastException) {
            Log.e("CCE", e.message.toString())
        }
    }
}