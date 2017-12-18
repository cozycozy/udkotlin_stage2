package com.example.km.todo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.km.todo.common.IntentKey
import com.example.km.todo.common.ModeInEit
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_edit.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var title: String? = ""
    private var deadline: String? = ""
    private var taskDetail: String? = ""
    private var isCmpleted: Boolean? = false
    private var mode: ModeInEit? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments.getString(ARG_title)
            deadline = arguments.getString(ARG_deadline)
            taskDetail = arguments.getString(ARG_taskdetail)
            isCmpleted = arguments.getBoolean(ARG_isCompleted)
            mode = arguments.getSerializable(ARG_mode) as ModeInEit?
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater!!.inflate(R.layout.fragment_edit, container, false)
        setHasOptionsMenu(true)
        return view

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_resister).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.menu_resister) recordToRealmDB(mode)

        return super.onOptionsItemSelected(item)
    }

    private fun recordToRealmDB(mode: ModeInEit?) {

        //期日とタイトルがセットされていない場合はエラー
        val requireInput = isRequiredFilled()
        if (requireInput) return

        when (mode) {
            ModeInEit.NEW_ENTRY -> addNewTodo()
            ModeInEit.EDIT -> editTodo()
        }

        mListener?.onDataEdited()

        //フラグメントを閉じる
        fragmentManager.beginTransaction().remove(this).commit()

    }

    private fun isRequiredFilled(): Boolean {

        if( inputTitleText.text.toString() == ""){
            inputTitle.error = getString(R.string.error)
            return false
        }

        if( inputDateText.text.toString() == ""){
            inputDate.error = getString(R.string.error)
            return  false
        }

        return true
    }

    private fun editTodo() {


    }

    private fun addNewTodo() {

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val newTodo = realm.createObject(TodoModel::class.java)

        newTodo.apply {
            title = inputTitleText.toString()
            deadLine = inputDateText.toString()
            taskDetail = inputDetailText.toString()
            isCompleted = if(checkBox.isChecked) true else false
        }

        realm.commitTransaction()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateUI(mode)
        imageButton.setOnClickListener{
            mListener!!.onDatePickerLaunched()

        }

    }

    private fun updateUI(mode: ModeInEit?) {

        when(mode) {
            ModeInEit.NEW_ENTRY -> {

            }
            ModeInEit.EDIT -> {
                checkBox.visibility = View.INVISIBLE
            }


        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onDatePickerLaunched()
        fun onFragmentInteraction(uri: Uri)
        fun onDataEdited ()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_title = IntentKey.TITLE.name
        private val ARG_deadline = IntentKey.DEADLINE.name
        private val ARG_taskdetail = IntentKey.TASK_DETAIL.name
        private val ARG_isCompleted = IntentKey.IS_COMPLETED.name
        private val ARG_mode = IntentKey.MODE_IN_EDIT.name

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(title: String, deadline: String, taskdetail : String, isCompleted : Boolean, mode : ModeInEit): EditFragment {
            val fragment = EditFragment()
            val args = Bundle()
            args.putString(ARG_title, title)
            args.putString(ARG_deadline, deadline)
            args.putString(ARG_taskdetail, taskdetail)
            args.putBoolean(ARG_isCompleted, isCompleted)
            args.putSerializable(ARG_mode, mode)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
