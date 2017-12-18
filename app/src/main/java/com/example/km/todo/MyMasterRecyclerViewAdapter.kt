package com.example.km.todo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.km.todo.MasterFragment.OnListFragmentInteractionListener
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyMasterRecyclerViewAdapter
    (private val mValues: RealmResults<TodoModel>,
     private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyMasterRecyclerViewAdapter.ViewHolder>() {

    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_master, parent, false)
        return ViewHolder(view)
    }

    public override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues.get(position)
        holder.textViewTitle.text = mValues[position]?.title
        holder.textViewDeadline.text = MyApplication.appContext.getString(R.string.deadline) + ":" + mValues[position]?.deadLine

        val changedDeadline = SimpleDateFormat("yyyy/MM/dd").parse(mValues[position]?.deadLine)
        val today = Date()

        if(today >= changedDeadline) {
            holder.imageStatus.setImageResource(R.drawable.ic_warning_black_24dp)
        } else {
            holder.imageStatus.setImageResource(R.drawable.ic_work_black_24dp)
        }

        holder.mView.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener!!.onListFragmentInteraction(holder.mItem!!)
                }
            }
        })
    }

    public override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textViewTitle : TextView
        val textViewDeadline: TextView
        val imageStatus : ImageView
        var mItem: TodoModel? = null

        init {
            textViewTitle = mView.findViewById<View>(R.id.textViewTitle) as TextView
            textViewDeadline = mView.findViewById<View>(R.id.textViewDeadline) as TextView
            imageStatus = mView.findViewById(R.id.imageStatus)
        }

    }
}
