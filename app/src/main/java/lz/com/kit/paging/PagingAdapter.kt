package lz.com.kit.paging

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


/**
 *-----------作者----------日期----------变更内容-----
 *-          刘泽      2019-05-24       创建class
 */
class PagingAdapter : PagedListAdapter<String, PagingAdapter.PageHolder>(PagingCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingAdapter.PageHolder {
        return PageHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: PagingAdapter.PageHolder, position: Int) {
        val item = getItem(position)
        val textView = holder.itemView as TextView
        textView.text = item;
    }


    class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

        }
    }


    class PagingCallBack : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }


    }

}