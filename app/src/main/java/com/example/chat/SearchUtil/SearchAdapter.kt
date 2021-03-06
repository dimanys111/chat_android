package com.example.chat.SearchUtil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.MainActivity
import com.example.chat.MessagUtil.MessagesFragment
import com.example.chat.R
import com.example.chat.UserUtil.MyUser
import com.example.chat.UserUtil.User
import com.example.chat.Util
import com.example.chat.ui.CircleImageView
import kotlinx.android.synthetic.main.item_list.view.*


class SearchAdapter(
    var searchUsers: MutableList<User>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val mOnClickListener: View.OnClickListener
    init {
        mOnClickListener = View.OnClickListener { v ->
            val user = v.tag as User
            val fragmentManager = MainActivity.activity!!.supportFragmentManager
            MyUser.users_map[user.username]=user
            if(user.messag_fragment==null)
                user.messag_fragment= MessagesFragment.newInstance(user)
            fragmentManager.beginTransaction().replace(R.id.container_frag, user.messag_fragment!!)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = searchUsers[position]
        holder.mNameView.text = user.name
        if(user.activ){
            holder.iv_activ_user.visibility=View.VISIBLE
        } else{
            holder.iv_activ_user.visibility=View.GONE
        }
        Util.set_image_bitmap(
            holder.mImageView,
            user.avatar
        )
        with(holder.ll) {
            tag = user
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = searchUsers.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: CircleImageView = mView.image
        val mNameView: TextView = mView.name
        val ll: LinearLayout = mView.parentLayout
        val iv_activ_user: ImageView = mView.iv_activ_user
    }
}
